import 'dart:convert';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:wedding_mate/data/local/hive_service.dart';

final userLocalSourceProvider = Provider<UserLocalSource>((ref) {
  return UserLocalSource();
});

class UserLocalSource {
  static const String _userProfileKey = 'user_profile';
  static const String _onboardingCompletedKey = 'onboarding_completed';
  static const String _notificationEnabledKey = 'notification_enabled';
  static const String _themeKey = 'theme_mode';

  // User profile cache
  Future<void> saveUserProfile(Map<String, dynamic> profile) async {
    await HiveService.userBox.put(_userProfileKey, json.encode(profile));
  }

  Map<String, dynamic>? getUserProfile() {
    final raw = HiveService.userBox.get(_userProfileKey) as String?;
    if (raw == null) return null;
    return json.decode(raw) as Map<String, dynamic>;
  }

  Future<void> clearUserProfile() async {
    await HiveService.userBox.delete(_userProfileKey);
  }

  // Settings
  Future<void> setOnboardingCompleted(bool completed) async {
    await HiveService.settingsBox.put(_onboardingCompletedKey, completed);
  }

  bool get isOnboardingCompleted =>
      HiveService.settingsBox.get(_onboardingCompletedKey, defaultValue: false)
          as bool;

  Future<void> setNotificationEnabled(bool enabled) async {
    await HiveService.settingsBox.put(_notificationEnabledKey, enabled);
  }

  bool get isNotificationEnabled =>
      HiveService.settingsBox.get(_notificationEnabledKey, defaultValue: true)
          as bool;

  Future<void> setThemeMode(String mode) async {
    await HiveService.settingsBox.put(_themeKey, mode);
  }

  String get themeMode =>
      HiveService.settingsBox.get(_themeKey, defaultValue: 'light') as String;
}
