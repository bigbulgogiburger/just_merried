import type { ErrorResponse } from '@/types/api';
import type { AxiosError } from 'axios';

export class ApiError extends Error {
  code: string;
  status: number;
  details?: Record<string, string[]>;

  constructor(
    message: string,
    code: string,
    status: number,
    details?: Record<string, string[]>,
  ) {
    super(message);
    this.name = 'ApiError';
    this.code = code;
    this.status = status;
    this.details = details;
  }
}

export function handleApiError(error: unknown): ApiError {
  if (error instanceof ApiError) {
    return error;
  }

  const axiosError = error as AxiosError<ErrorResponse>;

  if (axiosError.response?.data?.error) {
    const { code, message, details } = axiosError.response.data.error;
    return new ApiError(
      message,
      code,
      axiosError.response.status,
      details,
    );
  }

  if (axiosError.response) {
    return new ApiError(
      '서버 오류가 발생했습니다.',
      'SERVER_ERROR',
      axiosError.response.status,
    );
  }

  if (axiosError.request) {
    return new ApiError(
      '네트워크 연결을 확인해주세요.',
      'NETWORK_ERROR',
      0,
    );
  }

  return new ApiError(
    '알 수 없는 오류가 발생했습니다.',
    'UNKNOWN_ERROR',
    0,
  );
}
