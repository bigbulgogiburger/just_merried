'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';

import { getLifeDashboard, type LifeDashboardResponse } from '@/lib/api/s9';

export default function LifePage(){
  const [data,setData]=useState<LifeDashboardResponse | null>(null);
  useEffect(()=>{ getLifeDashboard().then(setData); },[]);

  return <main className="p-6 space-y-4">
    <h1 className="text-2xl font-bold">결혼 생활 대시보드</h1>
    <div className="grid md:grid-cols-3 gap-3">
      <div className="border rounded p-4"><p className="text-sm text-gray-500">결혼 D+Day</p><p className="text-xl font-semibold">D+{data?.dDay ?? 0}</p></div>
      <div className="border rounded p-4"><p className="text-sm text-gray-500">다가오는 가족 행사(30일)</p><p className="text-xl font-semibold">{data?.upcomingEventCount ?? 0}건</p></div>
      <div className="border rounded p-4"><p className="text-sm text-gray-500">신혼집 체크리스트 진행률</p><p className="text-xl font-semibold">{data ? `${data.newHomeCompleted}/${data.newHomeTotal}` : '0/0'}</p></div>
    </div>
    <div className="flex gap-2">
      <Link href="/life/family" className="px-3 py-2 rounded bg-black text-white">가족 캘린더</Link>
      <Link href="/life/newhome" className="px-3 py-2 rounded border">신혼집 체크리스트</Link>
    </div>
  </main>;
}
