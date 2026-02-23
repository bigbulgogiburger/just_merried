'use client';

import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { getMe, updateMe } from '@/lib/api/s2';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function ProfilePage() {
  const toast = useAppToast();
  const [nickname, setNickname] = useState('');
  const [region, setRegion] = useState('');
  const [profileImageUrl, setProfileImageUrl] = useState('');

  useEffect(() => {
    getMe()
      .then((me) => {
        setNickname(me.nickname ?? '');
        setRegion(me.region ?? '');
        setProfileImageUrl(me.profileImageUrl ?? '');
      })
      .catch(() => toast.error('프로필 조회 실패'));
  }, [toast]);

  const onSave = async () => {
    try {
      await updateMe({ nickname, region, profileImageUrl });
      toast.success('프로필 저장 완료');
    } catch {
      toast.error('프로필 저장 실패');
    }
  };

  return (
    <div className="wedding-container py-6">
      <Card className="max-w-xl">
        <CardHeader>
          <CardTitle>내 프로필</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <Input label="닉네임" value={nickname} onChange={(e) => setNickname(e.target.value)} />
          <Input label="지역" value={region} onChange={(e) => setRegion(e.target.value)} />
          <Input label="프로필 이미지 URL" value={profileImageUrl} onChange={(e) => setProfileImageUrl(e.target.value)} />
          <Button onClick={onSave}>저장</Button>
        </CardContent>
      </Card>
    </div>
  );
}
