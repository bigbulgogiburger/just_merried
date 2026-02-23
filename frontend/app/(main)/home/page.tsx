'use client';

import { useEffect, useState } from 'react';

import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { getDashboardSummary } from '@/lib/api/s2';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { DashboardSummaryResponse } from '@/lib/api/s2';

export default function DashboardHomePage() {
  const toast = useAppToast();
  const [summary, setSummary] = useState<DashboardSummaryResponse | null>(null);

  useEffect(() => {
    getDashboardSummary()
      .then((res) => setSummary(res))
      .catch(() => toast.error('대시보드 조회 실패', '잠시 후 다시 시도해주세요.'));
  }, [toast]);

  return (
    <div className="wedding-container space-y-4 py-6">
      <h1 className="text-2xl font-bold">홈 대시보드</h1>
      <div className="grid gap-4 tablet:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>D-Day</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-primary">
              {summary?.weddingDate ? `D-${summary.dDay}` : '날짜 미설정'}
            </p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>준비 진행률</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-primary">
              {summary?.progressPercent ?? 0}%
            </p>
            <p className="mt-2 text-sm text-text-secondary">
              오늘 할 일 {summary?.todayTodoCount ?? 0}개 · 완료 {summary?.completedTodoCount ?? 0}개
            </p>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
