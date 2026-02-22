'use client';

import { X } from 'lucide-react';

import { cn } from '@/lib/utils/cn';

interface ChipProps extends React.HTMLAttributes<HTMLButtonElement> {
  selected?: boolean;
  onRemove?: () => void;
  disabled?: boolean;
}

function Chip({
  className,
  children,
  selected = false,
  onRemove,
  disabled = false,
  ...props
}: ChipProps) {
  return (
    <button
      type="button"
      disabled={disabled}
      className={cn(
        'inline-flex items-center gap-1 rounded-full border px-3 py-1.5 text-sm font-medium transition-colors',
        selected
          ? 'border-primary bg-primary/10 text-primary'
          : 'border-border bg-surface text-text-secondary hover:border-primary/30 hover:text-text-primary',
        disabled && 'cursor-not-allowed opacity-50',
        className,
      )}
      {...props}
    >
      {children}
      {onRemove && (
        <X
          className="h-3.5 w-3.5 cursor-pointer opacity-60 hover:opacity-100"
          onClick={(e) => {
            e.stopPropagation();
            onRemove();
          }}
        />
      )}
    </button>
  );
}

export { Chip };
export type { ChipProps };
