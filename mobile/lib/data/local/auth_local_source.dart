import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:wedding_mate/data/local/hive_service.dart';

final authLocalSourceProvider = Provider<AuthLocalSource>((ref) {
  return AuthLocalSource();
});

class AuthLocalSource {
  static const String _accessTokenKey = 'access_token';
  static const String _refreshTokenKey = 'refresh_token';

  Future<void> saveTokens({
    required String accessToken,
    required String refreshToken,
  }) async {
    final box = HiveService.authBox;
    await box.put(_accessTokenKey, accessToken);
    await box.put(_refreshTokenKey, refreshToken);
  }

  Future<String?> getAccessToken() async {
    return HiveService.authBox.get(_accessTokenKey);
  }

  Future<String?> getRefreshToken() async {
    return HiveService.authBox.get(_refreshTokenKey);
  }

  Future<void> clearTokens() async {
    final box = HiveService.authBox;
    await box.delete(_accessTokenKey);
    await box.delete(_refreshTokenKey);
  }

  bool get hasTokens => HiveService.authBox.containsKey(_accessTokenKey);
}
