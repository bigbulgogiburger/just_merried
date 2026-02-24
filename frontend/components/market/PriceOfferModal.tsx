'use client';
import { useState } from 'react';

import { createOffer } from '@/lib/api/s8';

export default function PriceOfferModal({ secondhandProductId }: { secondhandProductId: number }) {
  const [price, setPrice] = useState(0);
  return <div className='space-x-2'><input className='border p-2' type='number' value={price} onChange={e=>setPrice(Number(e.target.value))}/><button className='border px-3 py-2' onClick={async()=>{await createOffer({secondhandProductId,offeredPrice:price}); alert('가격 제안 완료');}}>가격 제안</button></div>;
}
