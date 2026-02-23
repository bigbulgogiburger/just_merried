'use client';

import { useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { BottomSheet } from '@/components/ui/bottom-sheet';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
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
import { connectCouple, createInviteCode, disconnectCouple } from '@/lib/api/s2';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function CouplePage() {
  const toast = useAppToast();
  const [inviteCode, setInviteCode] = useState('');
  const [generatedCode, setGeneratedCode] = useState('');
  const [role, setRole] = useState<'GROOM' | 'BRIDE'>('BRIDE');
  const [shareSheetOpen, setShareSheetOpen] = useState(false);

  const onGenerate = async () => {
    try {
      const code = await createInviteCode();
      setGeneratedCode(code);
      setShareSheetOpen(true);
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
        <CardContent className="space-y-5">
          <Tabs defaultValue="invite" className="space-y-4">
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="invite">초대코드 생성</TabsTrigger>
              <TabsTrigger value="connect">코드로 연동</TabsTrigger>
            </TabsList>

            <TabsContent value="invite" className="space-y-3">
              <div className="flex items-center gap-2">
                <Badge variant="info">내 역할</Badge>
                <div className="flex gap-2">
                  <Button variant={role === 'BRIDE' ? 'primary' : 'outline'} onClick={() => setRole('BRIDE')}>BRIDE</Button>
                  <Button variant={role === 'GROOM' ? 'primary' : 'outline'} onClick={() => setRole('GROOM')}>GROOM</Button>
                </div>
              </div>
              <Button variant="secondary" onClick={onGenerate}>초대코드 생성</Button>
              {generatedCode && <p className="text-lg font-semibold">생성 코드: {generatedCode}</p>}
            </TabsContent>

            <TabsContent value="connect" className="space-y-3">
              <Input label="연동 코드 입력" value={inviteCode} onChange={(e) => setInviteCode(e.target.value)} maxLength={6} />
              <Button onClick={onConnect}>연동하기</Button>
            </TabsContent>
          </Tabs>

          <Modal>
            <ModalTrigger asChild>
              <Button variant="ghost">연동해제</Button>
            </ModalTrigger>
            <ModalContent>
              <ModalHeader>
                <ModalTitle>커플 연동을 해제할까요?</ModalTitle>
                <ModalDescription>해제하면 공유 중인 일부 연동 데이터가 분리됩니다.</ModalDescription>
              </ModalHeader>
              <ModalFooter>
                <ModalClose asChild>
                  <Button variant="ghost">취소</Button>
                </ModalClose>
                <ModalClose asChild>
                  <Button variant="outline" onClick={onDisconnect}>해제하기</Button>
                </ModalClose>
              </ModalFooter>
            </ModalContent>
          </Modal>
        </CardContent>
      </Card>

      <BottomSheet open={shareSheetOpen} onClose={() => setShareSheetOpen(false)} title="초대코드 공유">
        <div className="space-y-3">
          <p className="text-sm text-text-secondary">아래 코드를 복사해 상대방에게 전달하세요.</p>
          <div className="rounded-lg border border-border bg-background p-4 text-center text-2xl font-bold tracking-[0.4em]">
            {generatedCode}
          </div>
          <Button
            className="w-full"
            onClick={async () => {
              await navigator.clipboard.writeText(generatedCode);
              toast.success('복사 완료', '초대코드가 클립보드에 복사되었습니다.');
            }}
          >
            코드 복사
          </Button>
        </div>
      </BottomSheet>
    </div>
  );
}
