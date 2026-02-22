import 'dart:io';

import 'package:dio/dio.dart';
import 'package:wedding_mate/core/utils/logger.dart';
import 'package:wedding_mate/domain/models/api_error.dart';

class ErrorInterceptor extends Interceptor {
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    final exception = _mapException(err);
    appLogger.e('API Error: $exception', error: err);

    handler.next(
      DioException(
        requestOptions: err.requestOptions,
        response: err.response,
        type: err.type,
        error: exception,
      ),
    );
  }

  Exception _mapException(DioException err) {
    switch (err.type) {
      case DioExceptionType.connectionTimeout:
      case DioExceptionType.sendTimeout:
      case DioExceptionType.receiveTimeout:
        return const TimeoutError();

      case DioExceptionType.connectionError:
        return const NetworkError();

      case DioExceptionType.badResponse:
        return _parseApiError(err.response);

      case DioExceptionType.cancel:
        return const NetworkError(message: 'Request cancelled');

      case DioExceptionType.badCertificate:
        return const NetworkError(message: 'Certificate error');

      case DioExceptionType.unknown:
        if (err.error is SocketException) {
          return const NetworkError();
        }
        return const NetworkError(message: 'Unknown error occurred');
    }
  }

  ApiError _parseApiError(Response<dynamic>? response) {
    if (response?.data is Map<String, dynamic>) {
      final data = response!.data as Map<String, dynamic>;
      return ApiError.fromJson({
        'statusCode': response.statusCode,
        ...data,
      });
    }

    return ApiError(
      statusCode: response?.statusCode ?? 0,
      message: response?.statusMessage ?? 'Unknown error',
    );
  }
}
