'use client';

import { useEffect, useMemo, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import {
  Modal,
  ModalClose,
  ModalContent,
  ModalDescription,
  ModalFooter,
  ModalHeader,
  ModalTitle,
  ModalTrigger,
} from '@/components/ui/modal';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import {
  createChecklist,
  createChecklistItem,
  listChecklists,
  toggleChecklistItem,
} from '@/lib/api/s3';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { Checklist } from '@/lib/api/s3';

export default function PreparePage() {
  const toast = useAppToast();
  const [checklists, setChecklists] = useState<Checklist[]>([]);
  const [selectedChecklistId, setSelectedChecklistId] = useState<number | null>(null);

  const [newChecklistTitle, setNewChecklistTitle] = useState('');
  const [newChecklistDesc, setNewChecklistDesc] = useState('');
  const [newItemTitle, setNewItemTitle] = useState('');

  const [budgetTotal, setBudgetTotal] = useState('50000000');
  const [budgetCurrency, setBudgetCurrency] = useState('KRW');
  const [budgetStartDate, setBudgetStartDate] = useState('');
  const [budgetEndDate, setBudgetEndDate] = useState('');
  const [budgetSummary, setBudgetSummary] = useState<{
    totalBudget: number;
    totalPlanned: number;
    totalSpent: number;
    totalRemaining: number;
    categories: Array<{ id: number; name: string; plannedAmount: number; spentAmount: number; remainingAmount: number; sortOrder: number }>;
  } | null>(null);
  const [newCategoryName, setNewCategoryName] = useState('');
  const [newCategoryPlanned, setNewCategoryPlanned] = useState('0');

  const selectedChecklist = useMemo(
    () => checklists.find((c) => c.id === selectedChecklistId) ?? checklists[0],
    [checklists, selectedChecklistId],
  );

  const refresh = async () => {
    try {
      const data = await listChecklists();
      setChecklists(data);
      if (data.length && !selectedChecklistId) setSelectedChecklistId(data[0].id);
    } catch {
      toast.error('체크리스트 조회 실패');
    }
  };

  const loadBudget = async () => {
    try {
      const { getBudgetSummary } = await import('@/lib/api/s3');
      const summary = await getBudgetSummary();
      setBudgetSummary(summary);
      setBudgetTotal(String(summary.totalBudget));
      setBudgetCurrency(summary.currency);
    } catch {
      // 예산이 아직 생성되지 않은 경우 조용히 유지
    }
  };

  useEffect(() => {
    refresh();
    loadBudget();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onCreateChecklist = async () => {
    if (!newChecklistTitle.trim()) {
      toast.error('체크리스트 제목을 입력해주세요.');
      return;
    }
    try {
      await createChecklist({ title: newChecklistTitle, description: newChecklistDesc });
      setNewChecklistTitle('');
      setNewChecklistDesc('');
      toast.success('체크리스트 생성 완료');
      await refresh();
    } catch {
      toast.error('체크리스트 생성 실패');
    }
  };

  const onCreateItem = async () => {
    if (!selectedChecklist || !newItemTitle.trim()) {
      toast.error('항목 제목을 입력해주세요.');
      return;
    }
    try {
      await createChecklistItem(selectedChecklist.id, { title: newItemTitle, assignee: 'BOTH' });
      setNewItemTitle('');
      toast.success('체크 항목 추가 완료');
      await refresh();
    } catch {
      toast.error('체크 항목 추가 실패');
    }
  };

  const onToggle = async (itemId: number, completed: boolean) => {
    if (!selectedChecklist) return;
    try {
      await toggleChecklistItem(selectedChecklist.id, itemId, !completed);
      await refresh();
    } catch {
      toast.error('상태 변경 실패');
    }
  };

  const onSaveBudget = async () => {
    try {
      const { upsertBudget } = await import('@/lib/api/s3');
      const summary = await upsertBudget({
        totalBudget: Number(budgetTotal || 0),
        currency: budgetCurrency,
        startDate: budgetStartDate || undefined,
        endDate: budgetEndDate || undefined,
      });
      setBudgetSummary(summary);
      toast.success('예산 저장 완료');
    } catch {
      toast.error('예산 저장 실패');
    }
  };

  const onCreateCategory = async () => {
    if (!newCategoryName.trim()) {
      toast.error('카테고리명을 입력해주세요.');
      return;
    }
    try {
      const { createBudgetCategory, getBudgetSummary } = await import('@/lib/api/s3');
      await createBudgetCategory({ name: newCategoryName, plannedAmount: Number(newCategoryPlanned || 0), sortOrder: 0 });
      const summary = await getBudgetSummary();
      setBudgetSummary(summary);
      setNewCategoryName('');
      setNewCategoryPlanned('0');
      toast.success('카테고리 추가 완료');
    } catch {
      toast.error('카테고리 추가 실패', '먼저 예산을 저장해 주세요.');
    }
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-start justify-between gap-3">
        <div>
          <h1 className="text-2xl font-bold">결혼준비</h1>
          <p className="mt-1 text-sm text-text-secondary">체크리스트 중심으로 준비 상황을 관리해요.</p>
        </div>
        <Modal>
          <ModalTrigger asChild>
            <Button>체크리스트 생성</Button>
          </ModalTrigger>
          <ModalContent>
            <ModalHeader>
              <ModalTitle>새 체크리스트</ModalTitle>
              <ModalDescription>결혼 준비 단계에 맞는 목록을 추가해보세요.</ModalDescription>
            </ModalHeader>
            <div className="space-y-3">
              <Input label="제목" value={newChecklistTitle} onChange={(e) => setNewChecklistTitle(e.target.value)} />
              <Input label="설명" value={newChecklistDesc} onChange={(e) => setNewChecklistDesc(e.target.value)} />
            </div>
            <ModalFooter>
              <ModalClose asChild>
                <Button variant="ghost">취소</Button>
              </ModalClose>
              <ModalClose asChild>
                <Button onClick={onCreateChecklist}>생성</Button>
              </ModalClose>
            </ModalFooter>
          </ModalContent>
        </Modal>
      </div>

      <Tabs defaultValue="checklist" className="space-y-4">
        <TabsList className="grid w-full grid-cols-3">
          <TabsTrigger value="checklist">체크리스트</TabsTrigger>
          <TabsTrigger value="budget">예산</TabsTrigger>
          <TabsTrigger value="schedule">일정</TabsTrigger>
        </TabsList>

        <TabsContent value="checklist" className="space-y-4">
          <div className="grid gap-4 tablet:grid-cols-[280px_1fr]">
            <Card>
              <CardHeader>
                <CardTitle className="text-base">목록</CardTitle>
              </CardHeader>
              <CardContent className="space-y-2">
                {checklists.length === 0 ? (
                  <p className="text-sm text-text-secondary">아직 생성된 체크리스트가 없어요.</p>
                ) : (
                  checklists.map((checklist) => (
                    <button
                      key={checklist.id}
                      type="button"
                      className={`w-full rounded-md border p-3 text-left transition ${
                        selectedChecklist?.id === checklist.id
                          ? 'border-primary bg-primary/5'
                          : 'border-border hover:bg-background'
                      }`}
                      onClick={() => setSelectedChecklistId(checklist.id)}
                    >
                      <p className="font-medium">{checklist.title}</p>
                      <p className="text-xs text-text-secondary">진행률 {checklist.progressPercent}%</p>
                    </button>
                  ))
                )}
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>{selectedChecklist?.title ?? '체크리스트를 선택해 주세요'}</CardTitle>
                <CardDescription>{selectedChecklist?.description ?? '좌측 목록에서 선택 후 항목을 관리할 수 있어요.'}</CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                {selectedChecklist ? (
                  <>
                    <div className="flex items-center gap-2">
                      <Badge>진행률 {selectedChecklist.progressPercent}%</Badge>
                      <div className="h-2 flex-1 rounded-full bg-background">
                        <div
                          className="h-2 rounded-full bg-primary transition-all"
                          style={{ width: `${selectedChecklist.progressPercent}%` }}
                        />
                      </div>
                    </div>

                    <div className="flex gap-2">
                      <Input
                        placeholder="새 체크 항목 입력"
                        value={newItemTitle}
                        onChange={(e) => setNewItemTitle(e.target.value)}
                      />
                      <Button onClick={onCreateItem}>추가</Button>
                    </div>

                    <div className="space-y-2">
                      {selectedChecklist.items.length === 0 ? (
                        <p className="text-sm text-text-secondary">아직 항목이 없어요. 첫 항목을 추가해보세요.</p>
                      ) : (
                        selectedChecklist.items.map((item) => (
                          <button
                            key={item.id}
                            type="button"
                            onClick={() => onToggle(item.id, item.completed)}
                            className={`flex w-full items-center justify-between rounded-md border p-3 text-left ${
                              item.completed ? 'border-success/30 bg-success/5' : 'border-border'
                            }`}
                          >
                            <div>
                              <p className={`font-medium ${item.completed ? 'line-through text-text-muted' : ''}`}>{item.title}</p>
                              <p className="text-xs text-text-secondary">담당: {item.assignee}</p>
                            </div>
                            <Badge variant={item.completed ? 'success' : 'outline'}>
                              {item.completed ? '완료' : '미완료'}
                            </Badge>
                          </button>
                        ))
                      )}
                    </div>
                  </>
                ) : null}
              </CardContent>
            </Card>
          </div>
        </TabsContent>

        <TabsContent value="budget" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>예산 설정</CardTitle>
              <CardDescription>총 예산과 통화, 기간을 먼저 저장하세요.</CardDescription>
            </CardHeader>
            <CardContent className="grid gap-3 tablet:grid-cols-2">
              <Input label="총 예산" type="number" value={budgetTotal} onChange={(e) => setBudgetTotal(e.target.value)} />
              <Input label="통화" value={budgetCurrency} onChange={(e) => setBudgetCurrency(e.target.value)} />
              <Input label="시작일" type="date" value={budgetStartDate} onChange={(e) => setBudgetStartDate(e.target.value)} />
              <Input label="종료일" type="date" value={budgetEndDate} onChange={(e) => setBudgetEndDate(e.target.value)} />
              <div className="tablet:col-span-2">
                <Button onClick={onSaveBudget}>예산 저장</Button>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>카테고리</CardTitle>
              <CardDescription>예산 카테고리를 추가하고 계획 금액을 설정하세요.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-3">
              <div className="grid gap-2 tablet:grid-cols-[1fr_180px_auto]">
                <Input label="카테고리명" value={newCategoryName} onChange={(e) => setNewCategoryName(e.target.value)} />
                <Input label="계획 금액" type="number" value={newCategoryPlanned} onChange={(e) => setNewCategoryPlanned(e.target.value)} />
                <div className="self-end">
                  <Button onClick={onCreateCategory}>추가</Button>
                </div>
              </div>

              {budgetSummary ? (
                <>
                  <div className="grid gap-2 tablet:grid-cols-4">
                    <Badge>총예산 {budgetSummary.totalBudget.toLocaleString()}</Badge>
                    <Badge variant="info">계획합 {budgetSummary.totalPlanned.toLocaleString()}</Badge>
                    <Badge variant="warning">지출합 {budgetSummary.totalSpent.toLocaleString()}</Badge>
                    <Badge variant="success">잔여 {budgetSummary.totalRemaining.toLocaleString()}</Badge>
                  </div>
                  <div className="space-y-2">
                    {budgetSummary.categories.length === 0 ? (
                      <p className="text-sm text-text-secondary">카테고리를 추가해 주세요.</p>
                    ) : (
                      budgetSummary.categories.map((category) => (
                        <div key={category.id} className="rounded-md border border-border p-3">
                          <p className="font-medium">{category.name}</p>
                          <p className="text-xs text-text-secondary">
                            계획 {category.plannedAmount.toLocaleString()} / 지출 {category.spentAmount.toLocaleString()} / 잔여 {category.remainingAmount.toLocaleString()}
                          </p>
                        </div>
                      ))
                    )}
                  </div>
                </>
              ) : (
                <p className="text-sm text-text-secondary">예산 저장 후 요약을 확인할 수 있어요.</p>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="schedule">
          <Card>
            <CardHeader>
              <CardTitle>일정</CardTitle>
              <CardDescription>S3-7에서 캘린더 UI와 함께 연결합니다.</CardDescription>
            </CardHeader>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
}
