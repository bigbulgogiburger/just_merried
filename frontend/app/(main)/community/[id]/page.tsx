'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { createComment, getComments, getPostDetail, likePost, unlikePost } from '@/lib/api/s5';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { CommentItem, PostDetail } from '@/lib/api/s5';

export default function CommunityDetailPage() {
  const toast = useAppToast();
  const params = useParams<{ id: string }>();
  const postId = Number(params.id);

  const [post, setPost] = useState<PostDetail | null>(null);
  const [comments, setComments] = useState<CommentItem[]>([]);
  const [comment, setComment] = useState('');
  const [liked, setLiked] = useState(false);

  const refresh = async () => {
    try {
      const [detail, commentList] = await Promise.all([getPostDetail(postId), getComments(postId)]);
      setPost(detail);
      setComments(commentList);
    } catch {
      toast.error('게시글 조회 실패');
    }
  };

  useEffect(() => {
    if (!Number.isFinite(postId)) return;
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [postId]);

  const onLike = async () => {
    try {
      if (liked) {
        await unlikePost(postId);
        setLiked(false);
      } else {
        await likePost(postId);
        setLiked(true);
      }
      await refresh();
    } catch {
      toast.error('좋아요 처리 실패');
    }
  };

  const onComment = async () => {
    try {
      await createComment(postId, { content: comment });
      setComment('');
      await refresh();
      toast.success('댓글 등록 완료');
    } catch {
      toast.error('댓글 등록 실패');
    }
  };

  if (!post) return <div className="wedding-container py-6">로딩 중...</div>;

  return (
    <div className="wedding-container space-y-4 py-6">
      <Card>
        <CardHeader>
          <CardTitle>게시글 상세</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <div className="flex items-center justify-between">
            <Link href={`/community/user/${post.userId}`}>
              <Button variant="ghost">작성자 #{post.userId}</Button>
            </Link>
            <Badge variant="outline">{post.region}</Badge>
          </div>
          <p>{post.content}</p>
          <div className="flex flex-wrap gap-2">
            {post.hashtags.map((tag) => (
              <Badge key={tag}>#{tag}</Badge>
            ))}
          </div>
          <div className="flex gap-2">
            <Button variant={liked ? 'primary' : 'outline'} onClick={onLike}>좋아요 {post.likeCount}</Button>
            <Badge variant="warning">댓글 {post.commentCount}</Badge>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">댓글</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <div className="flex gap-2">
            <Input value={comment} onChange={(e) => setComment(e.target.value)} placeholder="댓글 입력" />
            <Button onClick={onComment}>등록</Button>
          </div>
          <div className="space-y-2">
            {comments.map((c) => (
              <div key={c.id} className="rounded-md border border-border p-3 text-sm">
                <p>{c.content}</p>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
