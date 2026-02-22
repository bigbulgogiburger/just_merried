class ApiError implements Exception {
  const ApiError({
    required this.statusCode,
    required this.message,
    this.code,
    this.details,
  });

  final int statusCode;
  final String message;
  final String? code;
  final dynamic details;

  factory ApiError.fromJson(Map<String, dynamic> json) {
    return ApiError(
      statusCode: json['statusCode'] as int? ?? 0,
      message: json['message'] as String? ?? 'Unknown error',
      code: json['code'] as String?,
      details: json['details'],
    );
  }

  bool get isUnauthorized => statusCode == 401;
  bool get isForbidden => statusCode == 403;
  bool get isNotFound => statusCode == 404;
  bool get isServerError => statusCode >= 500;

  @override
  String toString() => 'ApiError($statusCode): $message';
}

class NetworkError implements Exception {
  const NetworkError({this.message = 'Network connection error'});

  final String message;

  @override
  String toString() => 'NetworkError: $message';
}

class TimeoutError implements Exception {
  const TimeoutError({this.message = 'Request timeout'});

  final String message;

  @override
  String toString() => 'TimeoutError: $message';
}
