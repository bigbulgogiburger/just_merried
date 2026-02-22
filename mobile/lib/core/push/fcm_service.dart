import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:wedding_mate/core/push/notification_handler.dart';
import 'package:wedding_mate/core/utils/logger.dart';

@pragma('vm:entry-point')
Future<void> _firebaseBackgroundHandler(RemoteMessage message) async {
  await Firebase.initializeApp();
  appLogger.i('Background message: ${message.messageId}');
  await NotificationHandler.handleBackgroundMessage(message);
}

class FcmService {
  FcmService._();

  static final FirebaseMessaging _messaging = FirebaseMessaging.instance;

  static Future<void> initialize() async {
    try {
      await Firebase.initializeApp();

      // Request permission
      final settings = await _messaging.requestPermission(
        alert: true,
        badge: true,
        sound: true,
        provisional: false,
      );

      appLogger.i('FCM permission: ${settings.authorizationStatus}');

      if (settings.authorizationStatus == AuthorizationStatus.authorized ||
          settings.authorizationStatus == AuthorizationStatus.provisional) {
        await _setupMessageHandlers();
      }
    } catch (e) {
      appLogger.e('FCM initialization failed', error: e);
    }
  }

  static Future<void> _setupMessageHandlers() async {
    // Background handler
    FirebaseMessaging.onBackgroundMessage(_firebaseBackgroundHandler);

    // Foreground handler
    FirebaseMessaging.onMessage.listen(NotificationHandler.handleForegroundMessage);

    // Opened from notification
    FirebaseMessaging.onMessageOpenedApp.listen(
      NotificationHandler.handleMessageOpenedApp,
    );

    // Handle notification that opened the app from terminated state
    final initialMessage = await _messaging.getInitialMessage();
    if (initialMessage != null) {
      NotificationHandler.handleMessageOpenedApp(initialMessage);
    }

    // Configure foreground notification presentation
    await _messaging.setForegroundNotificationPresentationOptions(
      alert: true,
      badge: true,
      sound: true,
    );
  }

  static Future<String?> getToken() async {
    try {
      return await _messaging.getToken();
    } catch (e) {
      appLogger.e('Failed to get FCM token', error: e);
      return null;
    }
  }

  static Stream<String> get onTokenRefresh => _messaging.onTokenRefresh;

  static Future<void> subscribeToTopic(String topic) async {
    await _messaging.subscribeToTopic(topic);
  }

  static Future<void> unsubscribeFromTopic(String topic) async {
    await _messaging.unsubscribeFromTopic(topic);
  }
}
