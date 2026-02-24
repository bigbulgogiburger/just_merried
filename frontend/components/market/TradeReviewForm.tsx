'use client';
import { useState } from 'react';

import { createTradeReview } from '@/lib/api/s8';

export default function TradeReviewForm({ escrowTransactionId, revieweeUserId }: { escrowTransactionId:number; revieweeUserId:number; }){
  const [score,setScore]=useState(5); const [content,setContent]=useState('');
  return <div className='space-y-2'><input className='border p-2' type='number' min={1} max={5} value={score} onChange={e=>setScore(Number(e.target.value))}/><textarea className='border p-2 w-full' value={content} onChange={e=>setContent(e.target.value)}/><button className='border px-3 py-2' onClick={async()=>{await createTradeReview({escrowTransactionId,revieweeUserId,scoreKindness:score,scorePunctuality:score,scoreQuality:score,content}); alert('후기 등록 완료');}}>후기 등록</button></div>;
}
