abstract final class ApiEndpoints {
  // Auth
  static const String login = '/api/v1/auth/login';
  static const String logout = '/api/v1/auth/logout';
  static const String refresh = '/api/v1/auth/refresh';
  static const String socialLogin = '/api/v1/auth/social';

  // User
  static const String userProfile = '/api/v1/users/me';
  static const String updateProfile = '/api/v1/users/me';

  // Wedding
  static const String weddingInfo = '/api/v1/wedding';
  static const String weddingChecklist = '/api/v1/wedding/checklist';
  static const String weddingBudget = '/api/v1/wedding/budget';
  static const String weddingSchedule = '/api/v1/wedding/schedule';

  // Community
  static const String posts = '/api/v1/posts';
  static const String comments = '/api/v1/comments';

  // Market
  static const String products = '/api/v1/products';
  static const String vendors = '/api/v1/vendors';

  // Notification
  static const String notifications = '/api/v1/notifications';
  static const String fcmToken = '/api/v1/notifications/fcm-token';

  // File
  static const String uploadImage = '/api/v1/files/upload';
}
