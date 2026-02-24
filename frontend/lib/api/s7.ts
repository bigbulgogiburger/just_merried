import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface MarketCategory { id: number; name: string; slug: string; sortOrder: number; }
export interface MarketProductSummary { id: number; name: string; basePrice: number; status: string; stockQuantity: number; thumbnailUrl?: string; createdAt: string; }
export interface MarketProductDetail extends MarketProductSummary { categoryId: number; categoryName: string; description?: string; reviewCount: number; reviewAverage: number; images: {id:number; imageUrl:string; sortOrder:number;}[]; options: {id:number; optionName:string; extraPrice:number; stockQuantity:number;}[]; }
export interface SecondhandSummary { id:number; title:string; price:number; region:string; saleStatus:string; conditionStatus:string; thumbnailUrl?:string; createdAt:string; }
export interface SecondhandDetail extends SecondhandSummary { sellerUserId:number; description?:string; tradeMethod:string; images:{id:number; imageUrl:string; sortOrder:number;}[]; }
export interface WishlistItem { id:number; targetType:'MARKET_PRODUCT'|'SECONDHAND_PRODUCT'; targetId:number; createdAt:string; }
export interface MarketSearchResponse { latestMarketProducts: MarketProductSummary[]; latestSecondhandProducts: SecondhandSummary[]; popularMarketProducts: MarketProductSummary[]; popularSecondhandProducts: SecondhandSummary[]; }

export async function getMarketCategories(){ const {data}=await apiClient.get<ApiResponse<MarketCategory[]>>('/market/categories'); return data.data; }
export async function getMarketProducts(params?:{categoryId?:number;page?:number;size?:number}){ const {data}=await apiClient.get<ApiResponse<MarketProductSummary[]>>('/market/products',{params}); return data.data; }
export async function getMarketProductDetail(id:number){ const {data}=await apiClient.get<ApiResponse<MarketProductDetail>>(`/market/products/${id}`); return data.data; }
export async function getSecondhandProducts(params?:{region?:string;page?:number;size?:number}){ const {data}=await apiClient.get<ApiResponse<SecondhandSummary[]>>('/market/secondhand',{params}); return data.data; }
export async function getSecondhandDetail(id:number){ const {data}=await apiClient.get<ApiResponse<SecondhandDetail>>(`/market/secondhand/${id}`); return data.data; }
export async function createSecondhand(payload:{title:string;description?:string;price:number;region:string;conditionStatus:string;tradeMethod:string;imageUrls:string[]}){ const {data}=await apiClient.post<ApiResponse<SecondhandDetail>>('/market/secondhand',payload); return data.data; }
export async function deleteSecondhand(id:number){ await apiClient.delete(`/market/secondhand/${id}`); }
export async function updateSecondhandStatus(id:number,saleStatus:string){ const {data}=await apiClient.patch<ApiResponse<SecondhandDetail>>(`/market/secondhand/${id}/status`,{saleStatus}); return data.data; }
export async function getMySecondhand(){ const {data}=await apiClient.get<ApiResponse<SecondhandSummary[]>>('/market/secondhand/me'); return data.data; }
export async function addWishlist(targetType:'MARKET_PRODUCT'|'SECONDHAND_PRODUCT',targetId:number){ const {data}=await apiClient.post<ApiResponse<WishlistItem>>('/market/wishlist',{targetType,targetId}); return data.data; }
export async function getWishlist(){ const {data}=await apiClient.get<ApiResponse<WishlistItem[]>>('/market/wishlist'); return data.data; }
export async function removeWishlist(targetType:'MARKET_PRODUCT'|'SECONDHAND_PRODUCT',targetId:number){ await apiClient.delete('/market/wishlist',{data:{targetType,targetId}}); }
export async function searchMarket(keyword:string,params?:{region?:string;minPrice?:number;maxPrice?:number}){ const {data}=await apiClient.get<ApiResponse<MarketSearchResponse>>('/market/search',{params:{keyword,...params}}); return data.data; }
