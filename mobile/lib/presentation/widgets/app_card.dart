import 'package:flutter/material.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_radius.dart';
import 'package:wedding_mate/core/theme/app_shadows.dart';
import 'package:wedding_mate/core/theme/app_spacing.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';

class AppCard extends StatelessWidget {
  const AppCard({
    super.key,
    required this.child,
    this.header,
    this.headerAction,
    this.padding,
    this.onTap,
    this.elevation = false,
  });

  final Widget child;
  final String? header;
  final Widget? headerAction;
  final EdgeInsets? padding;
  final VoidCallback? onTap;
  final bool elevation;

  @override
  Widget build(BuildContext context) {
    final content = Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: [
        if (header != null)
          Padding(
            padding: const EdgeInsets.only(
              left: AppSpacing.md,
              right: AppSpacing.md,
              top: AppSpacing.md,
              bottom: AppSpacing.sm,
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(header!, style: AppTypography.h6),
                if (headerAction != null) headerAction!,
              ],
            ),
          ),
        Padding(
          padding: padding ??
              EdgeInsets.only(
                left: AppSpacing.md,
                right: AppSpacing.md,
                top: header != null ? 0 : AppSpacing.md,
                bottom: AppSpacing.md,
              ),
          child: child,
        ),
      ],
    );

    return Container(
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: AppRadius.mdBorder,
        border: elevation ? null : Border.all(color: AppColors.border),
        boxShadow: elevation ? AppShadows.card : null,
      ),
      child: onTap != null
          ? Material(
              color: Colors.transparent,
              child: InkWell(
                onTap: onTap,
                borderRadius: AppRadius.mdBorder,
                child: content,
              ),
            )
          : content,
    );
  }
}
