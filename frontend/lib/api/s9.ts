import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface LifeDashboardResponse { dDay:number; upcomingEventCount:number; newHomeTotal:number; newHomeCompleted:number; }
export interface FamilyMember { id:number; name:string; relation:string; birthDate:string; lunar:boolean; }
export interface FamilyEvent { id:number; title:string; eventDate:string; lunar:boolean; familyMemberId?:number; remindDaysBefore:number; repeatYearly:boolean; }
export interface NewHomeItem { id:number; category:'MOVE'|'APPLIANCE'|'FURNITURE'|'INTERIOR'; title:string; memo?:string; completed:boolean; assignee?:string; }

export async function getLifeDashboard(){ const {data}=await apiClient.get<ApiResponse<LifeDashboardResponse>>('/life/dashboard'); return data.data; }
export async function getFamilyMembers(){ const {data}=await apiClient.get<ApiResponse<FamilyMember[]>>('/life/family/members'); return data.data; }
export async function createFamilyMember(payload:{name:string;relation:string;birthDate:string;lunar:boolean}){ const {data}=await apiClient.post<ApiResponse<FamilyMember>>('/life/family/members', payload); return data.data; }
export async function deleteFamilyMember(id:number){ await apiClient.delete(`/life/family/members/${id}`); }

export async function getFamilyEvents(){ const {data}=await apiClient.get<ApiResponse<FamilyEvent[]>>('/life/family/events'); return data.data; }
export async function createFamilyEvent(payload:{title:string;eventDate:string;lunar:boolean;familyMemberId?:number;remindDaysBefore?:number;repeatYearly?:boolean}){ const {data}=await apiClient.post<ApiResponse<FamilyEvent>>('/life/family/events', payload); return data.data; }
export async function deleteFamilyEvent(id:number){ await apiClient.delete(`/life/family/events/${id}`); }

export async function getNewHomeItems(category?:string){ const {data}=await apiClient.get<ApiResponse<NewHomeItem[]>>('/life/newhome', { params: category ? { category } : undefined }); return data.data; }
export async function createNewHomeItem(payload:{category:'MOVE'|'APPLIANCE'|'FURNITURE'|'INTERIOR'; title:string; memo?:string; completed?:boolean; assignee?:string}){ const {data}=await apiClient.post<ApiResponse<NewHomeItem>>('/life/newhome', payload); return data.data; }
export async function updateNewHomeItem(id:number,payload:{category:'MOVE'|'APPLIANCE'|'FURNITURE'|'INTERIOR'; title:string; memo?:string; completed?:boolean; assignee?:string}){ const {data}=await apiClient.put<ApiResponse<NewHomeItem>>(`/life/newhome/${id}`, payload); return data.data; }
