'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
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
        <CardHeader>
          <CardTitle>온보딩</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
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
          <Input type="date" label="예정일" value={weddingDate} onChange={(e) => setWeddingDate(e.target.value)} />
          <Input label="지역" value={region} onChange={(e) => setRegion(e.target.value)} />
          <Input label="관심사(쉼표구분)" value={interests} onChange={(e) => setInterests(e.target.value)} />
          <Button className="w-full" onClick={onSubmit}>완료하고 홈으로</Button>
        </CardContent>
      </Card>
    </div>
  );
}
