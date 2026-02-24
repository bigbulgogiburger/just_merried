'use client';

import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { deleteSecondhand, getMySecondhand, updateSecondhandStatus } from '@/lib/api/s7';

import type { SecondhandSummary } from '@/lib/api/s7';

export default function MySecondhandPage() {
  const [items, setItems] = useState<SecondhandSummary[]>([]);
  const load = () => getMySecondhand().then(setItems);
  useEffect(() => { load(); }, []);

  return <div className="wedding-container space-y-3 py-6"><h1 className="text-xl font-bold">내 중고 거래</h1>{items.map((item)=><Card key={item.id}><CardContent className="space-y-2 py-4"><p>{item.title}</p><div className="flex gap-2"><Button size="sm" variant="outline" onClick={()=>updateSecondhandStatus(item.id,'RESERVED').then(load)}>예약중</Button><Button size="sm" variant="outline" onClick={()=>updateSecondhandStatus(item.id,'SOLD').then(load)}>거래완료</Button><Button size="sm" variant="ghost" onClick={()=>deleteSecondhand(item.id).then(load)}>삭제</Button></div></CardContent></Card>)}</div>;
}
