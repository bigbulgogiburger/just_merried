'use client';

import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { followUser, getFollowers, getFollowing, unfollowUser } from '@/lib/api/s5';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { FollowUser } from '@/lib/api/s5';

export default function CommunityUserPage() {
  const toast = useAppToast();
  const params = useParams<{ id: string }>();
  const userId = Number(params.id);

  const [followers, setFollowers] = useState<FollowUser[]>([]);
  const [following, setFollowing] = useState<FollowUser[]>([]);
  const [followingState, setFollowingState] = useState(false);

  const refresh = async () => {
    try {
      const [fs, fg] = await Promise.all([getFollowers(userId), getFollowing(userId)]);
      setFollowers(fs);
      setFollowing(fg);
    } catch {
      toast.error('프로필 조회 실패');
    }
  };

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [userId]);

  const onToggleFollow = async () => {
    try {
      if (followingState) {
        await unfollowUser(userId);
        setFollowingState(false);
      } else {
        await followUser(userId);
        setFollowingState(true);
      }
      await refresh();
    } catch {
      toast.error('팔로우 처리 실패');
    }
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <Card>
        <CardHeader>
          <CardTitle>유저 #{userId}</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <Button variant={followingState ? 'primary' : 'outline'} onClick={onToggleFollow}>
            {followingState ? '언팔로우' : '팔로우'}
          </Button>
          <div className="flex gap-2">
            <Badge variant="info">팔로워 {followers.length}</Badge>
            <Badge variant="outline">팔로잉 {following.length}</Badge>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
