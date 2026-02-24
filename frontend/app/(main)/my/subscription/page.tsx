'use client';
import { useEffect, useState } from 'react';

import { cancelSubscription, getMySubscription, requestPayment, subscribeBasic, type SubscriptionResponse } from '@/lib/api/s8';

export default function SubscriptionPage(){ const [sub,setSub]=useState<SubscriptionResponse | null>(null); const load=()=>getMySubscription().then(setSub).catch(()=>setSub(null)); useEffect(()=>{load();},[]);
return <main className='p-6 space-y-4'><h1 className='text-xl font-bold'>구독 관리</h1><div className='border p-4'>현재 플랜: {sub?.planType ?? 'FREE'} / 상태: {sub?.status ?? '-'}</div><button className='bg-black text-white px-4 py-2' onClick={async()=>{const p=await requestPayment({orderType:'SUBSCRIPTION',amount:4900,paymentMethod:'CARD',provider:'MOCK'}); await subscribeBasic({paymentId:p.id}); load();}}>Basic 구독 결제</button><button className='border px-4 py-2 ml-2' onClick={async()=>{await cancelSubscription(); load();}}>구독 해지</button></main>; }
