import { expect, test } from '@playwright/test';

test('회원 시작 동선 접근 가능', async ({ page }) => {
  await page.goto('/');
  await expect(page).toHaveTitle(/WeddingMate/);

  await page.getByRole('link', { name: /시작하기|로그인/i }).first().click();
  await expect(page).toHaveURL(/login|onboarding/);
});

test('핵심 허브 페이지 로딩', async ({ page }) => {
  const routes = ['/home', '/prepare', '/community', '/market', '/life'];

  for (const route of routes) {
    await page.goto(route);
    await expect(page.locator('main')).toBeVisible();
  }
});
