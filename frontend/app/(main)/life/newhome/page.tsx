'use client';

import { useEffect, useState } from 'react';

import { createNewHomeItem, getNewHomeItems, updateNewHomeItem, type NewHomeItem } from '@/lib/api/s9';

const categories = ['MOVE','APPLIANCE','FURNITURE','INTERIOR'] as const;

export default function NewHomePage(){
  const [items,setItems]=useState<NewHomeItem[]>([]);
  const [category,setCategory]=useState<typeof categories[number]>('MOVE');
  const [title,setTitle]=useState('');
  const load=()=>getNewHomeItems().then(setItems);
  useEffect(()=>{ load(); },[]);

  return <main className="p-6 space-y-4">
    <h1 className="text-2xl font-bold">신혼집 체크리스트</h1>
    <div className="flex gap-2"><select className="border p-2" value={category} onChange={e=>setCategory(e.target.value as typeof categories[number])}>{categories.map(c=><option key={c} value={c}>{c}</option>)}</select><input className="border p-2" placeholder="항목" value={title} onChange={e=>setTitle(e.target.value)}/><button className="px-3 py-2 bg-black text-white rounded" onClick={async()=>{await createNewHomeItem({category,title,completed:false}); setTitle(''); load();}}>추가</button></div>
    <ul className="space-y-2">{items.map(item=><li key={item.id} className="border rounded p-3 flex justify-between items-center"><span>{item.category} · {item.title}</span><button className={item.completed? 'text-gray-500':'text-green-600'} onClick={async()=>{await updateNewHomeItem(item.id,{category:item.category,title:item.title,memo:item.memo,assignee:item.assignee,completed:!item.completed}); load();}}>{item.completed?'완료됨':'완료 처리'}</button></li>)}</ul>
  </main>;
}
