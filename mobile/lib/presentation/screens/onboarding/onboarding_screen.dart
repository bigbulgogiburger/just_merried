import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:wedding_mate/core/router/route_names.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_spacing.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';
import 'package:wedding_mate/presentation/widgets/app_button.dart';

class OnboardingScreen extends StatelessWidget {
  const OnboardingScreen({super.key});

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
              // TODO: Add onboarding content / page view
              Text(
                '함께 준비하는\n우리의 결혼',
                style: AppTypography.h2,
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: AppSpacing.md),
              Text(
                '웨딩메이트와 함께 특별한 결혼을 준비하세요',
                style: AppTypography.body1.copyWith(
                  color: AppColors.textSecondary,
                ),
                textAlign: TextAlign.center,
              ),
              const Spacer(),
              AppButton(
                label: '시작하기',
                isExpanded: true,
                onPressed: () => context.goNamed(RouteNames.login),
              ),
              const SizedBox(height: AppSpacing.xl),
            ],
          ),
        ),
      ),
    );
  }
}
