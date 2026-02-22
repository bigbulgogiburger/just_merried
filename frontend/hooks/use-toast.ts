'use client';

import { useCallback, useState } from 'react';

import type { ToastProps } from '@/components/ui/toast';

interface ToastItem extends Pick<ToastProps, 'variant'> {
  id: string;
  title?: string;
  description?: string;
}

function useToast() {
  const [toasts, setToasts] = useState<ToastItem[]>([]);

  const addToast = useCallback(
    (toast: Omit<ToastItem, 'id'>) => {
      const id = Math.random().toString(36).slice(2, 9);
      setToasts((prev) => [...prev, { ...toast, id }]);
    },
    [],
  );

  const removeToast = useCallback((id: string) => {
    setToasts((prev) => prev.filter((t) => t.id !== id));
  }, []);

  const success = useCallback(
    (title: string, description?: string) =>
      addToast({ variant: 'success', title, description }),
    [addToast],
  );

  const error = useCallback(
    (title: string, description?: string) =>
      addToast({ variant: 'error', title, description }),
    [addToast],
  );

  const warning = useCallback(
    (title: string, description?: string) =>
      addToast({ variant: 'warning', title, description }),
    [addToast],
  );

  const info = useCallback(
    (title: string, description?: string) =>
      addToast({ variant: 'info', title, description }),
    [addToast],
  );

  return {
    toasts,
    addToast,
    removeToast,
    success,
    error,
    warning,
    info,
  };
}

export { useToast };
export type { ToastItem };
