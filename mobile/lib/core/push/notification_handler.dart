import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:wedding_mate/core/push/local_notification_service.dart';
import 'package:wedding_mate/core/utils/logger.dart';

class NotificationHandler {
  NotificationHandler._();

  static Future<void> handleForegroundMessage(RemoteMessage message) async {
    appLogger.i('Foreground message: ${message.messageId}');

    final notification = message.notification;
    if (notification != null) {
      await LocalNotificationService.show(
        title: notification.title ?? '',
        body: notification.body ?? '',
        payload: message.data.toString(),
      );
    }
  }

  static Future<void> handleBackgroundMessage(RemoteMessage message) async {
    appLogger.i('Background message: ${message.messageId}');
    // Background messages are automatically shown as notifications
    // Add any additional processing here
  }

  static void handleMessageOpenedApp(RemoteMessage message) {
    appLogger.i('Opened from notification: ${message.messageId}');

    final data = message.data;
    final type = data['type'] as String?;
    final targetId = data['targetId'] as String?;

    if (type != null && targetId != null) {
      _navigateToTarget(type, targetId);
    }
  }

  static void _navigateToTarget(String type, String targetId) {
    // TODO: Implement deep link navigation based on notification type
    appLogger.d('Navigate to $type: $targetId');
  }
}
