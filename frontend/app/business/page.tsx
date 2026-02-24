'use client';
import { useEffect, useState } from 'react';

import { createBusinessProduct, getBusinessStats, getMySettlements, type SettlementResponse } from '@/lib/api/s8';

export default function BusinessPage() {
  const [stats, setStats] = useState<{ dailySales: number; settlementCount: number } | null>(null);
  const [settlements, setSettlements] = useState<SettlementResponse[]>([]);
  const [name, setName] = useState('샘플 상품');
  const load = () => { getBusinessStats().then(setStats); getMySettlements().then(setSettlements); };

  useEffect(() => { load(); }, []);

  return <main className='p-6 space-y-4'><h1 className='text-xl font-bold'>비즈니스 센터</h1><div className='border p-3'>일매출 {stats?.dailySales ?? 0} / 정산건수 {stats?.settlementCount ?? 0}</div><div><input className='border p-2 mr-2' value={name} onChange={e => setName(e.target.value)} /><button className='bg-black text-white px-3 py-2' onClick={async () => { await createBusinessProduct({ name, basePrice: 10000, description: '', categoryId: 1 }); }}>상품 등록</button></div><ul>{settlements.map(s => <li key={s.id}>정산 #{s.id} 순수익 {s.netAmount}</li>)}</ul></main>;
}
