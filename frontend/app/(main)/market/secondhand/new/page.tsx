'use client';

import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { createSecondhand } from '@/lib/api/s7';
import { useAppToast } from '@/lib/providers/toast-provider';

export default function NewSecondhandPage() {
  const toast = useAppToast();
  const [title, setTitle] = useState('');
  const [price, setPrice] = useState(0);
  const [region, setRegion] = useState('서울');
  const [description, setDescription] = useState('');

  const submit = async () => {
    try {
      const created = await createSecondhand({ title, price, region, description, conditionStatus: 'GOOD', tradeMethod: 'BOTH', imageUrls: [] });
      toast.success('등록 완료');
      window.location.href = `/market/secondhand/${created.id}`;
    } catch {
      toast.error('등록 실패');
    }
  };

  return <div className="wedding-container py-6"><Card><CardHeader><CardTitle>중고 등록</CardTitle></CardHeader><CardContent className="space-y-2"><Input placeholder="제목" value={title} onChange={(e)=>setTitle(e.target.value)} /><Input type="number" placeholder="가격" value={price} onChange={(e)=>setPrice(Number(e.target.value))} /><Input placeholder="지역" value={region} onChange={(e)=>setRegion(e.target.value)} /><Input placeholder="설명" value={description} onChange={(e)=>setDescription(e.target.value)} /><Button onClick={submit}>등록</Button></CardContent></Card></div>;
}
