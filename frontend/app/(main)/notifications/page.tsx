'use client';

import Link from 'next/link';
import { useEffect, useMemo, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { getNotifications, readAllNotifications, readNotification } from '@/lib/api/s6';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { NotificationItem } from '@/lib/api/s6';

const typeLabel: Record<NotificationItem['type'], string> = {
  LIKE: '좋아요',
  COMMENT: '댓글',
  FOLLOW: '팔로우',
  DM: 'DM',
  SYSTEM: '시스템',
};

const typeVariant: Record<NotificationItem['type'], 'default' | 'info' | 'warning' | 'success' | 'outline'> = {
  LIKE: 'default',
  COMMENT: 'info',
  FOLLOW: 'success',
  DM: 'warning',
  SYSTEM: 'outline',
};

export default function NotificationsPage() {
  const toast = useAppToast();
  const [items, setItems] = useState<NotificationItem[]>([]);

  const unreadCount = useMemo(() => items.filter((n) => !n.read).length, [items]);

  const refresh = async () => {
    try {
      const data = await getNotifications();
      setItems(data);
    } catch {
      toast.error('알림 조회 실패');
    }
  };

  useEffect(() => {
    refresh();
    const timer = setInterval(refresh, 8000);
    return () => clearInterval(timer);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onRead = async (id: number) => {
    try {
      await readNotification(id);
      await refresh();
    } catch {
      toast.error('읽음 처리 실패');
    }
  };

  const onReadAll = async () => {
    try {
      await readAllNotifications();
      await refresh();
      toast.success('전체 읽음 처리 완료');
    } catch {
      toast.error('전체 읽음 처리 실패');
    }
  };

  const targetLink = (n: NotificationItem) => {
    if (n.targetType === 'POST' && n.targetId) return `/community/${n.targetId}`;
    if (n.targetType === 'USER' && n.targetId) return `/community/user/${n.targetId}`;
    if (n.targetType === 'DM_ROOM' && n.targetId) return `/community/dm/${n.targetId}`;
    return '/community';
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">알림센터</h1>
          <p className="text-sm text-text-secondary">미읽음 {unreadCount}건</p>
        </div>
        <Button variant="outline" onClick={onReadAll}>전체 읽음</Button>
      </div>

      {items.length === 0 ? (
        <Card>
          <CardContent className="py-10 text-center text-sm text-text-secondary">새 알림이 없습니다.</CardContent>
        </Card>
      ) : (
        <div className="space-y-2">
          {items.map((n) => (
            <Card key={n.id} className={n.read ? 'opacity-70' : ''}>
              <CardHeader>
                <CardTitle className="flex items-center justify-between text-base">
                  <span>{n.title}</span>
                  <Badge variant={typeVariant[n.type]}>{typeLabel[n.type]}</Badge>
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-2">
                <p className="text-sm text-text-secondary">{n.body || '-'}</p>
                <p className="text-xs text-text-muted">{new Date(n.createdAt).toLocaleString()}</p>
                <div className="flex gap-2">
                  <Link href={targetLink(n)}>
                    <Button variant="outline">대상으로 이동</Button>
                  </Link>
                  {!n.read ? <Button onClick={() => onRead(n.id)}>읽음 처리</Button> : null}
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
