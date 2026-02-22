import { cn } from '@/lib/utils/cn';

interface FooterProps {
  className?: string;
}

function Footer({ className }: FooterProps) {
  return (
    <footer
      className={cn(
        'border-t border-border bg-surface px-4 py-6 text-center text-xs text-text-muted',
        className,
      )}
    >
      <p>&copy; {new Date().getFullYear()} WeddingMate. All rights reserved.</p>
    </footer>
  );
}

export { Footer };
export type { FooterProps };
