'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';

import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Tabs, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { getMarketCategories, getMarketProducts, getSecondhandProducts, searchMarket } from '@/lib/api/s7';
import { useAppToast } from '@/lib/providers/toast-provider';

import type { MarketCategory, MarketProductSummary, SecondhandSummary } from '@/lib/api/s7';

export default function MarketPage() {
  const toast = useAppToast();
  const [tab, setTab] = useState<'b2b' | 'secondhand'>('b2b');
  const [categories, setCategories] = useState<MarketCategory[]>([]);
  const [categoryId, setCategoryId] = useState<number>();
  const [keyword, setKeyword] = useState('');
  const [products, setProducts] = useState<MarketProductSummary[]>([]);
  const [secondhand, setSecondhand] = useState<SecondhandSummary[]>([]);

  const load = async () => {
    try {
      if (tab === 'b2b') {
        setProducts(await getMarketProducts({ categoryId }));
      } else {
        setSecondhand(await getSecondhandProducts());
      }
    } catch {
      toast.error('마켓 조회 실패');
    }
  };

  useEffect(() => {
    getMarketCategories().then(setCategories).catch(() => undefined);
  }, []);

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tab, categoryId]);

  const onSearch = async () => {
    try {
      const res = await searchMarket(keyword);
      setProducts(res.latestMarketProducts);
      setSecondhand(res.latestSecondhandProducts);
      setTab('b2b');
    } catch {
      toast.error('검색 실패');
    }
  };

  return (
    <div className="wedding-container space-y-4 py-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">마켓</h1>
        <Link href="/market/secondhand/new"><Button>중고 등록</Button></Link>
      </div>
      <div className="flex gap-2">
        <Input value={keyword} onChange={(e) => setKeyword(e.target.value)} placeholder="상품 검색" />
        <Button variant="outline" onClick={onSearch}>검색</Button>
      </div>

      <Tabs defaultValue="b2b">
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="b2b" onClick={() => setTab('b2b')}>신상품</TabsTrigger>
          <TabsTrigger value="secondhand" onClick={() => setTab('secondhand')}>중고</TabsTrigger>
        </TabsList>
      </Tabs>

      {tab === 'b2b' && (
        <div className="space-y-3">
          <div className="flex flex-wrap gap-2">
            <Button size="sm" variant={categoryId ? 'outline' : 'primary'} onClick={() => setCategoryId(undefined)}>전체</Button>
            {categories.map((c) => <Button key={c.id} size="sm" variant={categoryId === c.id ? 'primary' : 'outline'} onClick={() => setCategoryId(c.id)}>{c.name}</Button>)}
          </div>
          {products.map((item) => (
            <Card key={item.id}><CardHeader><CardTitle className="text-base">{item.name}</CardTitle></CardHeader><CardContent className="flex items-center justify-between"><div><p className="text-sm">{item.basePrice.toLocaleString()}원</p><Badge variant="outline">재고 {item.stockQuantity}</Badge></div><Link href={`/market/${item.id}`}><Button variant="outline">상세</Button></Link></CardContent></Card>
          ))}
        </div>
      )}

      {tab === 'secondhand' && (
        <div className="space-y-3">
          {secondhand.map((item) => (
            <Card key={item.id}><CardHeader><CardTitle className="text-base">{item.title}</CardTitle></CardHeader><CardContent className="flex items-center justify-between"><div><p className="text-sm">{item.price.toLocaleString()}원 · {item.region}</p><Badge variant="warning">{item.saleStatus}</Badge></div><Link href={`/market/secondhand/${item.id}`}><Button variant="outline">상세</Button></Link></CardContent></Card>
          ))}
        </div>
      )}
    </div>
  );
}
