'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { createOrReuseDmRoom, getMyDmRooms } from '@/lib/api/s6';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { DmRoom } from '@/lib/api/s6';

export default function DmRoomsPage() {
  const toast = useAppToast();
  const [rooms, setRooms] = useState<DmRoom[]>([]);
  const [targetUserId, setTargetUserId] = useState('');

  const refresh = async () => {
    try {
      const data = await getMyDmRooms();
      setRooms(data);
    } catch {
      toast.error('DM 목록 조회 실패');
    }
  };

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onCreateRoom = async () => {
    if (!targetUserId.trim()) {
      toast.error('대상 유저 ID를 입력해주세요.');
      return;
    }
    try {
      const room = await createOrReuseDmRoom(Number(targetUserId));
      toast.success('DM 방 준비 완료');
      window.location.href = `/community/dm/${room.roomId}`;
    } catch {
      toast.error('DM 방 생성 실패');
    }
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <div>
        <h1 className="text-2xl font-bold">DM</h1>
        <p className="text-sm text-text-secondary">1:1 메시지를 주고받을 수 있어요.</p>
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">새 DM 시작</CardTitle>
        </CardHeader>
        <CardContent className="flex gap-2">
          <Input
            type="number"
            value={targetUserId}
            onChange={(e) => setTargetUserId(e.target.value)}
            placeholder="대상 유저 ID"
          />
          <Button onClick={onCreateRoom}>방 열기</Button>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">내 대화방</CardTitle>
        </CardHeader>
        <CardContent className="space-y-2">
          {rooms.length === 0 ? (
            <p className="text-sm text-text-secondary">아직 대화방이 없습니다.</p>
          ) : (
            rooms.map((room) => (
              <Link key={room.roomId} href={`/community/dm/${room.roomId}`}>
                <div className="rounded-md border border-border p-3 hover:bg-background">
                  <p className="font-medium">{room.counterpartNickname} (#{room.counterpartUserId})</p>
                  <p className="text-xs text-text-secondary">참여일 {new Date(room.joinedAt).toLocaleString()}</p>
                </div>
              </Link>
            ))
          )}
        </CardContent>
      </Card>
    </div>
  );
}
