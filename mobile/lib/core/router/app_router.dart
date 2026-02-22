import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:wedding_mate/core/router/route_names.dart';
import 'package:wedding_mate/presentation/screens/login/login_screen.dart';
import 'package:wedding_mate/presentation/screens/main/main_screen.dart';
import 'package:wedding_mate/presentation/screens/main/home/home_tab.dart';
import 'package:wedding_mate/presentation/screens/main/wedding_prep/wedding_prep_tab.dart';
import 'package:wedding_mate/presentation/screens/main/community/community_tab.dart';
import 'package:wedding_mate/presentation/screens/main/market/market_tab.dart';
import 'package:wedding_mate/presentation/screens/main/my/my_tab.dart';
import 'package:wedding_mate/presentation/screens/onboarding/onboarding_screen.dart';
import 'package:wedding_mate/presentation/screens/splash/splash_screen.dart';

final _rootNavigatorKey = GlobalKey<NavigatorState>();
final _shellNavigatorKey = GlobalKey<NavigatorState>();

final appRouterProvider = Provider<GoRouter>((ref) {
  return GoRouter(
    navigatorKey: _rootNavigatorKey,
    initialLocation: RoutePaths.splash,
    debugLogDiagnostics: true,
    routes: [
      GoRoute(
        path: RoutePaths.splash,
        name: RouteNames.splash,
        builder: (context, state) => const SplashScreen(),
      ),
      GoRoute(
        path: RoutePaths.onboarding,
        name: RouteNames.onboarding,
        builder: (context, state) => const OnboardingScreen(),
      ),
      GoRoute(
        path: RoutePaths.login,
        name: RouteNames.login,
        builder: (context, state) => const LoginScreen(),
      ),
      ShellRoute(
        navigatorKey: _shellNavigatorKey,
        builder: (context, state, child) => MainScreen(child: child),
        routes: [
          GoRoute(
            path: RoutePaths.home,
            name: RouteNames.home,
            pageBuilder: (context, state) => const NoTransitionPage(
              child: HomeTab(),
            ),
          ),
          GoRoute(
            path: RoutePaths.weddingPrep,
            name: RouteNames.weddingPrep,
            pageBuilder: (context, state) => const NoTransitionPage(
              child: WeddingPrepTab(),
            ),
          ),
          GoRoute(
            path: RoutePaths.community,
            name: RouteNames.community,
            pageBuilder: (context, state) => const NoTransitionPage(
              child: CommunityTab(),
            ),
          ),
          GoRoute(
            path: RoutePaths.market,
            name: RouteNames.market,
            pageBuilder: (context, state) => const NoTransitionPage(
              child: MarketTab(),
            ),
          ),
          GoRoute(
            path: RoutePaths.my,
            name: RouteNames.my,
            pageBuilder: (context, state) => const NoTransitionPage(
              child: MyTab(),
            ),
          ),
        ],
      ),
    ],
  );
});
