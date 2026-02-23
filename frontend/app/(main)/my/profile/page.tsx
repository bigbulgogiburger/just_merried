'use client';

import { useEffect, useMemo, useState } from 'react';

import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { getMe, updateMe } from '@/lib/api/s2';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function ProfilePage() {
  const toast = useAppToast();
  const [nickname, setNickname] = useState('');
  const [region, setRegion] = useState('');
  const [profileImageUrl, setProfileImageUrl] = useState('');
  const [email, setEmail] = useState('');
  const [role, setRole] = useState('USER');

  useEffect(() => {
    getMe()
      .then((me) => {
        setNickname(me.nickname ?? '');
        setRegion(me.region ?? '');
        setProfileImageUrl(me.profileImageUrl ?? '');
        setEmail(me.email ?? '');
        setRole(me.role ?? 'USER');
      })
      .catch(() => toast.error('프로필 조회 실패'));
  }, [toast]);

  const initials = useMemo(
    () => nickname.slice(0, 2).toUpperCase() || 'ME',
    [nickname],
  );

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
      <Card className="max-w-2xl">
        <CardHeader>
          <CardTitle>내 프로필</CardTitle>
          <CardDescription>
            기본 정보와 지역을 최신 상태로 유지해 주세요.
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-6">
          <div className="flex items-center gap-4">
            <Avatar className="h-16 w-16">
              <AvatarImage src={profileImageUrl} alt="프로필 이미지" />
              <AvatarFallback>{initials}</AvatarFallback>
            </Avatar>
            <div className="space-y-1">
              <p className="font-semibold">{nickname || '닉네임 미설정'}</p>
              <p className="text-sm text-text-secondary">{email || '이메일 미확인'}</p>
              <Badge variant="outline">{role}</Badge>
            </div>
          </div>

          <div className="grid gap-4 tablet:grid-cols-2">
            <Input
              label="닉네임"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
            />
            <Input
              label="지역"
              value={region}
              onChange={(e) => setRegion(e.target.value)}
            />
          </div>
          <Input
            label="프로필 이미지 URL"
            value={profileImageUrl}
            onChange={(e) => setProfileImageUrl(e.target.value)}
            helperText="이미지 주소를 입력하면 프로필 미리보기에 반영됩니다."
          />

          <div className="flex justify-end">
            <Button onClick={onSave}>변경사항 저장</Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
