import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_radius.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';

abstract final class AppTheme {
  static ThemeData get light => ThemeData(
        useMaterial3: true,
        brightness: Brightness.light,
        fontFamily: 'Pretendard',

        // Color Scheme
        colorScheme: const ColorScheme.light(
          primary: AppColors.primaryRoseGold,
          onPrimary: Colors.white,
          primaryContainer: AppColors.primaryLight,
          onPrimaryContainer: AppColors.primaryDark,
          secondary: AppColors.accentDustyRose,
          onSecondary: Colors.white,
          surface: AppColors.surface,
          onSurface: AppColors.textPrimary,
          error: AppColors.error,
          onError: Colors.white,
        ),

        // Scaffold
        scaffoldBackgroundColor: AppColors.background,

        // AppBar
        appBarTheme: const AppBarTheme(
          backgroundColor: AppColors.surface,
          foregroundColor: AppColors.textPrimary,
          elevation: 0,
          scrolledUnderElevation: 0.5,
          centerTitle: true,
          titleTextStyle: AppTypography.h5,
          systemOverlayStyle: SystemUiOverlayStyle(
            statusBarColor: Colors.transparent,
            statusBarIconBrightness: Brightness.dark,
            statusBarBrightness: Brightness.light,
          ),
        ),

        // Bottom Navigation
        bottomNavigationBarTheme: const BottomNavigationBarThemeData(
          backgroundColor: AppColors.surface,
          selectedItemColor: AppColors.primaryRoseGold,
          unselectedItemColor: AppColors.textMuted,
          type: BottomNavigationBarType.fixed,
          elevation: 8,
          selectedLabelStyle: TextStyle(
            fontFamily: 'Pretendard',
            fontSize: 12,
            fontWeight: FontWeight.w600,
          ),
          unselectedLabelStyle: TextStyle(
            fontFamily: 'Pretendard',
            fontSize: 12,
            fontWeight: FontWeight.w400,
          ),
        ),

        // Card
        cardTheme: CardTheme(
          color: AppColors.surface,
          elevation: 0,
          shape: RoundedRectangleBorder(
            borderRadius: AppRadius.mdBorder,
            side: const BorderSide(color: AppColors.border, width: 1),
          ),
          margin: EdgeInsets.zero,
        ),

        // Input Decoration
        inputDecorationTheme: InputDecorationTheme(
          filled: true,
          fillColor: AppColors.surface,
          contentPadding: const EdgeInsets.symmetric(
            horizontal: 16,
            vertical: 14,
          ),
          border: OutlineInputBorder(
            borderRadius: AppRadius.smBorder,
            borderSide: const BorderSide(color: AppColors.border),
          ),
          enabledBorder: OutlineInputBorder(
            borderRadius: AppRadius.smBorder,
            borderSide: const BorderSide(color: AppColors.border),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: AppRadius.smBorder,
            borderSide: const BorderSide(
              color: AppColors.primaryRoseGold,
              width: 1.5,
            ),
          ),
          errorBorder: OutlineInputBorder(
            borderRadius: AppRadius.smBorder,
            borderSide: const BorderSide(color: AppColors.error),
          ),
          focusedErrorBorder: OutlineInputBorder(
            borderRadius: AppRadius.smBorder,
            borderSide: const BorderSide(
              color: AppColors.error,
              width: 1.5,
            ),
          ),
          hintStyle: AppTypography.body2.copyWith(
            color: AppColors.textMuted,
          ),
          labelStyle: AppTypography.label,
          errorStyle: AppTypography.caption.copyWith(
            color: AppColors.error,
          ),
        ),

        // Elevated Button
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: AppColors.primaryRoseGold,
            foregroundColor: Colors.white,
            disabledBackgroundColor: AppColors.primaryLight.withValues(alpha: 0.5),
            disabledForegroundColor: Colors.white70,
            elevation: 0,
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 14),
            shape: RoundedRectangleBorder(
              borderRadius: AppRadius.smBorder,
            ),
            textStyle: AppTypography.button,
          ),
        ),

        // Outlined Button
        outlinedButtonTheme: OutlinedButtonThemeData(
          style: OutlinedButton.styleFrom(
            foregroundColor: AppColors.primaryRoseGold,
            side: const BorderSide(color: AppColors.primaryRoseGold),
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 14),
            shape: RoundedRectangleBorder(
              borderRadius: AppRadius.smBorder,
            ),
            textStyle: AppTypography.button,
          ),
        ),

        // Text Button
        textButtonTheme: TextButtonThemeData(
          style: TextButton.styleFrom(
            foregroundColor: AppColors.primaryRoseGold,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            textStyle: AppTypography.button,
          ),
        ),

        // Divider
        dividerTheme: const DividerThemeData(
          color: AppColors.border,
          thickness: 1,
          space: 1,
        ),

        // Chip
        chipTheme: ChipThemeData(
          backgroundColor: AppColors.surface,
          selectedColor: AppColors.primaryRoseGold.withValues(alpha: 0.1),
          disabledColor: AppColors.border,
          labelStyle: AppTypography.body2,
          shape: RoundedRectangleBorder(
            borderRadius: AppRadius.fullBorder,
            side: const BorderSide(color: AppColors.border),
          ),
          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
        ),

        // Bottom Sheet
        bottomSheetTheme: const BottomSheetThemeData(
          backgroundColor: AppColors.surface,
          shape: RoundedRectangleBorder(
            borderRadius: AppRadius.topXl,
          ),
          showDragHandle: true,
          dragHandleColor: AppColors.border,
        ),

        // Dialog
        dialogTheme: DialogTheme(
          backgroundColor: AppColors.surface,
          shape: RoundedRectangleBorder(
            borderRadius: AppRadius.lgBorder,
          ),
          titleTextStyle: AppTypography.h5,
          contentTextStyle: AppTypography.body2,
        ),

        // Snackbar
        snackBarTheme: SnackBarThemeData(
          backgroundColor: AppColors.textPrimary,
          contentTextStyle: AppTypography.body2.copyWith(color: Colors.white),
          shape: RoundedRectangleBorder(
            borderRadius: AppRadius.smBorder,
          ),
          behavior: SnackBarBehavior.floating,
        ),

        // Tab Bar
        tabBarTheme: const TabBarTheme(
          labelColor: AppColors.primaryRoseGold,
          unselectedLabelColor: AppColors.textMuted,
          indicatorColor: AppColors.primaryRoseGold,
          labelStyle: AppTypography.label,
          unselectedLabelStyle: AppTypography.body2,
        ),
      );

  // Dark theme placeholder for future implementation
  static ThemeData get dark => light.copyWith(
        brightness: Brightness.dark,
        // TODO: Implement dark mode color scheme
      );
}
