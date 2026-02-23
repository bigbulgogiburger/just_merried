'use client';

import { useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { connectCouple, createInviteCode, disconnectCouple } from '@/lib/api/s2';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function CouplePage() {
  const toast = useAppToast();
  const [inviteCode, setInviteCode] = useState('');
  const [generatedCode, setGeneratedCode] = useState('');
  const [role, setRole] = useState<'GROOM' | 'BRIDE'>('BRIDE');

  const onGenerate = async () => {
    try {
      const code = await createInviteCode();
      setGeneratedCode(code);
      toast.success('초대코드 생성 완료', `코드: ${code}`);
    } catch {
      toast.error('초대코드 생성 실패');
    }
  };

  const onConnect = async () => {
    try {
      await connectCouple({ inviteCode, role });
      toast.success('커플 연동 완료');
    } catch {
      toast.error('커플 연동 실패', '코드를 확인해주세요.');
    }
  };

  const onDisconnect = async () => {
    try {
      await disconnectCouple();
      setGeneratedCode('');
      setInviteCode('');
      toast.info('커플 연동 해제 완료');
    } catch {
      toast.error('연동 해제 실패');
    }
  };

  return (
    <div className="wedding-container py-6">
      <Card className="max-w-xl">
        <CardHeader>
          <CardTitle>커플 연동</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="flex items-center gap-2">
            <Badge variant="info">내 역할</Badge>
            <div className="flex gap-2">
              <Button variant={role === 'BRIDE' ? 'primary' : 'outline'} onClick={() => setRole('BRIDE')}>BRIDE</Button>
              <Button variant={role === 'GROOM' ? 'primary' : 'outline'} onClick={() => setRole('GROOM')}>GROOM</Button>
            </div>
          </div>

          <Button variant="secondary" onClick={onGenerate}>초대코드 생성</Button>
          {generatedCode && <p className="text-lg font-semibold">생성 코드: {generatedCode}</p>}

          <Input label="연동 코드 입력" value={inviteCode} onChange={(e) => setInviteCode(e.target.value)} maxLength={6} />
          <div className="flex gap-2">
            <Button onClick={onConnect}>연동하기</Button>
            <Button variant="ghost" onClick={onDisconnect}>연동해제</Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
