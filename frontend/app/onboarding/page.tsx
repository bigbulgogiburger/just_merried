'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { saveOnboarding } from '@/lib/api/s2';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function OnboardingPage() {
  const router = useRouter();
  const toast = useAppToast();
  const [weddingStatus, setWeddingStatus] = useState<'PREPARING' | 'IN_PROGRESS' | 'COMPLETED'>('PREPARING');
  const [weddingDate, setWeddingDate] = useState('');
  const [region, setRegion] = useState('서울');
  const [interests, setInterests] = useState('체크리스트,예산');

  const onSubmit = async () => {
    try {
      await saveOnboarding({
        weddingStatus,
        weddingDate: weddingDate || undefined,
        region,
        interests: interests.split(',').map((v) => v.trim()).filter(Boolean),
      });
      toast.success('온보딩 저장 완료', '홈 화면으로 이동합니다.');
      router.push('/home');
    } catch {
      toast.error('온보딩 저장 실패', '입력값을 확인 후 다시 시도해주세요.');
    }
  };

  return (
    <div className="mx-auto max-w-md p-4">
      <Card>
        <CardHeader className="space-y-2">
          <CardTitle>온보딩</CardTitle>
          <Badge>4단계</Badge>
        </CardHeader>
        <CardContent>
          <Tabs defaultValue="status" className="space-y-4">
            <TabsList className="grid w-full grid-cols-4">
              <TabsTrigger value="status">상태</TabsTrigger>
              <TabsTrigger value="date">날짜</TabsTrigger>
              <TabsTrigger value="region">지역</TabsTrigger>
              <TabsTrigger value="interest">관심사</TabsTrigger>
            </TabsList>

            <TabsContent value="status" className="space-y-3">
              <p className="text-sm text-text-secondary">현재 결혼 준비 단계를 선택하세요.</p>
              <div className="grid grid-cols-3 gap-2">
                {['PREPARING', 'IN_PROGRESS', 'COMPLETED'].map((status) => (
                  <Button
                    key={status}
                    variant={weddingStatus === status ? 'primary' : 'outline'}
                    onClick={() => setWeddingStatus(status as 'PREPARING' | 'IN_PROGRESS' | 'COMPLETED')}
                  >
                    {status}
                  </Button>
                ))}
              </div>
            </TabsContent>

            <TabsContent value="date">
              <Input type="date" label="예정일" value={weddingDate} onChange={(e) => setWeddingDate(e.target.value)} />
            </TabsContent>

            <TabsContent value="region">
              <Input label="지역" value={region} onChange={(e) => setRegion(e.target.value)} />
            </TabsContent>

            <TabsContent value="interest">
              <Input label="관심사(쉼표구분)" value={interests} onChange={(e) => setInterests(e.target.value)} />
            </TabsContent>
          </Tabs>

          <Button className="mt-6 w-full" onClick={onSubmit}>완료하고 홈으로</Button>
        </CardContent>
      </Card>
    </div>
  );
}
