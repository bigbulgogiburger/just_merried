'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input, Textarea } from '@/components/ui/input';
import { createPost } from '@/lib/api/s5';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function CommunityCreatePage() {
  const toast = useAppToast();
  const router = useRouter();
  const [content, setContent] = useState('');
  const [region, setRegion] = useState('서울');
  const [hashtags, setHashtags] = useState('#웨딩,#결혼준비');

  const onSubmit = async () => {
    try {
      const created = await createPost({
        content,
        region,
        hashtags: hashtags.split(',').map((v) => v.trim()).filter(Boolean),
      });
      toast.success('게시글 등록 완료');
      router.push(`/community/${created.id}`);
    } catch {
      toast.error('게시글 등록 실패');
    }
  };

  return (
    <div className="wedding-container py-6">
      <Card className="max-w-2xl">
        <CardHeader>
          <CardTitle>게시글 작성</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <Textarea label="내용" value={content} onChange={(e) => setContent(e.target.value)} />
          <Input label="지역" value={region} onChange={(e) => setRegion(e.target.value)} />
          <Input label="해시태그(쉼표구분)" value={hashtags} onChange={(e) => setHashtags(e.target.value)} />
          <Button onClick={onSubmit}>등록하기</Button>
        </CardContent>
      </Card>
    </div>
  );
}
