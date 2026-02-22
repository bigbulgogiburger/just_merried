/// WeddingMate Design System - Flutter ThemeData
/// Generated from DTCG tokens
///
/// Usage:
///   MaterialApp(theme: WeddingMateTheme.light, ...)

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

// ---------------------------------------------------------------------------
// Color Definitions
// ---------------------------------------------------------------------------

class WeddingMateColors {
  WeddingMateColors._();

  // Primary (Rose Gold)
  static const Color primary50 = Color(0xFFFDF2F4);
  static const Color primary100 = Color(0xFFFAE5E8);
  static const Color primary200 = Color(0xFFF5CCD2);
  static const Color primary300 = Color(0xFFECAAB3);
  static const Color primary400 = Color(0xFFD4808D);
  static const Color primary500 = Color(0xFFB76E79);
  static const Color primary600 = Color(0xFF9C4F5A);
  static const Color primary700 = Color(0xFF7D3A44);
  static const Color primary800 = Color(0xFF5E2B33);
  static const Color primary900 = Color(0xFF3F1C22);

  static const MaterialColor primary = MaterialColor(0xFFB76E79, {
    50: primary50,
    100: primary100,
    200: primary200,
    300: primary300,
    400: primary400,
    500: primary500,
    600: primary600,
    700: primary700,
    800: primary800,
    900: primary900,
  });

  // Secondary (Ivory)
  static const Color secondary50 = Color(0xFFFFFFF0);
  static const Color secondary = Color(0xFFFFFFF0);

  // Accent (Dusty Rose)
  static const Color accent50 = Color(0xFFFBF5F1);
  static const Color accent100 = Color(0xFFF7EBE3);
  static const Color accent200 = Color(0xFFEFD7C7);
  static const Color accent300 = Color(0xFFE5C0A8);
  static const Color accent400 = Color(0xFFDCAE96);
  static const Color accent500 = Color(0xFFC89478);
  static const Color accent600 = Color(0xFFB07A5E);
  static const Color accent700 = Color(0xFF8E6048);
  static const Color accent800 = Color(0xFF6C4836);
  static const Color accent900 = Color(0xFF4A3225);

  // Neutral
  static const Color neutral50 = Color(0xFFF5F5F5);
  static const Color neutral100 = Color(0xFFEBEBEB);
  static const Color neutral200 = Color(0xFFD9D9D9);
  static const Color neutral300 = Color(0xFFBFBFBF);
  static const Color neutral400 = Color(0xFF9B9B9B);
  static const Color neutral500 = Color(0xFF8A8A8A);
  static const Color neutral600 = Color(0xFF6B6B6B);
  static const Color neutral700 = Color(0xFF525252);
  static const Color neutral800 = Color(0xFF3D3D3D);
  static const Color neutral900 = Color(0xFF2D2D2D);

  // Semantic
  static const Color success = Color(0xFF4CAF50);
  static const Color warning = Color(0xFFFF9800);
  static const Color error = Color(0xFFF44336);
  static const Color info = Color(0xFF2196F3);

  // Background
  static const Color backgroundPrimary = Color(0xFFFFF9F5);
  static const Color backgroundSecondary = Color(0xFFFFFFFF);
  static const Color backgroundSurface = Color(0xFFFFFFF0);

  // Social
  static const Color kakao = Color(0xFFFEE500);
  static const Color naver = Color(0xFF03C75A);
  static const Color google = Color(0xFFFFFFFF);
  static const Color apple = Color(0xFF000000);
}

// ---------------------------------------------------------------------------
// Spacing
// ---------------------------------------------------------------------------

class WeddingMateSpacing {
  WeddingMateSpacing._();

  static const double xs = 4;
  static const double sm = 8;
  static const double md = 12;
  static const double base = 16;
  static const double lg = 20;
  static const double xl = 24;
  static const double xxl = 32;
  static const double xxxl = 40;
  static const double huge = 48;
  static const double massive = 64;
  static const double giant = 80;
  static const double colossal = 96;
}

