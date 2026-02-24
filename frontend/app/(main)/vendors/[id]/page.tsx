'use client';

import Image from 'next/image';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import {
  addCompare,
  addFavorite,
  createReview,
  getCompareList,
  getVendorDetail,
  listReviews,
  removeCompare,
  removeFavorite,
} from '@/lib/api/s4';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { VendorDetail, VendorReview } from '@/lib/api/s4';

export default function VendorDetailPage() {
  const toast = useAppToast();
  const params = useParams<{ id: string }>();
  const vendorId = Number(params.id);

  const [vendor, setVendor] = useState<VendorDetail | null>(null);
  const [reviews, setReviews] = useState<VendorReview[]>([]);
  const [isFavorite, setIsFavorite] = useState(false);
  const [isCompared, setIsCompared] = useState(false);

  const [newRating, setNewRating] = useState('5');
  const [newContent, setNewContent] = useState('');

  const coverImage = useMemo(
    () => vendor?.images.find((img) => img.isCover)?.imageUrl ?? vendor?.images[0]?.imageUrl,
    [vendor],
  );

  const refresh = async () => {
    try {
      const [detail, reviewList, compareList] = await Promise.all([
        getVendorDetail(vendorId),
        listReviews(vendorId),
        getCompareList(),
      ]);
      setVendor(detail);
      setReviews(reviewList);
      setIsCompared(compareList.some((item) => item.vendorId === vendorId));
    } catch {
      toast.error('업체 상세 조회 실패');
    }
  };

  useEffect(() => {
    if (!Number.isFinite(vendorId)) return;
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [vendorId]);

  const onToggleFavorite = async () => {
    try {
      if (isFavorite) {
        await removeFavorite(vendorId);
        setIsFavorite(false);
        toast.info('찜 해제 완료');
      } else {
        await addFavorite(vendorId);
        setIsFavorite(true);
        toast.success('찜 추가 완료');
      }
    } catch {
      toast.error('찜 처리 실패');
    }
  };

  const onToggleCompare = async () => {
    try {
      if (isCompared) {
        await removeCompare(vendorId);
        setIsCompared(false);
        toast.info('비교함 제거 완료');
      } else {
        await addCompare(vendorId);
        setIsCompared(true);
        toast.success('비교함 추가 완료');
      }
    } catch {
      toast.error('비교함 처리 실패', '비교함 최대 개수를 확인해주세요.');
    }
  };

  const onCreateReview = async () => {
    try {
      await createReview(vendorId, { rating: Number(newRating), content: newContent });
      setNewContent('');
      toast.success('리뷰 등록 완료');
      await refresh();
    } catch {
      toast.error('리뷰 등록 실패');
    }
  };

  if (!vendor) {
    return (
      <div className="wedding-container py-6">
        <Card>
          <CardContent className="py-10 text-center text-sm text-text-secondary">업체 정보를 불러오는 중입니다.</CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-center justify-between gap-2">
        <div>
          <h1 className="text-2xl font-bold">{vendor.name}</h1>
          <p className="mt-1 text-sm text-text-secondary">{vendor.region} · {vendor.category}</p>
        </div>
        <div className="flex gap-2">
          <Button variant={isFavorite ? 'primary' : 'outline'} onClick={onToggleFavorite}>찜</Button>
          <Button variant={isCompared ? 'primary' : 'outline'} onClick={onToggleCompare}>비교</Button>
        </div>
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">대표 정보</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          {coverImage ? (
            <Image
              src={coverImage}
              alt={vendor.name}
              width={1200}
              height={560}
              className="h-56 w-full rounded-lg object-cover"
            />
          ) : (
            <div className="flex h-56 items-center justify-center rounded-lg bg-background text-sm text-text-secondary">대표 이미지 없음</div>
          )}
          <p className="text-sm text-text-secondary">{vendor.description || '설명 없음'}</p>
          <div className="flex flex-wrap gap-2">
            <Badge variant="info">평점 {vendor.ratingAvg} ({vendor.ratingCount})</Badge>
            <Badge variant="outline">{vendor.minPrice.toLocaleString()} ~ {vendor.maxPrice.toLocaleString()}원</Badge>
          </div>
          <p className="text-sm text-text-secondary">주소: {vendor.address || '-'}</p>
          <p className="text-sm text-text-secondary">연락처: {vendor.phone || '-'}</p>
          {vendor.homepageUrl ? (
            <Link href={vendor.homepageUrl} target="_blank" className="text-sm text-primary underline">홈페이지 바로가기</Link>
          ) : null}
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">가격 패키지</CardTitle>
        </CardHeader>
        <CardContent className="space-y-2">
          {vendor.prices.length === 0 ? (
            <p className="text-sm text-text-secondary">등록된 패키지가 없습니다.</p>
          ) : (
            vendor.prices.map((price) => (
              <div key={price.id} className="rounded-md border border-border p-3">
                <p className="font-medium">{price.packageName}</p>
                <p className="text-sm text-text-secondary">{price.price.toLocaleString()}원</p>
                <p className="text-xs text-text-secondary">{price.description || '-'}</p>
              </div>
            ))
          )}
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">리뷰</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <div className="grid gap-2 tablet:grid-cols-[120px_1fr_auto]">
            <Input label="평점(1~5)" type="number" min={1} max={5} value={newRating} onChange={(e) => setNewRating(e.target.value)} />
            <Input label="리뷰 내용" value={newContent} onChange={(e) => setNewContent(e.target.value)} />
            <div className="self-end">
              <Button onClick={onCreateReview}>등록</Button>
            </div>
          </div>

          <div className="space-y-2">
            {reviews.length === 0 ? (
              <p className="text-sm text-text-secondary">아직 리뷰가 없습니다.</p>
            ) : (
              reviews.map((review) => (
                <div key={review.id} className="rounded-md border border-border p-3">
                  <p className="font-medium">평점 {review.rating}</p>
                  <p className="text-sm text-text-secondary">{review.content || '-'}</p>
                  <p className="text-xs text-text-muted">{new Date(review.createdAt).toLocaleString()}</p>
                </div>
              ))
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
