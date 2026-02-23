import './globals.css';

import { QueryProvider } from '@/lib/providers/query-provider';
import { AppToastProvider } from '@/lib/providers/toast-provider';

import type { Metadata, Viewport } from 'next';

export const metadata: Metadata = {
  title: {
    default: 'WeddingMate - 결혼 준비의 모든 것',
    template: '%s | WeddingMate',
  },
  description:
    '웨딩메이트와 함께 결혼 준비를 스마트하게. 체크리스트, 예산 관리, 업체 비교까지 한번에.',
  keywords: ['결혼', '웨딩', '결혼준비', '웨딩플래너', '웨딩메이트'],
};

export const viewport: Viewport = {
  width: 'device-width',
  initialScale: 1,
  maximumScale: 1,
  userScalable: false,
  themeColor: '#B76E79',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body className="font-sans antialiased">
        <QueryProvider>
          <AppToastProvider>{children}</AppToastProvider>
        </QueryProvider>
      </body>
    </html>
  );
}
