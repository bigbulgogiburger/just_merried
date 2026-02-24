'use client';
import { useEffect, useState } from 'react';

import { confirmEscrow, createEscrow, getMyEscrow, shipEscrow, type EscrowResponse } from '@/lib/api/s8';

export default function EscrowPage(){
  const [productId,setProductId]=useState(1); const [amount,setAmount]=useState(50000); const [list,setList]=useState<EscrowResponse[]>([]);
  const load=()=>getMyEscrow().then(setList);
  useEffect(()=>{ load(); },[]);
  return <main className="p-6 space-y-4"><h1 className="text-xl font-bold">안전거래</h1><div className='space-x-2'><input className="border p-2" type='number' value={productId} onChange={e=>setProductId(Number(e.target.value))}/><input className="border p-2" type='number' value={amount} onChange={e=>setAmount(Number(e.target.value))}/><button className='bg-black text-white px-3 py-2' onClick={async()=>{await createEscrow({secondhandProductId:productId,amount}); load();}}>결제 생성</button></div><ul className='space-y-2'>{list.map(e=><li key={e.id} className='border p-3'>#{e.id} {e.status} <button onClick={async()=>{await shipEscrow(e.id,{carrier:'CJ',trackingNumber:'123-456'});load();}} className='ml-2 text-blue-600'>발송</button><button onClick={async()=>{await confirmEscrow(e.id);load();}} className='ml-2 text-green-600'>수령확인</button></li>)}</ul></main>
}
