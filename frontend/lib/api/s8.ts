import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface PaymentResponse { id:number; amount:number; status:string; provider:string; approvedAt?:string; }
export interface EscrowResponse { id:number; secondhandProductId:number; amount:number; status:string; carrier?:string; trackingNumber?:string; autoConfirmDueAt?:string; }
export interface OfferResponse { id:number; secondhandProductId:number; offeredPrice:number; status:string; message?:string; }
export interface TradeReviewResponse { id:number; revieweeUserId:number; averageScore:number; content?:string; createdAt:string; }
export interface SubscriptionResponse { id:number; planType:'FREE'|'BASIC'; status:string; autoRenew:boolean; renewAt?:string; }
export interface SettlementResponse { id:number; settlementType:string; grossAmount:number; feeAmount:number; netAmount:number; status:string; }

export async function requestPayment(payload:{orderType:string;orderId?:number;amount:number;paymentMethod:string;provider?:string}){ const {data}=await apiClient.post<ApiResponse<PaymentResponse>>('/commerce/payments',payload); return data.data; }
export async function createEscrow(payload:{secondhandProductId:number;amount:number}){ const {data}=await apiClient.post<ApiResponse<EscrowResponse>>('/commerce/escrow',payload); return data.data; }
export async function shipEscrow(escrowId:number,payload:{carrier:string;trackingNumber:string}){ const {data}=await apiClient.patch<ApiResponse<EscrowResponse>>(`/commerce/escrow/${escrowId}/ship`,payload); return data.data; }
export async function confirmEscrow(escrowId:number){ const {data}=await apiClient.patch<ApiResponse<EscrowResponse>>(`/commerce/escrow/${escrowId}/confirm`); return data.data; }
export async function getMyEscrow(){ const {data}=await apiClient.get<ApiResponse<EscrowResponse[]>>('/commerce/escrow/me'); return data.data; }
export async function createOffer(payload:{secondhandProductId:number;offeredPrice:number;message?:string}){ const {data}=await apiClient.post<ApiResponse<OfferResponse>>('/commerce/offers',payload); return data.data; }
export async function respondOffer(offerId:number,status:'ACCEPTED'|'REJECTED'){ const {data}=await apiClient.patch<ApiResponse<OfferResponse>>(`/commerce/offers/${offerId}`,{status}); return data.data; }
export async function getMyOffers(){ const {data}=await apiClient.get<ApiResponse<OfferResponse[]>>('/commerce/offers/me'); return data.data; }
export async function createTradeReview(payload:{escrowTransactionId:number;revieweeUserId:number;scoreKindness:number;scorePunctuality:number;scoreQuality:number;content?:string}){ const {data}=await apiClient.post<ApiResponse<TradeReviewResponse>>('/commerce/reviews',payload); return data.data; }
export async function subscribeBasic(payload:{paymentId:number}){ const {data}=await apiClient.post<ApiResponse<SubscriptionResponse>>('/commerce/subscriptions',{planType:'BASIC',paymentId:payload.paymentId}); return data.data; }
export async function getMySubscription(){ const {data}=await apiClient.get<ApiResponse<SubscriptionResponse>>('/commerce/subscriptions/me'); return data.data; }
export async function cancelSubscription(){ const {data}=await apiClient.post<ApiResponse<SubscriptionResponse>>('/commerce/subscriptions/cancel'); return data.data; }
export async function getMySettlements(){ const {data}=await apiClient.get<ApiResponse<SettlementResponse[]>>('/commerce/settlements/me'); return data.data; }
export async function createBusinessProduct(payload:{name:string;basePrice:number;description?:string;categoryId:number}){ const {data}=await apiClient.post<ApiResponse<number>>('/commerce/business/products',payload); return data.data; }
export async function getBusinessStats(){ const {data}=await apiClient.get<ApiResponse<{dailySales:number;weeklySales:number;monthlySales:number;settlementCount:number}>>('/commerce/business/stats'); return data.data; }
