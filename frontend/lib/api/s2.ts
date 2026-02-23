import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export type WeddingStatus = 'PREPARING' | 'IN_PROGRESS' | 'COMPLETED';
export type CoupleRole = 'GROOM' | 'BRIDE';

export interface MeResponse {
  id: number;
  email: string;
  nickname: string;
  profileImageUrl?: string;
  weddingStatus: WeddingStatus;
  weddingDate?: string;
  region?: string;
  role: string;
  grade: string;
}

export async function getMe() {
  const { data } = await apiClient.get<ApiResponse<MeResponse>>('/users/me');
  return data.data;
}

export async function updateMe(payload: Partial<MeResponse>) {
  const { data } = await apiClient.put<ApiResponse<MeResponse>>('/users/me', payload);
  return data.data;
}

export async function saveOnboarding(payload: {
  weddingStatus: WeddingStatus;
  weddingDate?: string;
  region: string;
  interests: string[];
}) {
  const { data } = await apiClient.post<ApiResponse<void>>('/auth/onboarding', payload);
  return data;
}

export async function createInviteCode() {
  const { data } = await apiClient.post<ApiResponse<{ inviteCode: string }>>('/auth/couple/invite');
  return data.data.inviteCode;
}

export async function connectCouple(payload: { inviteCode: string; role: CoupleRole }) {
  const { data } = await apiClient.post<ApiResponse<void>>('/auth/couple/connect', payload);
  return data;
}

export async function disconnectCouple() {
  const { data } = await apiClient.delete<ApiResponse<void>>('/auth/couple');
  return data;
}

export async function socialLogin(provider: 'kakao' | 'naver' | 'google' | 'apple', accessToken: string) {
  const { data } = await apiClient.post<ApiResponse<{ accessToken: string; refreshToken: string }>>(
    `/auth/login/${provider}`,
    { accessToken },
  );
  return data.data;
}
