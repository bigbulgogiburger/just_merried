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