// ---------------------------------------------------------------------------
// Border Radius
// ---------------------------------------------------------------------------

class WeddingMateRadius {
  WeddingMateRadius._();

  static const double xs = 2;
  static const double sm = 4;
  static const double md = 8;
  static const double lg = 12;
  static const double xl = 16;
  static const double xxl = 24;
  static const double full = 9999;

  static BorderRadius get xsBorder => BorderRadius.circular(xs);
  static BorderRadius get smBorder => BorderRadius.circular(sm);
  static BorderRadius get mdBorder => BorderRadius.circular(md);
  static BorderRadius get lgBorder => BorderRadius.circular(lg);
  static BorderRadius get xlBorder => BorderRadius.circular(xl);
  static BorderRadius get xxlBorder => BorderRadius.circular(xxl);
  static BorderRadius get fullBorder => BorderRadius.circular(full);
}

// ---------------------------------------------------------------------------
// Shadows
// ---------------------------------------------------------------------------

class WeddingMateShadows {
  WeddingMateShadows._();

  static List<BoxShadow> get sm => [
        const BoxShadow(
          offset: Offset(0, 1),
          blurRadius: 2,
          color: Color.fromRGBO(0, 0, 0, 0.05),
        ),
      ];

  static List<BoxShadow> get md => [
        const BoxShadow(
          offset: Offset(0, 4),
          blurRadius: 6,
          color: Color.fromRGBO(0, 0, 0, 0.07),
        ),
      ];

  static List<BoxShadow> get lg => [
        const BoxShadow(
          offset: Offset(0, 10),
          blurRadius: 15,
          color: Color.fromRGBO(0, 0, 0, 0.1),
        ),
      ];

  static List<BoxShadow> get xl => [
        const BoxShadow(
          offset: Offset(0, 20),
          blurRadius: 25,
          color: Color.fromRGBO(0, 0, 0, 0.1),
        ),
      ];
}

// ---------------------------------------------------------------------------
// Typography
// ---------------------------------------------------------------------------

class WeddingMateTypography {
  WeddingMateTypography._();

  static TextStyle get display => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 36,
        height: 44 / 36,
        fontWeight: FontWeight.w700,
        letterSpacing: -0.72,
      );

  static TextStyle get h1 => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 28,
        height: 36 / 28,
        fontWeight: FontWeight.w700,
        letterSpacing: -0.28,
      );

  static TextStyle get h2 => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 24,
        height: 32 / 24,
        fontWeight: FontWeight.w600,
        letterSpacing: -0.24,
      );

  static TextStyle get h3 => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 20,
        height: 28 / 20,
        fontWeight: FontWeight.w600,
        letterSpacing: 0,
      );

  static TextStyle get h4 => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 18,
        height: 26 / 18,
        fontWeight: FontWeight.w500,
        letterSpacing: 0,
      );

  static TextStyle get body1 => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 16,
        height: 24 / 16,
        fontWeight: FontWeight.w400,
        letterSpacing: 0,
      );

  static TextStyle get body2 => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 14,
        height: 22 / 14,
        fontWeight: FontWeight.w400,
        letterSpacing: 0,
      );

  static TextStyle get caption => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 12,
        height: 18 / 12,
        fontWeight: FontWeight.w400,
        letterSpacing: 0.12,
      );

  static TextStyle get overline => const TextStyle(
        fontFamily: 'Pretendard',
        fontSize: 10,
        height: 14 / 10,
        fontWeight: FontWeight.w500,
        letterSpacing: 0.5,
      );
}

// ---------------------------------------------------------------------------
// Theme
// ---------------------------------------------------------------------------

class WeddingMateTheme {
  WeddingMateTheme._();

