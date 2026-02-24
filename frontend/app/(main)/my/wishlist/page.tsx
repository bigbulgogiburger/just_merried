'use client';

import { useEffect, useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { getWishlist, removeWishlist } from '@/lib/api/s7';

import type { WishlistItem } from '@/lib/api/s7';

export default function WishlistPage() {
  const [items, setItems] = useState<WishlistItem[]>([]);
  const load = () => getWishlist().then(setItems);
  useEffect(() => { load(); }, []);

  return <div className="wedding-container space-y-3 py-6"><h1 className="text-xl font-bold">관심상품</h1>{items.map((item)=><Card key={item.id}><CardContent className="flex items-center justify-between py-4"><p className="text-sm">{item.targetType} #{item.targetId}</p><Button size="sm" variant="outline" onClick={()=>removeWishlist(item.targetType,item.targetId).then(load)}>해제</Button></CardContent></Card>)}</div>;
}
