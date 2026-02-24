'use client';

import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { getDmMessages, sendDmMessage } from '@/lib/api/s6';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { DmMessage } from '@/lib/api/s6';

export default function DmRoomPage() {
  const toast = useAppToast();
  const params = useParams<{ roomId: string }>();
  const roomId = Number(params.roomId);

  const [messages, setMessages] = useState<DmMessage[]>([]);
  const [text, setText] = useState('');

  const refresh = async () => {
    try {
      const data = await getDmMessages(roomId);
      setMessages(data);
    } catch {
      toast.error('메시지 조회 실패');
    }
  };

  useEffect(() => {
    if (!Number.isFinite(roomId)) return;
    refresh();
    const timer = setInterval(refresh, 4000);
    return () => clearInterval(timer);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [roomId]);

  const onSend = async () => {
    if (!text.trim()) return;
    try {
      await sendDmMessage(roomId, text);
      setText('');
      await refresh();
    } catch {
      toast.error('메시지 전송 실패');
    }
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <Card>
        <CardHeader>
          <CardTitle>DM 방 #{roomId}</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <div className="max-h-[420px] space-y-2 overflow-y-auto rounded-md border border-border p-3">
            {messages.length === 0 ? (
              <p className="text-sm text-text-secondary">아직 메시지가 없습니다.</p>
            ) : (
              messages.map((m) => (
                <div key={m.id} className="rounded-md bg-background p-2">
                  <p className="text-sm">{m.content}</p>
                  <p className="text-[11px] text-text-muted">보낸이 #{m.senderId} · {new Date(m.createdAt).toLocaleString()}</p>
                </div>
              ))
            )}
          </div>
          <div className="flex gap-2">
            <Input value={text} onChange={(e) => setText(e.target.value)} placeholder="메시지 입력" />
            <Button onClick={onSend}>전송</Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
