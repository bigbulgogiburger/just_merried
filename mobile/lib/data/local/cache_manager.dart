import 'dart:convert';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:wedding_mate/core/constants/app_constants.dart';
import 'package:wedding_mate/data/local/hive_service.dart';

final cacheManagerProvider = Provider<CacheManager>((ref) {
  return CacheManager();
});

class CacheManager {
  Future<void> put(
    String key,
    dynamic data, {
    Duration ttl = AppConstants.defaultCacheTtl,
  }) async {
    final entry = CacheEntry(
      data: data,
      expiresAt: DateTime.now().add(ttl),
    );
    await HiveService.cacheBox.put(key, json.encode(entry.toJson()));
  }

  T? get<T>(String key) {
    final raw = HiveService.cacheBox.get(key) as String?;
    if (raw == null) return null;

    final entry = CacheEntry.fromJson(json.decode(raw) as Map<String, dynamic>);
    if (entry.isExpired) {
      HiveService.cacheBox.delete(key);
      return null;
    }

    return entry.data as T?;
  }

  Future<void> remove(String key) async {
    await HiveService.cacheBox.delete(key);
  }

  Future<void> clearExpired() async {
    final box = HiveService.cacheBox;
    final keysToRemove = <dynamic>[];

    for (final key in box.keys) {
      final raw = box.get(key) as String?;
      if (raw == null) continue;

      try {
        final entry =
            CacheEntry.fromJson(json.decode(raw) as Map<String, dynamic>);
        if (entry.isExpired) {
          keysToRemove.add(key);
        }
      } catch (_) {
        keysToRemove.add(key);
      }
    }

    await box.deleteAll(keysToRemove);
  }

  Future<void> clearAll() async {
    await HiveService.cacheBox.clear();
  }
}

class CacheEntry {
  const CacheEntry({
    required this.data,
    required this.expiresAt,
  });

  final dynamic data;
  final DateTime expiresAt;

  bool get isExpired => DateTime.now().isAfter(expiresAt);

  Map<String, dynamic> toJson() => {
        'data': data,
        'expiresAt': expiresAt.toIso8601String(),
      };

  factory CacheEntry.fromJson(Map<String, dynamic> json) {
    return CacheEntry(
      data: json['data'],
      expiresAt: DateTime.parse(json['expiresAt'] as String),
    );
  }
}
