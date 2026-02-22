'use client';

import { ChevronLeft } from 'lucide-react';
import { useRouter } from 'next/navigation';

import { cn } from '@/lib/utils/cn';

interface HeaderProps {
  title?: string;
  showBack?: boolean;
  onBack?: () => void;
  rightAction?: React.ReactNode;
  className?: string;
}

function Header({
  title,
  showBack = false,
  onBack,
  rightAction,
  className,
}: HeaderProps) {
  const router = useRouter();

  const handleBack = () => {
    if (onBack) {
      onBack();
    } else {
      router.back();
    }
  };

  return (
    <header
      className={cn(
        'sticky top-0 z-[var(--z-sticky)] flex h-14 items-center border-b border-border bg-surface px-4 safe-top',
        className,
      )}
    >
      <div className="flex w-10 items-center">
        {showBack && (
          <button
            type="button"
            onClick={handleBack}
            className="flex h-10 w-10 items-center justify-center rounded-full transition-colors hover:bg-background"
            aria-label="뒤로 가기"
          >
            <ChevronLeft className="h-5 w-5 text-text-primary" />
          </button>
        )}
      </div>
      <h1 className="flex-1 text-center text-base font-semibold text-text-primary">
        {title}
      </h1>
      <div className="flex w-10 items-center justify-end">{rightAction}</div>
    </header>
  );
}

export { Header };
export type { HeaderProps };
