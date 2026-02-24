'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { createOrReuseDmRoom } from '@/lib/api/s6';
import { addWishlist, getSecondhandDetail } from '@/lib/api/s7';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { SecondhandDetail } from '@/lib/api/s7';

export default function SecondhandDetailPage({ params }: { params: { id: string } }) {
  const toast = useAppToast();
  const [detail, setDetail] = useState<SecondhandDetail | null>(null);

  useEffect(() => {
    getSecondhandDetail(Number(params.id)).then(setDetail).catch(() => toast.error('상세 조회 실패'));
  }, [params.id, toast]);

  const goDm = async () => {
    if (!detail) return;
    try {
      const room = await createOrReuseDmRoom(detail.sellerUserId);
      window.location.href = `/community/dm?roomId=${room.roomId}`;
    } catch {
      toast.error('DM 연결 실패');
    }
  };

  if (!detail) return <div className="wedding-container py-8">로딩 중...</div>;

  return <div className="wedding-container space-y-4 py-6"><Card><CardHeader><CardTitle>{detail.title}</CardTitle></CardHeader><CardContent className="space-y-2"><p>{detail.description}</p><p>{detail.price.toLocaleString()}원 · {detail.region}</p><div className="flex gap-2"><Button onClick={() => addWishlist('SECONDHAND_PRODUCT', detail.id).then(() => toast.success('관심 등록')).catch(() => toast.error('실패'))}>관심 등록</Button><Button variant="outline" onClick={goDm}>판매자 DM</Button><Link href="/market"><Button variant="ghost">목록</Button></Link></div></CardContent></Card></div>;
}
