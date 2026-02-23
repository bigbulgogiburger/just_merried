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

  useEffect(() => {
    refresh();
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

        <TabsContent value="budget">
          <Card>
            <CardHeader>
              <CardTitle>예산</CardTitle>
              <CardDescription>S3-6에서 화면 연동을 마무리합니다.</CardDescription>
            </CardHeader>
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
