'use client';

import { useEffect, useState } from 'react';

import { createFamilyEvent, createFamilyMember, deleteFamilyEvent, deleteFamilyMember, getFamilyEvents, getFamilyMembers, type FamilyEvent, type FamilyMember } from '@/lib/api/s9';

export default function FamilyPage(){
  const [members,setMembers]=useState<FamilyMember[]>([]);
  const [events,setEvents]=useState<FamilyEvent[]>([]);
  const [name,setName]=useState(''); const [relation,setRelation]=useState(''); const [birthDate,setBirthDate]=useState('');
  const [title,setTitle]=useState(''); const [eventDate,setEventDate]=useState(''); const [memberId,setMemberId]=useState<number|undefined>(undefined);
  const load=()=>Promise.all([getFamilyMembers().then(setMembers), getFamilyEvents().then(setEvents)]);
  useEffect(()=>{ load(); },[]);

  return <main className="p-6 space-y-6">
    <h1 className="text-2xl font-bold">가족 캘린더</h1>

    <section className="space-y-2">
      <h2 className="font-semibold">가족 구성원 등록</h2>
      <div className="flex flex-wrap gap-2"><input className="border p-2" placeholder="이름" value={name} onChange={e=>setName(e.target.value)}/><input className="border p-2" placeholder="관계" value={relation} onChange={e=>setRelation(e.target.value)}/><input className="border p-2" type="date" value={birthDate} onChange={e=>setBirthDate(e.target.value)}/><button className="px-3 py-2 bg-black text-white rounded" onClick={async()=>{await createFamilyMember({name,relation,birthDate,lunar:false}); setName(''); setRelation(''); setBirthDate(''); load();}}>추가</button></div>
      <ul className="space-y-1">{members.map(m=><li key={m.id} className="border rounded p-2 flex justify-between">{m.name} · {m.relation} · {m.birthDate}<button className="text-red-600" onClick={async()=>{await deleteFamilyMember(m.id); load();}}>삭제</button></li>)}</ul>
    </section>

    <section className="space-y-2">
      <h2 className="font-semibold">가족 행사 등록</h2>
      <div className="flex flex-wrap gap-2"><input className="border p-2" placeholder="행사명" value={title} onChange={e=>setTitle(e.target.value)}/><input className="border p-2" type="date" value={eventDate} onChange={e=>setEventDate(e.target.value)}/><select className="border p-2" value={memberId ?? ''} onChange={e=>setMemberId(e.target.value?Number(e.target.value):undefined)}><option value="">구성원 선택(선택)</option>{members.map(m=><option key={m.id} value={m.id}>{m.name}</option>)}</select><button className="px-3 py-2 bg-black text-white rounded" onClick={async()=>{await createFamilyEvent({title,eventDate,lunar:false,familyMemberId:memberId,remindDaysBefore:3,repeatYearly:true}); setTitle(''); setEventDate(''); setMemberId(undefined); load();}}>추가</button></div>
      <ul className="space-y-1">{events.map(e=><li key={e.id} className="border rounded p-2 flex justify-between">{e.title} · {e.eventDate} · D-{e.remindDaysBefore}<button className="text-red-600" onClick={async()=>{await deleteFamilyEvent(e.id); load();}}>삭제</button></li>)}</ul>
    </section>
  </main>;
}
