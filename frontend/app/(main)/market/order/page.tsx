'use client';
import { useState } from 'react';

import { requestPayment } from '@/lib/api/s8';

export default function OrderPage(){
  const [amount,setAmount]=useState(10000); const [result,setResult]=useState<string>('');
  return <main className="p-6 space-y-4"><h1 className="text-xl font-bold">주문/결제</h1><input className="border p-2" type="number" value={amount} onChange={e=>setAmount(Number(e.target.value))}/><button className="bg-black text-white px-4 py-2" onClick={async()=>{const r=await requestPayment({orderType:'MARKET_ORDER',amount,paymentMethod:'CARD',provider:'MOCK'}); setResult(`결제완료 #${r.id} (${r.status})`);}}>결제하기</button><p>{result}</p></main>
}
