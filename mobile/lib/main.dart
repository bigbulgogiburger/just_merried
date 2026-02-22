import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:wedding_mate/core/push/fcm_service.dart';
import 'package:wedding_mate/core/router/app_router.dart';
import 'package:wedding_mate/core/theme/app_theme.dart';
import 'package:wedding_mate/data/local/hive_service.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  // Set preferred orientations
  await SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp,
    DeviceOrientation.portraitDown,
  ]);

  // Initialize Hive
  await Hive.initFlutter();
  await HiveService.initialize();

  // Initialize FCM
  await FcmService.initialize();

  runApp(
    const ProviderScope(
      child: WeddingMateApp(),
    ),
  );
}

class WeddingMateApp extends ConsumerWidget {
  const WeddingMateApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final router = ref.watch(appRouterProvider);

    return MaterialApp.router(
      title: 'WeddingMate',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.light,
      routerConfig: router,
    );
  }
}
