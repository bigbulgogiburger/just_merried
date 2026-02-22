import 'package:flutter/material.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_radius.dart';
import 'package:wedding_mate/core/theme/app_spacing.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';

class AppBottomSheet extends StatelessWidget {
  const AppBottomSheet({
    super.key,
    required this.child,
    this.title,
    this.maxHeightFraction = 0.9,
  });

  final Widget child;
  final String? title;
  final double maxHeightFraction;

  static Future<T?> show<T>({
    required BuildContext context,
    required Widget child,
    String? title,
    double maxHeightFraction = 0.9,
    bool isDismissible = true,
    bool enableDrag = true,
  }) {
    return showModalBottomSheet<T>(
      context: context,
      isScrollControlled: true,
      isDismissible: isDismissible,
      enableDrag: enableDrag,
      backgroundColor: Colors.transparent,
      builder: (context) => AppBottomSheet(
        title: title,
        maxHeightFraction: maxHeightFraction,
        child: child,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      constraints: BoxConstraints(
        maxHeight: MediaQuery.of(context).size.height * maxHeightFraction,
      ),
      decoration: const BoxDecoration(
        color: AppColors.surface,
        borderRadius: AppRadius.topXl,
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          const SizedBox(height: AppSpacing.sm),
          // Drag handle
          Container(
            width: 36,
            height: 4,
            decoration: BoxDecoration(
              color: AppColors.border,
              borderRadius: AppRadius.fullBorder,
            ),
          ),
          if (title != null) ...[
            Padding(
              padding: const EdgeInsets.all(AppSpacing.md),
              child: Text(title!, style: AppTypography.h5),
            ),
            const Divider(height: 1),
          ] else
            const SizedBox(height: AppSpacing.sm),
          Flexible(child: child),
        ],
      ),
    );
  }
}
