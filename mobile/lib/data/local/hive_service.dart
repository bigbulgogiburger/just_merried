import 'package:hive_flutter/hive_flutter.dart';
import 'package:wedding_mate/core/utils/logger.dart';

abstract final class HiveBoxNames {
  static const String auth = 'auth_box';
  static const String user = 'user_box';
  static const String cache = 'cache_box';
  static const String settings = 'settings_box';
}

class HiveService {
  HiveService._();

  static Future<void> initialize() async {
    try {
      await Hive.openBox<String>(HiveBoxNames.auth);
      await Hive.openBox<dynamic>(HiveBoxNames.user);
      await Hive.openBox<dynamic>(HiveBoxNames.cache);
      await Hive.openBox<dynamic>(HiveBoxNames.settings);
      appLogger.i('Hive initialized successfully');
    } catch (e) {
      appLogger.e('Hive initialization failed', error: e);
      rethrow;
    }
  }

  static Future<void> clearAll() async {
    await Hive.box<String>(HiveBoxNames.auth).clear();
    await Hive.box<dynamic>(HiveBoxNames.user).clear();
    await Hive.box<dynamic>(HiveBoxNames.cache).clear();
  }

  static Box<String> get authBox => Hive.box<String>(HiveBoxNames.auth);
  static Box<dynamic> get userBox => Hive.box<dynamic>(HiveBoxNames.user);
  static Box<dynamic> get cacheBox => Hive.box<dynamic>(HiveBoxNames.cache);
  static Box<dynamic> get settingsBox =>
      Hive.box<dynamic>(HiveBoxNames.settings);
}
