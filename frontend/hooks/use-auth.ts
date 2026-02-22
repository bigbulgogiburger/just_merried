'use client';

import { useCallback } from 'react';

import { apiClient } from '@/lib/api/client';
import { handleApiError } from '@/lib/api/error-handler';
import { useAuthStore } from '@/stores/auth-store';

import type { User } from '@/stores/auth-store';
import type { ApiResponse } from '@/types/api';

function useAuth() {
  const {
    user,
    accessToken,
    isAuthenticated,
    isLoading,
    login: storeLogin,
    logout: storeLogout,
    setLoading,
    setUser,
  } = useAuthStore();

  const login = useCallback(
    async (email: string, password: string) => {
      try {
        setLoading(true);
        const { data } = await apiClient.post<
          ApiResponse<{
            user: User;
            accessToken: string;
            refreshToken: string;
          }>
        >('/auth/login', { email, password });

        storeLogin(
          data.data.user,
          data.data.accessToken,
          data.data.refreshToken,
        );
      } catch (error) {
        setLoading(false);
        throw handleApiError(error);
      }
    },
    [storeLogin, setLoading],
  );

  const logout = useCallback(async () => {
    try {
      await apiClient.post('/auth/logout');
    } catch {
      // Proceed with local logout even if API fails
    } finally {
      storeLogout();
    }
  }, [storeLogout]);

  const refreshProfile = useCallback(async () => {
    try {
      const { data } = await apiClient.get<ApiResponse<User>>('/users/me');
      setUser(data.data);
    } catch (error) {
      throw handleApiError(error);
    }
  }, [setUser]);

  return {
    user,
    accessToken,
    isAuthenticated,
    isLoading,
    login,
    logout,
    refreshProfile,
  };
}

export { useAuth };
