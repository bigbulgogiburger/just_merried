import type { MetadataRoute } from 'next';

export default function sitemap(): MetadataRoute.Sitemap {
  const siteUrl = process.env.NEXT_PUBLIC_APP_URL ?? 'https://weddingmate.app';
  const now = new Date();

  const routes = [
    '',
    '/home',
    '/prepare',
    '/vendors',
    '/vendors/compare',
    '/community',
    '/market',
    '/life',
    '/business',
  ];

  return routes.map((route) => ({
    url: `${siteUrl}${route}`,
    lastModified: now,
    changeFrequency: route === '' ? 'daily' : 'weekly',
    priority: route === '' ? 1 : 0.7,
  }));
}
