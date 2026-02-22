abstract final class AppConstants {
  static const String appName = 'WeddingMate';
  static const String appVersion = '1.0.0';

  // API
  static const String baseUrl = 'https://api.weddingmate.co.kr';
  static const Duration connectionTimeout = Duration(seconds: 30);
  static const Duration receiveTimeout = Duration(seconds: 30);

  // Cache
  static const Duration defaultCacheTtl = Duration(minutes: 30);
  static const Duration longCacheTtl = Duration(hours: 24);

  // Pagination
  static const int defaultPageSize = 20;

  // Animation
  static const Duration shortAnimation = Duration(milliseconds: 200);
  static const Duration mediumAnimation = Duration(milliseconds: 350);
  static const Duration longAnimation = Duration(milliseconds: 500);

  // Image
  static const int maxImageWidth = 1080;
  static const int maxImageQuality = 85;
  static const int thumbnailSize = 200;
}
