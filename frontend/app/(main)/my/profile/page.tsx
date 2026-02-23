'use client';

import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { getMe, updateMe } from '@/lib/api/s2';

export default function ProfilePage() {
  const [nickname, setNickname] = useState('');
  const [region, setRegion] = useState('');
  const [profileImageUrl, setProfileImageUrl] = useState('');

  useEffect(() => {
    getMe().then((me) => {
      setNickname(me.nickname ?? '');
      setRegion(me.region ?? '');
      setProfileImageUrl(me.profileImageUrl ?? '');
    });
  }, []);

  const onSave = async () => {
    await updateMe({ nickname, region, profileImageUrl });
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
