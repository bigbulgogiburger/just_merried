import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface DmRoom {
  roomId: number;
  counterpartUserId: number;
  counterpartNickname: string;
  counterpartProfileImageUrl?: string;
  joinedAt: string;
}

export interface DmMessage {
  id: number;
  roomId: number;
  senderId: number;
  content: string;
  messageType: 'TEXT' | 'IMAGE' | 'SYSTEM';
  createdAt: string;
}

export async function createOrReuseDmRoom(targetUserId: number) {
  const { data } = await apiClient.post<ApiResponse<DmRoom>>('/community/dm/rooms', { targetUserId });
  return data.data;
}

export async function getMyDmRooms() {
  const { data } = await apiClient.get<ApiResponse<DmRoom[]>>('/community/dm/rooms');
  return data.data;
}

export async function getDmMessages(roomId: number) {
  const { data } = await apiClient.get<ApiResponse<DmMessage[]>>(`/community/dm/rooms/${roomId}/messages`);
  return data.data;
}

export async function sendDmMessage(roomId: number, content: string) {
  const { data } = await apiClient.post<ApiResponse<DmMessage>>(`/community/dm/rooms/${roomId}/messages`, { content });
  return data.data;
}
