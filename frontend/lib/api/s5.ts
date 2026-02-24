import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface FeedItem {
  id: number;
  userId: number;
  content: string;
  region: string;
  likeCount: number;
  commentCount: number;
  createdAt: string;
}

export interface PostDetail {
  id: number;
  userId: number;
  content: string;
  region: string;
  likeCount: number;
  commentCount: number;
  createdAt: string;
  media: Array<{ id: number; mediaUrl: string; mediaType: string; sortOrder: number }>;
  hashtags: string[];
}

export interface CommentItem {
  id: number;
  postId: number;
  userId: number;
  parentId?: number;
  content: string;
  createdAt: string;
}

export interface FollowUser {
  userId: number;
  nickname: string;
  profileImageUrl?: string;
}

export async function getGlobalFeed(page = 0, size = 20) {
  const { data } = await apiClient.get<ApiResponse<FeedItem[]>>('/community/feed', { params: { page, size } });
  return data.data;
}

export async function getFollowingFeed(page = 0, size = 20) {
  const { data } = await apiClient.get<ApiResponse<FeedItem[]>>('/community/feed/following', { params: { page, size } });
  return data.data;
}

export async function getRegionFeed(region: string, page = 0, size = 20) {
  const { data } = await apiClient.get<ApiResponse<FeedItem[]>>('/community/feed/region', { params: { region, page, size } });
  return data.data;
}

export async function createPost(payload: {
  content: string;
  region: string;
  media?: Array<{ mediaUrl: string; mediaType: string; sortOrder?: number }>;
  hashtags?: string[];
}) {
  const { data } = await apiClient.post<ApiResponse<PostDetail>>('/community/posts', payload);
  return data.data;
}

export async function getPostDetail(postId: number) {
  const { data } = await apiClient.get<ApiResponse<PostDetail>>(`/community/posts/${postId}`);
  return data.data;
}

export async function likePost(postId: number) {
  const { data } = await apiClient.post<ApiResponse<void>>(`/community/posts/${postId}/like`);
  return data;
}

export async function unlikePost(postId: number) {
  const { data } = await apiClient.delete<ApiResponse<void>>(`/community/posts/${postId}/like`);
  return data;
}

export async function getComments(postId: number) {
  const { data } = await apiClient.get<ApiResponse<CommentItem[]>>(`/community/posts/${postId}/comments`);
  return data.data;
}

export async function createComment(postId: number, payload: { parentId?: number; content: string }) {
  const { data } = await apiClient.post<ApiResponse<CommentItem>>(`/community/posts/${postId}/comments`, payload);
  return data.data;
}

export async function followUser(userId: number) {
  const { data } = await apiClient.post<ApiResponse<void>>(`/community/users/${userId}/follow`);
  return data;
}

export async function unfollowUser(userId: number) {
  const { data } = await apiClient.delete<ApiResponse<void>>(`/community/users/${userId}/follow`);
  return data;
}

export async function getFollowers(userId: number) {
  const { data } = await apiClient.get<ApiResponse<FollowUser[]>>(`/community/users/${userId}/followers`);
  return data.data;
}

export async function getFollowing(userId: number) {
  const { data } = await apiClient.get<ApiResponse<FollowUser[]>>(`/community/users/${userId}/following`);
  return data.data;
}
