import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface VendorListItem {
  id: number;
  name: string;
  category: string;
  region: string;
  minPrice: number;
  maxPrice: number;
  ratingAvg: number;
  ratingCount: number;
  status: 'ACTIVE' | 'INACTIVE';
}

export interface VendorImage {
  id: number;
  imageUrl: string;
  isCover: boolean;
  sortOrder: number;
}

export interface VendorPrice {
  id: number;
  packageName: string;
  price: number;
  description?: string;
  sortOrder: number;
}

export interface VendorDetail {
  id: number;
  name: string;
  description?: string;
  category: string;
  region: string;
  address?: string;
  phone?: string;
  homepageUrl?: string;
  minPrice: number;
  maxPrice: number;
  ratingAvg: number;
  ratingCount: number;
  status: 'ACTIVE' | 'INACTIVE';
  images: VendorImage[];
  prices: VendorPrice[];
}

export interface VendorCompareItem {
  compareItemId: number;
  vendorId: number;
  name: string;
  category: string;
  region: string;
  minPrice: number;
  maxPrice: number;
  ratingAvg: number;
  ratingCount: number;
}

export interface VendorReview {
  id: number;
  userId: number;
  rating: number;
  content?: string;
  createdAt: string;
}

export async function listVendors(params?: {
  categoryId?: number;
  region?: string;
  minPrice?: number;
  maxPrice?: number;
  sort?: 'latest' | 'rating' | 'price_asc' | 'price_desc';
}) {
  const { data } = await apiClient.get<ApiResponse<VendorListItem[]>>('/vendors', {
    params,
  });
  return data.data;
}

export async function getVendorDetail(vendorId: number) {
  const { data } = await apiClient.get<ApiResponse<VendorDetail>>(`/vendors/${vendorId}`);
  return data.data;
}

export async function addFavorite(vendorId: number) {
  const { data } = await apiClient.post<ApiResponse<void>>(`/vendors/${vendorId}/favorite`);
  return data;
}

export async function removeFavorite(vendorId: number) {
  const { data } = await apiClient.delete<ApiResponse<void>>(`/vendors/${vendorId}/favorite`);
  return data;
}

export async function addCompare(vendorId: number) {
  const { data } = await apiClient.post<ApiResponse<void>>(`/vendors/${vendorId}/compare`);
  return data;
}

export async function removeCompare(vendorId: number) {
  const { data } = await apiClient.delete<ApiResponse<void>>(`/vendors/${vendorId}/compare`);
  return data;
}

export async function getCompareList() {
  const { data } = await apiClient.get<ApiResponse<VendorCompareItem[]>>('/vendors/compare');
  return data.data;
}

export async function createReview(vendorId: number, payload: { rating: number; content?: string }) {
  const { data } = await apiClient.post<ApiResponse<VendorReview>>(`/vendors/${vendorId}/reviews`, payload);
  return data.data;
}

export async function listReviews(vendorId: number) {
  const { data } = await apiClient.get<ApiResponse<VendorReview[]>>(`/vendors/${vendorId}/reviews`);
  return data.data;
}
