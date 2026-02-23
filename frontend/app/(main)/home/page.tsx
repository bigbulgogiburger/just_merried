'use client';

import { useEffect, useMemo, useState } from 'react';

import { StatCard } from '@/components/feature/stat-card';
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

  const progress = summary?.progressPercent ?? 0;
  const progressLabel = useMemo(() => {
    if (progress >= 80) return '마무리 단계예요. 체크리스트 정리를 추천해요.';
    if (progress >= 40) return '중간 단계예요. 예산/일정 점검을 진행해요.';
    return '초기 단계예요. 온보딩 정보부터 채워보세요.';
  }, [progress]);

  return (
    <div className="wedding-container space-y-4 py-6">
      <div>
        <h1 className="text-2xl font-bold">홈 대시보드</h1>
        <p className="mt-1 text-sm text-text-secondary">결혼 준비 상태를 한눈에 확인해요.</p>
      </div>

      <div className="grid gap-4 tablet:grid-cols-2">
        <StatCard
          title="D-Day"
          value={summary?.weddingDate ? `D-${summary.dDay}` : '날짜 미설정'}
          helper={summary?.weddingDate ? `예정일 ${summary.weddingDate}` : '온보딩에서 예정일을 입력해 주세요.'}
        />
        <StatCard
          title="준비 진행률"
          value={`${progress}%`}
          helper={`오늘 할 일 ${summary?.todayTodoCount ?? 0}개 · 완료 ${summary?.completedTodoCount ?? 0}개`}
        />
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">진행 상태</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="h-3 w-full rounded-full bg-background">
            <div
              className="h-3 rounded-full bg-primary transition-all"
              style={{ width: `${Math.min(100, Math.max(0, progress))}%` }}
            />
          </div>
          <p className="mt-2 text-sm text-text-secondary">{progressLabel}</p>
        </CardContent>
      </Card>
    </div>
  );
}
