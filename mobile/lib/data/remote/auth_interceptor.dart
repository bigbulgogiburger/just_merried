import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:wedding_mate/core/utils/logger.dart';
import 'package:wedding_mate/data/local/auth_local_source.dart';
import 'package:wedding_mate/data/remote/api_endpoints.dart';

class AuthInterceptor extends Interceptor {
  AuthInterceptor({
    required this.dio,
    required this.ref,
  });

  final Dio dio;
  final Ref ref;
  bool _isRefreshing = false;

  @override
  Future<void> onRequest(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    final authLocal = ref.read(authLocalSourceProvider);
    final accessToken = await authLocal.getAccessToken();

    if (accessToken != null) {
      options.headers['Authorization'] = 'Bearer $accessToken';
    }

    handler.next(options);
  }

  @override
  Future<void> onError(
    DioException err,
    ErrorInterceptorHandler handler,
  ) async {
    if (err.response?.statusCode != 401 || _isRefreshing) {
      handler.next(err);
      return;
    }

    _isRefreshing = true;

    try {
      final authLocal = ref.read(authLocalSourceProvider);
      final refreshToken = await authLocal.getRefreshToken();

      if (refreshToken == null) {
        await _handleAuthFailure();
        handler.next(err);
        return;
      }

      final response = await dio.post(
        ApiEndpoints.refresh,
        data: {'refreshToken': refreshToken},
        options: Options(
          headers: {'Authorization': ''},
        ),
      );

      final newAccessToken = response.data['data']['accessToken'] as String;
      final newRefreshToken = response.data['data']['refreshToken'] as String;

      await authLocal.saveTokens(
        accessToken: newAccessToken,
        refreshToken: newRefreshToken,
      );

      // Retry the original request
      final retryOptions = err.requestOptions;
      retryOptions.headers['Authorization'] = 'Bearer $newAccessToken';

      final retryResponse = await dio.fetch(retryOptions);
      handler.resolve(retryResponse);
    } on DioException {
      await _handleAuthFailure();
      handler.next(err);
    } finally {
      _isRefreshing = false;
    }
  }

  Future<void> _handleAuthFailure() async {
    appLogger.w('Auth refresh failed, clearing tokens');
    final authLocal = ref.read(authLocalSourceProvider);
    await authLocal.clearTokens();
    // TODO: Navigate to login screen
  }
}
