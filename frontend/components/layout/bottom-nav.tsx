'use client';

import {
  Heart,
  Home,
  MessageSquare,
  ShoppingBag,
  User,
} from 'lucide-react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

import { cn } from '@/lib/utils/cn';

type NavHref = '/' | '/prepare' | '/community' | '/market' | '/my';

interface NavItem {
  label: string;
  href: NavHref;
  icon: React.ElementType;
}

const navItems: NavItem[] = [
  { label: '홈', href: '/', icon: Home },
  { label: '결혼준비', href: '/prepare', icon: Heart },
  { label: '커뮤니티', href: '/community', icon: MessageSquare },
  { label: '마켓', href: '/market', icon: ShoppingBag },
  { label: 'MY', href: '/my', icon: User },
];

function BottomNav() {
  const pathname = usePathname();

  return (
    <nav className="fixed bottom-0 left-0 right-0 z-[var(--z-fixed)] border-t border-border bg-surface safe-bottom">
      <ul className="mx-auto flex h-16 max-w-lg items-center justify-around">
        {navItems.map((item) => {
          const isActive =
            item.href === '/'
              ? pathname === '/'
              : pathname.startsWith(item.href);
          const Icon = item.icon;

          return (
            <li key={item.href}>
              <Link
                href={item.href}
                className={cn(
                  'flex flex-col items-center gap-0.5 px-3 py-1 text-xs transition-colors',
                  isActive
                    ? 'text-primary'
                    : 'text-text-muted hover:text-text-secondary',
                )}
              >
                <Icon
                  className={cn(
                    'h-5 w-5',
                    isActive && 'fill-primary/20',
                  )}
                  strokeWidth={isActive ? 2.5 : 2}
                />
                <span className={cn(isActive && 'font-semibold')}>
                  {item.label}
                </span>
              </Link>
            </li>
          );
        })}
      </ul>
    </nav>
  );
}

export { BottomNav };
