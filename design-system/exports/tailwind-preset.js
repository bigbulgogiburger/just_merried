/**
 * WeddingMate Design System - Tailwind CSS Preset
 * Generated from DTCG tokens
 *
 * Usage in tailwind.config.js:
 *   const weddingMatePreset = require('./design-system/exports/tailwind-preset');
 *   module.exports = { presets: [weddingMatePreset], ... }
 */

/** @type {import('tailwindcss').Config} */
module.exports = {
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#FDF2F4',
          100: '#FAE5E8',
          200: '#F5CCD2',
          300: '#ECAAB3',
          400: '#D4808D',
          500: '#B76E79',
          600: '#9C4F5A',
          700: '#7D3A44',
          800: '#5E2B33',
          900: '#3F1C22',
          DEFAULT: '#B76E79',
        },
        secondary: {
          50: '#FFFFF0',
          100: '#FFFFE0',
          200: '#FFFFD0',
          300: '#FFFFC0',
          400: '#FFFFB0',
          500: '#FFFFF0',
          DEFAULT: '#FFFFF0',
        },
        accent: {
          50: '#FBF5F1',
          100: '#F7EBE3',
          200: '#EFD7C7',
          300: '#E5C0A8',
          400: '#DCAE96',
          500: '#C89478',
          600: '#B07A5E',
          700: '#8E6048',
          800: '#6C4836',
          900: '#4A3225',
          DEFAULT: '#DCAE96',
        },
        neutral: {
          50: '#F5F5F5',
          100: '#EBEBEB',
          200: '#D9D9D9',
          300: '#BFBFBF',
          400: '#9B9B9B',
          500: '#8A8A8A',
          600: '#6B6B6B',
          700: '#525252',
          800: '#3D3D3D',
          900: '#2D2D2D',
        },
        success: '#4CAF50',
        warning: '#FF9800',
        error: '#F44336',
        info: '#2196F3',
        background: {
          primary: '#FFF9F5',
          secondary: '#FFFFFF',
          surface: '#FFFFF0',
        },
        social: {
          kakao: '#FEE500',
          naver: '#03C75A',
          google: '#FFFFFF',
          apple: '#000000',
        },
      },
      fontFamily: {
        sans: [
          'Pretendard',
          '-apple-system',
          'BlinkMacSystemFont',
          'Segoe UI',
          'Roboto',
          'sans-serif',
        ],
      },
      fontSize: {
        display: ['36px', { lineHeight: '44px', fontWeight: '700', letterSpacing: '-0.02em' }],
        h1: ['28px', { lineHeight: '36px', fontWeight: '700', letterSpacing: '-0.01em' }],
        h2: ['24px', { lineHeight: '32px', fontWeight: '600', letterSpacing: '-0.01em' }],
        h3: ['20px', { lineHeight: '28px', fontWeight: '600', letterSpacing: '0' }],
        h4: ['18px', { lineHeight: '26px', fontWeight: '500', letterSpacing: '0' }],
        body1: ['16px', { lineHeight: '24px', fontWeight: '400', letterSpacing: '0' }],
        body2: ['14px', { lineHeight: '22px', fontWeight: '400', letterSpacing: '0' }],
        caption: ['12px', { lineHeight: '18px', fontWeight: '400', letterSpacing: '0.01em' }],
        overline: ['10px', { lineHeight: '14px', fontWeight: '500', letterSpacing: '0.05em' }],
      },
      fontWeight: {
        regular: '400',
        medium: '500',
        semibold: '600',
        bold: '700',
      },
      spacing: {
        1: '4px',
        2: '8px',
        3: '12px',
        4: '16px',
        5: '20px',
        6: '24px',
        8: '32px',
        10: '40px',
        12: '48px',
        16: '64px',
        20: '80px',
        24: '96px',
      },
      borderRadius: {
        xs: '2px',
        sm: '4px',
        md: '8px',
        lg: '12px',
        xl: '16px',
        '2xl': '24px',
        full: '9999px',
      },
      boxShadow: {
        sm: '0 1px 2px rgba(0, 0, 0, 0.05)',
        md: '0 4px 6px rgba(0, 0, 0, 0.07)',
        lg: '0 10px 15px rgba(0, 0, 0, 0.1)',
        xl: '0 20px 25px rgba(0, 0, 0, 0.1)',
      },
    },
  },
};
