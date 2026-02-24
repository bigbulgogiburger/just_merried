import type { MetadataRoute } from 'next';

export default function manifest(): MetadataRoute.Manifest {
  return {
    name: 'WeddingMate',
    short_name: 'WeddingMate',
    description: '결혼 준비부터 결혼 생활까지 한 번에 관리하는 웨딩메이트',
    start_url: '/',
    display: 'standalone',
    background_color: '#FFF8F9',
    theme_color: '#B76E79',
    lang: 'ko-KR',
    orientation: 'portrait',
    icons: [
      {
        src: '/icons/icon-192.svg',
        sizes: '192x192',
        type: 'image/svg+xml',
      },
      {
        src: '/icons/icon-512.svg',
        sizes: '512x512',
        type: 'image/svg+xml',
      },
      {
        src: '/icons/maskable-icon.svg',
        sizes: '512x512',
        type: 'image/svg+xml',
        purpose: 'maskable',
      },
    ],
  };
}
