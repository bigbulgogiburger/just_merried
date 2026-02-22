import 'package:flutter/material.dart';
import 'package:wedding_mate/core/theme/app_colors.dart';
import 'package:wedding_mate/core/theme/app_typography.dart';

class WeddingPrepTab extends StatelessWidget {
  const WeddingPrepTab({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('웨딩준비'),
      ),
      body: const Center(
        child: Text('웨딩준비', style: AppTypography.h4),
      ),
    );
  }
}
