'use client';

import Link from 'next/link';
import { useEffect, useMemo, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { listVendors } from '@/lib/api/s4';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { VendorListItem } from '@/lib/api/s4';

const PAGE_SIZE = 8;

export default function VendorsPage() {
  const toast = useAppToast();
  const [vendors, setVendors] = useState<VendorListItem[]>([]);
  const [region, setRegion] = useState('');
  const [minPrice, setMinPrice] = useState('0');
  const [maxPrice, setMaxPrice] = useState('100000000');
  const [sort, setSort] = useState<'latest' | 'rating' | 'price_asc' | 'price_desc'>('latest');
  const [page, setPage] = useState(1);

  const fetchVendors = async () => {
    try {
      const data = await listVendors({
        region: region || undefined,
        minPrice: Number(minPrice || 0),
        maxPrice: Number(maxPrice || 100000000),
        sort,
      });
      setVendors(data);
      setPage(1);
    } catch {
      toast.error('업체 목록 조회 실패');
    }
  };

  useEffect(() => {
    fetchVendors();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [sort]);

  const pageCount = Math.max(1, Math.ceil(vendors.length / PAGE_SIZE));
  const pagedVendors = useMemo(
    () => vendors.slice((page - 1) * PAGE_SIZE, page * PAGE_SIZE),
    [vendors, page],
  );

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-center justify-between gap-2">
        <div className="space-y-1">
          <h1 className="text-2xl font-bold">업체 탐색</h1>
          <p className="text-sm text-text-secondary">카테고리/지역/가격 기준으로 업체를 비교해보세요.</p>
        </div>
        <Link href="/vendors/compare">
          <Button variant="outline">비교함 보기</Button>
        </Link>
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">필터 & 정렬</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <div className="grid gap-3 tablet:grid-cols-3">
            <Input label="지역" value={region} onChange={(e) => setRegion(e.target.value)} placeholder="예: 서울" />
            <Input label="최소 금액" type="number" value={minPrice} onChange={(e) => setMinPrice(e.target.value)} />
            <Input label="최대 금액" type="number" value={maxPrice} onChange={(e) => setMaxPrice(e.target.value)} />
          </div>
          <div className="flex flex-wrap items-center justify-between gap-3">
            <div className="flex flex-wrap gap-2">
              <Button variant={sort === 'latest' ? 'primary' : 'outline'} onClick={() => setSort('latest')}>최신순</Button>
              <Button variant={sort === 'rating' ? 'primary' : 'outline'} onClick={() => setSort('rating')}>평점순</Button>
              <Button variant={sort === 'price_asc' ? 'primary' : 'outline'} onClick={() => setSort('price_asc')}>저가순</Button>
              <Button variant={sort === 'price_desc' ? 'primary' : 'outline'} onClick={() => setSort('price_desc')}>고가순</Button>
            </div>
            <Button onClick={fetchVendors}>적용</Button>
          </div>
        </CardContent>
      </Card>

      <div className="grid gap-3 tablet:grid-cols-2 desktop:grid-cols-3">
        {pagedVendors.length === 0 ? (
          <Card>
            <CardContent className="py-10 text-center text-sm text-text-secondary">
              조건에 맞는 업체가 없습니다.
            </CardContent>
          </Card>
        ) : (
          pagedVendors.map((vendor) => (
            <Card key={vendor.id}>
              <CardHeader>
                <CardTitle className="line-clamp-1 text-base">{vendor.name}</CardTitle>
              </CardHeader>
              <CardContent className="space-y-2">
                <div className="flex flex-wrap gap-2">
                  <Badge variant="outline">{vendor.category}</Badge>
                  <Badge variant="info">{vendor.region}</Badge>
                </div>
                <p className="text-sm text-text-secondary">
                  {vendor.minPrice.toLocaleString()}원 ~ {vendor.maxPrice.toLocaleString()}원
                </p>
                <p className="text-sm text-text-secondary">
                  평점 {vendor.ratingAvg} ({vendor.ratingCount})
                </p>
                <Link href={`/vendors/${vendor.id}`}>
                  <Button variant="outline" className="w-full">상세 보기</Button>
                </Link>
              </CardContent>
            </Card>
          ))
        )}
      </div>

      <div className="flex items-center justify-center gap-2">
        <Button variant="ghost" onClick={() => setPage((p) => Math.max(1, p - 1))} disabled={page <= 1}>이전</Button>
        <Badge variant="outline">{page} / {pageCount}</Badge>
        <Button variant="ghost" onClick={() => setPage((p) => Math.min(pageCount, p + 1))} disabled={page >= pageCount}>다음</Button>
      </div>
    </div>
  );
}
