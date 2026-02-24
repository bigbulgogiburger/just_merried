'use client';

import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { addWishlist, getMarketProductDetail } from '@/lib/api/s7';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { MarketProductDetail } from '@/lib/api/s7';

export default function MarketDetailPage({ params }: { params: { id: string } }) {
  const toast = useAppToast();
  const [detail, setDetail] = useState<MarketProductDetail | null>(null);

  useEffect(() => {
    getMarketProductDetail(Number(params.id)).then(setDetail).catch(() => toast.error('상세 조회 실패'));
  }, [params.id, toast]);

  if (!detail) return <div className="wedding-container py-8">로딩 중...</div>;

  return <div className="wedding-container space-y-4 py-6"><Card><CardHeader><CardTitle>{detail.name}</CardTitle></CardHeader><CardContent className="space-y-2"><p>{detail.description}</p><p className="text-sm">{detail.basePrice.toLocaleString()}원</p><Button onClick={() => addWishlist('MARKET_PRODUCT', detail.id).then(() => toast.success('관심 등록')).catch(() => toast.error('실패'))}>관심 등록</Button></CardContent></Card></div>;
}
