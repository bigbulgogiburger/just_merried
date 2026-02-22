import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:wedding_mate/core/router/route_names.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_spacing.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';
import 'package:wedding_mate/presentation/widgets/app_button.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(
            horizontal: AppSpacing.screenHorizontal,
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Spacer(),
              Text(
                'WeddingMate',
                style: AppTypography.h2.copyWith(
                  color: AppColors.primaryRoseGold,
                ),
              ),
              const SizedBox(height: AppSpacing.sm),
              Text(
                '간편하게 로그인하세요',
                style: AppTypography.body1.copyWith(
                  color: AppColors.textSecondary,
                ),
              ),
              const Spacer(),
              // TODO: Implement social login buttons
              AppButton(
                label: '카카오로 시작하기',
                isExpanded: true,
                prefixIcon: Icons.chat_bubble,
                onPressed: () {
                  // TODO: Implement Kakao login
                  context.goNamed(RouteNames.home);
                },
              ),
              const SizedBox(height: AppSpacing.sm),
              AppButton(
                label: 'Apple로 시작하기',
                isExpanded: true,
                variant: AppButtonVariant.outline,
                prefixIcon: Icons.apple,
                onPressed: () {
                  // TODO: Implement Apple login
                  context.goNamed(RouteNames.home);
                },
              ),
              const SizedBox(height: AppSpacing.xl),
            ],
          ),
        ),
      ),
    );
  }
}
