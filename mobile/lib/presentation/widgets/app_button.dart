import 'package:flutter/material.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_radius.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';

enum AppButtonVariant { primary, secondary, outline, ghost }
enum AppButtonSize { small, medium, large }

class AppButton extends StatelessWidget {
  const AppButton({
    super.key,
    required this.label,
    this.onPressed,
    this.variant = AppButtonVariant.primary,
    this.size = AppButtonSize.medium,
    this.isLoading = false,
    this.isExpanded = false,
    this.prefixIcon,
    this.suffixIcon,
  });

  final String label;
  final VoidCallback? onPressed;
  final AppButtonVariant variant;
  final AppButtonSize size;
  final bool isLoading;
  final bool isExpanded;
  final IconData? prefixIcon;
  final IconData? suffixIcon;

  bool get _isDisabled => onPressed == null || isLoading;

  double get _height {
    switch (size) {
      case AppButtonSize.small:
        return 36;
      case AppButtonSize.medium:
        return 48;
      case AppButtonSize.large:
        return 56;
    }
  }

  EdgeInsets get _padding {
    switch (size) {
      case AppButtonSize.small:
        return const EdgeInsets.symmetric(horizontal: 12);
      case AppButtonSize.medium:
        return const EdgeInsets.symmetric(horizontal: 20);
      case AppButtonSize.large:
        return const EdgeInsets.symmetric(horizontal: 24);
    }
  }

  TextStyle get _textStyle {
    switch (size) {
      case AppButtonSize.small:
        return AppTypography.buttonSmall;
      case AppButtonSize.medium:
      case AppButtonSize.large:
        return AppTypography.button;
    }
  }

  Color get _backgroundColor {
    if (_isDisabled) {
      switch (variant) {
        case AppButtonVariant.primary:
          return AppColors.primaryLight.withValues(alpha: 0.5);
        case AppButtonVariant.secondary:
          return AppColors.border;
        case AppButtonVariant.outline:
        case AppButtonVariant.ghost:
          return Colors.transparent;
      }
    }
    switch (variant) {
      case AppButtonVariant.primary:
        return AppColors.primaryRoseGold;
      case AppButtonVariant.secondary:
        return AppColors.secondaryIvory;
      case AppButtonVariant.outline:
      case AppButtonVariant.ghost:
        return Colors.transparent;
    }
  }

  Color get _foregroundColor {
    if (_isDisabled) {
      return AppColors.textMuted;
    }
    switch (variant) {
      case AppButtonVariant.primary:
        return Colors.white;
      case AppButtonVariant.secondary:
        return AppColors.primaryRoseGold;
      case AppButtonVariant.outline:
        return AppColors.primaryRoseGold;
      case AppButtonVariant.ghost:
        return AppColors.primaryRoseGold;
    }
  }

  BorderSide? get _border {
    if (variant == AppButtonVariant.outline) {
      return BorderSide(
        color: _isDisabled ? AppColors.border : AppColors.primaryRoseGold,
      );
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    final child = Row(
      mainAxisSize: isExpanded ? MainAxisSize.max : MainAxisSize.min,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        if (isLoading) ...[
          SizedBox(
            width: 18,
            height: 18,
            child: CircularProgressIndicator(
              strokeWidth: 2,
              valueColor: AlwaysStoppedAnimation<Color>(_foregroundColor),
            ),
          ),
          const SizedBox(width: 8),
        ] else if (prefixIcon != null) ...[
          Icon(prefixIcon, size: 20, color: _foregroundColor),
          const SizedBox(width: 8),
        ],
        Text(
          label,
          style: _textStyle.copyWith(color: _foregroundColor),
        ),
        if (suffixIcon != null && !isLoading) ...[
          const SizedBox(width: 8),
          Icon(suffixIcon, size: 20, color: _foregroundColor),
        ],
      ],
    );

    return SizedBox(
      width: isExpanded ? double.infinity : null,
      height: _height,
      child: Material(
        color: _backgroundColor,
        borderRadius: AppRadius.smBorder,
        child: InkWell(
          onTap: _isDisabled ? null : onPressed,
          borderRadius: AppRadius.smBorder,
          child: Container(
            padding: _padding,
            decoration: BoxDecoration(
              borderRadius: AppRadius.smBorder,
              border: _border != null ? Border.fromBorderSide(_border!) : null,
            ),
            child: child,
          ),
        ),
      ),
    );
  }
}
