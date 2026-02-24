'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { getCompareList, removeCompare } from '@/lib/api/s4';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { VendorCompareItem } from '@/lib/api/s4';

export default function VendorComparePage() {
  const toast = useAppToast();
  const [items, setItems] = useState<VendorCompareItem[]>([]);

  const refresh = async () => {
    try {
      const data = await getCompareList();
      setItems(data);
    } catch {
      toast.error('비교함 조회 실패');
    }
  };

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onRemove = async (vendorId: number) => {
    try {
      await removeCompare(vendorId);
      toast.info('비교함에서 제거했습니다.');
      await refresh();
    } catch {
      toast.error('비교함 제거 실패');
    }
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">업체 비교함</h1>
          <p className="text-sm text-text-secondary">가격/평점/지역을 기준으로 비교해요.</p>
        </div>
        <Link href="/vendors">
          <Button variant="outline">업체 목록으로</Button>
        </Link>
      </div>

      {items.length === 0 ? (
        <Card>
          <CardContent className="py-12 text-center text-sm text-text-secondary">
            비교함이 비어있어요. 업체 상세에서 비교 버튼을 눌러 추가해 주세요.
          </CardContent>
        </Card>
      ) : (
        <Card>
          <CardHeader>
            <CardTitle>비교 목록 ({items.length}개)</CardTitle>
          </CardHeader>
          <CardContent className="space-y-2">
            {items.map((item) => (
              <div key={item.compareItemId} className="rounded-md border border-border p-3">
                <div className="flex items-center justify-between gap-2">
                  <div>
                    <p className="font-semibold">{item.name}</p>
                    <div className="mt-1 flex flex-wrap gap-2">
                      <Badge variant="outline">{item.category}</Badge>
                      <Badge variant="info">{item.region}</Badge>
                    </div>
                    <p className="mt-1 text-sm text-text-secondary">
                      {item.minPrice.toLocaleString()} ~ {item.maxPrice.toLocaleString()}원 · 평점 {item.ratingAvg} ({item.ratingCount})
                    </p>
                  </div>
                  <div className="flex gap-2">
                    <Link href={`/vendors/${item.vendorId}`}>
                      <Button variant="outline">상세</Button>
                    </Link>
                    <Button variant="ghost" onClick={() => onRemove(item.vendorId)}>제거</Button>
                  </div>
                </div>
              </div>
            ))}
          </CardContent>
        </Card>
      )}
    </div>
  );
}
