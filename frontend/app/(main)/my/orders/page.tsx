'use client';
import { useEffect, useState } from 'react';

import { getMyEscrow, type EscrowResponse } from '@/lib/api/s8';

export default function MyOrdersPage(){ const [list,setList]=useState<EscrowResponse[]>([]); useEffect(()=>{getMyEscrow().then(setList);},[]);
return <main className='p-6'><h1 className='text-xl font-bold mb-4'>주문/배송 관리</h1><ul className='space-y-2'>{list.map(o=><li key={o.id} className='border p-3'>주문 #{o.id} · 상태 {o.status} · 운송장 {o.trackingNumber ?? '-'}</li>)}</ul></main>; }
