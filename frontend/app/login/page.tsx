'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { socialLogin } from '@/lib/api/s2';
import { useAuthStore } from '@/stores/auth-store';

const PROVIDERS = [
  { key: 'kakao', label: '카카오 로그인' },
  { key: 'naver', label: '네이버 로그인' },
  { key: 'google', label: '구글 로그인' },
  { key: 'apple', label: 'Apple 로그인' },
] as const;

export default function LoginPage() {
  const router = useRouter();
  const login = useAuthStore((s) => s.login);
  const [mockToken, setMockToken] = useState('dev-social-access-token');
  const [loadingProvider, setLoadingProvider] = useState<string | null>(null);

  const onLogin = async (provider: (typeof PROVIDERS)[number]['key']) => {
    try {
      setLoadingProvider(provider);
      const tokens = await socialLogin(provider, mockToken);
      login(
        { id: 'me', email: 'me@weddingmate.app', nickname: '신혼메이트', role: 'BRIDE' },
        tokens.accessToken,
        tokens.refreshToken,
      );
      router.push('/onboarding');
    } finally {
      setLoadingProvider(null);
    }
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>로그인</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        <Input
          label="개발용 소셜 토큰"
          value={mockToken}
          onChange={(e) => setMockToken(e.target.value)}
        />
        <div className="grid gap-2">
          {PROVIDERS.map((provider) => (
            <Button
              key={provider.key}
              variant="outline"
              isLoading={loadingProvider === provider.key}
              onClick={() => onLogin(provider.key)}
            >
              {provider.label}
            </Button>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
