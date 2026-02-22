import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:wedding_mate/core/constants/app_constants.dart';
import 'package:wedding_mate/core/utils/logger.dart';
import 'package:wedding_mate/data/remote/auth_interceptor.dart';
import 'package:wedding_mate/data/remote/error_interceptor.dart';

final dioProvider = Provider<Dio>((ref) {
  final dio = Dio(
    BaseOptions(
      baseUrl: AppConstants.baseUrl,
      connectTimeout: AppConstants.connectionTimeout,
      receiveTimeout: AppConstants.receiveTimeout,
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
    ),
  );

  dio.interceptors.addAll([
    AuthInterceptor(dio: dio, ref: ref),
    ErrorInterceptor(),
    LogInterceptor(
      requestBody: true,
      responseBody: true,
      logPrint: (obj) => appLogger.d(obj.toString()),
    ),
  ]);

  return dio;
});
