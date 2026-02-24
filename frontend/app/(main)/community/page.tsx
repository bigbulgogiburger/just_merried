'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { getFollowingFeed, getGlobalFeed, getRegionFeed } from '@/lib/api/s5';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { FeedItem } from '@/lib/api/s5';

export default function CommunityPage() {
  const toast = useAppToast();
  const [tab, setTab] = useState<'global' | 'following' | 'region'>('global');
  const [region, setRegion] = useState('서울');
  const [items, setItems] = useState<FeedItem[]>([]);

  const load = async () => {
    try {
      const data =
        tab === 'following'
          ? await getFollowingFeed()
          : tab === 'region'
            ? await getRegionFeed(region)
            : await getGlobalFeed();
      setItems(data);
    } catch {
      toast.error('피드 조회 실패');
    }
  };

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tab]);

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">커뮤니티</h1>
          <p className="text-sm text-text-secondary">후기와 팁을 공유하고 소통해요.</p>
        </div>
        <Link href="/community/create">
          <Button>글 작성</Button>
        </Link>
      </div>

      <Tabs defaultValue="global" className="space-y-4">
        <TabsList className="grid w-full grid-cols-3">
          <TabsTrigger value="global" onClick={() => setTab('global')}>전체</TabsTrigger>
          <TabsTrigger value="following" onClick={() => setTab('following')}>팔로잉</TabsTrigger>
          <TabsTrigger value="region" onClick={() => setTab('region')}>지역</TabsTrigger>
        </TabsList>

        <TabsContent value="global"><></></TabsContent>
        <TabsContent value="following"><></></TabsContent>
        <TabsContent value="region">
          <Card>
            <CardContent className="pt-4">
              <div className="flex gap-2">
                <Input value={region} onChange={(e) => setRegion(e.target.value)} placeholder="지역" />
                <Button variant="outline" onClick={load}>적용</Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>

      <div className="space-y-3">
        {items.length === 0 ? (
          <Card>
            <CardContent className="py-10 text-center text-sm text-text-secondary">게시글이 없습니다.</CardContent>
          </Card>
        ) : (
          items.map((item) => (
            <Card key={item.id}>
              <CardHeader>
                <CardTitle className="text-base">작성자 #{item.userId}</CardTitle>
              </CardHeader>
              <CardContent className="space-y-2">
                <p className="text-sm">{item.content}</p>
                <div className="flex flex-wrap gap-2">
                  <Badge variant="outline">{item.region}</Badge>
                  <Badge variant="info">좋아요 {item.likeCount}</Badge>
                  <Badge variant="warning">댓글 {item.commentCount}</Badge>
                </div>
                <div className="flex justify-end">
                  <Link href={`/community/${item.id}`}>
                    <Button variant="outline">상세 보기</Button>
                  </Link>
                </div>
              </CardContent>
            </Card>
          ))
        )}
      </div>
    </div>
  );
}