  static ThemeData get light => ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: WeddingMateColors.primary500,
          primary: WeddingMateColors.primary500,
          onPrimary: Colors.white,
          secondary: WeddingMateColors.accent400,
          onSecondary: WeddingMateColors.neutral900,
          error: WeddingMateColors.error,
          surface: WeddingMateColors.backgroundPrimary,
          onSurface: WeddingMateColors.neutral900,
        ),
        scaffoldBackgroundColor: WeddingMateColors.backgroundPrimary,
        fontFamily: 'Pretendard',
        textTheme: TextTheme(
          displayLarge: WeddingMateTypography.display,
          headlineLarge: WeddingMateTypography.h1,
          headlineMedium: WeddingMateTypography.h2,
          headlineSmall: WeddingMateTypography.h3,
          titleLarge: WeddingMateTypography.h4,
          bodyLarge: WeddingMateTypography.body1,
          bodyMedium: WeddingMateTypography.body2,
          bodySmall: WeddingMateTypography.caption,
          labelSmall: WeddingMateTypography.overline,
        ),
        appBarTheme: AppBarTheme(
          backgroundColor: WeddingMateColors.backgroundPrimary,
          foregroundColor: WeddingMateColors.neutral900,
          elevation: 0,
          centerTitle: true,
          titleTextStyle: WeddingMateTypography.h4.copyWith(
            color: WeddingMateColors.neutral900,
          ),
        ),
        bottomNavigationBarTheme: const BottomNavigationBarThemeData(
          backgroundColor: Colors.white,
          selectedItemColor: WeddingMateColors.primary500,
          unselectedItemColor: WeddingMateColors.neutral400,
          selectedLabelStyle: TextStyle(fontSize: 10, fontWeight: FontWeight.w500),
          unselectedLabelStyle: TextStyle(fontSize: 10, fontWeight: FontWeight.w400),
          type: BottomNavigationBarType.fixed,
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: WeddingMateColors.primary500,
            foregroundColor: Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: WeddingMateRadius.mdBorder,
            ),
            padding: const EdgeInsets.symmetric(
              horizontal: WeddingMateSpacing.xl,
              vertical: WeddingMateSpacing.md,
            ),
            textStyle: WeddingMateTypography.body1.copyWith(
              fontWeight: FontWeight.w600,
            ),
          ),
        ),
        outlinedButtonTheme: OutlinedButtonThemeData(
          style: OutlinedButton.styleFrom(
            foregroundColor: WeddingMateColors.primary500,
            side: const BorderSide(color: WeddingMateColors.primary500),
            shape: RoundedRectangleBorder(
              borderRadius: WeddingMateRadius.mdBorder,
            ),
            padding: const EdgeInsets.symmetric(
              horizontal: WeddingMateSpacing.xl,
              vertical: WeddingMateSpacing.md,
            ),
          ),
        ),
        inputDecorationTheme: InputDecorationTheme(
          filled: true,
          fillColor: Colors.white,
          contentPadding: const EdgeInsets.symmetric(
            horizontal: WeddingMateSpacing.base,
            vertical: WeddingMateSpacing.md,
          ),
          border: OutlineInputBorder(
            borderRadius: WeddingMateRadius.mdBorder,
            borderSide: const BorderSide(color: WeddingMateColors.neutral200),
          ),
          enabledBorder: OutlineInputBorder(
            borderRadius: WeddingMateRadius.mdBorder,
            borderSide: const BorderSide(color: WeddingMateColors.neutral200),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: WeddingMateRadius.mdBorder,
            borderSide: const BorderSide(color: WeddingMateColors.primary500, width: 1.5),
          ),
          errorBorder: OutlineInputBorder(
            borderRadius: WeddingMateRadius.mdBorder,
            borderSide: const BorderSide(color: WeddingMateColors.error),
          ),
          hintStyle: WeddingMateTypography.body1.copyWith(
            color: WeddingMateColors.neutral400,
          ),
        ),
        cardTheme: CardTheme(
          color: Colors.white,
          elevation: 0,
          shape: RoundedRectangleBorder(
            borderRadius: WeddingMateRadius.lgBorder,
          ),
        ),
        dividerTheme: const DividerThemeData(
          color: WeddingMateColors.neutral100,
          thickness: 1,
        ),
      );
}
