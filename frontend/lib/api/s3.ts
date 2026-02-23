import { apiClient } from '@/lib/api/client';

import type { ApiResponse } from '@/types/api';

export interface ChecklistItem {
  id: number;
  title: string;
  description?: string;
  assignee: 'GROOM' | 'BRIDE' | 'BOTH';
  completed: boolean;
  completedAt?: string;
  sortOrder: number;
}

export interface Checklist {
  id: number;
  title: string;
  description?: string;
  startDate?: string;
  dueDate?: string;
  status: 'ACTIVE' | 'ARCHIVED';
  items: ChecklistItem[];
  progressPercent: number;
}

export interface BudgetCategorySummary {
  id: number;
  name: string;
  plannedAmount: number;
  spentAmount: number;
  remainingAmount: number;
  sortOrder: number;
}

export interface BudgetSummary {
  budgetId: number;
  totalBudget: number;
  totalPlanned: number;
  totalSpent: number;
  totalRemaining: number;
  currency: string;
  categories: BudgetCategorySummary[];
}

export interface ScheduleItem {
  id: number;
  title: string;
  description?: string;
  startAt: string;
  endAt: string;
  allDay: boolean;
  sharedWithCouple: boolean;
  reminderMinutes?: number;
  status: 'PLANNED' | 'DONE' | 'CANCELED';
}

export async function listChecklists() {
  const { data } = await apiClient.get<ApiResponse<Checklist[]>>('/checklists');
  return data.data;
}

export async function createChecklist(payload: {
  title: string;
  description?: string;
  startDate?: string;
  dueDate?: string;
}) {
  const { data } = await apiClient.post<ApiResponse<Checklist>>('/checklists', payload);
  return data.data;
}

export async function createChecklistItem(
  checklistId: number,
  payload: {
    title: string;
    description?: string;
    assignee?: 'GROOM' | 'BRIDE' | 'BOTH';
    sortOrder?: number;
  },
) {
  const { data } = await apiClient.post<ApiResponse<ChecklistItem>>(
    `/checklists/${checklistId}/items`,
    payload,
  );
  return data.data;
}

export async function toggleChecklistItem(
  checklistId: number,
  itemId: number,
  completed: boolean,
) {
  const { data } = await apiClient.patch<ApiResponse<ChecklistItem>>(
    `/checklists/${checklistId}/items/${itemId}/toggle`,
    { completed },
  );
  return data.data;
}

export async function upsertBudget(payload: {
  totalBudget: number;
  currency: string;
  startDate?: string;
  endDate?: string;
}) {
  const { data } = await apiClient.put<ApiResponse<BudgetSummary>>('/budgets/me', payload);
  return data.data;
}

export async function getBudgetSummary() {
  const { data } = await apiClient.get<ApiResponse<BudgetSummary>>('/budgets/me');
  return data.data;
}

export async function createBudgetCategory(payload: {
  name: string;
  plannedAmount: number;
  sortOrder?: number;
}) {
  const { data } = await apiClient.post<ApiResponse<BudgetCategorySummary>>('/budgets/categories', payload);
  return data.data;
}

export async function listSchedules(params?: { from?: string; to?: string }) {
  const { data } = await apiClient.get<ApiResponse<ScheduleItem[]>>('/schedules', {
    params,
  });
  return data.data;
}

export async function createSchedule(payload: {
  title: string;
  description?: string;
  startAt: string;
  endAt: string;
  allDay?: boolean;
  sharedWithCouple?: boolean;
  reminderMinutes?: number;
}) {
  const { data } = await apiClient.post<ApiResponse<ScheduleItem>>('/schedules', payload);
  return data.data;
}

export async function updateSchedule(
  scheduleId: number,
  payload: Partial<{
    title: string;
    description?: string;
    startAt: string;
    endAt: string;
    allDay: boolean;
    sharedWithCouple: boolean;
    reminderMinutes: number;
    status: 'PLANNED' | 'DONE' | 'CANCELED';
  }>,
) {
  const { data } = await apiClient.put<ApiResponse<ScheduleItem>>(`/schedules/${scheduleId}`, payload);
  return data.data;
}

export async function deleteSchedule(scheduleId: number) {
  const { data } = await apiClient.delete<ApiResponse<void>>(`/schedules/${scheduleId}`);
  return data;
}
