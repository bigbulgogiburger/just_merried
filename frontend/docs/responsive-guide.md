# Responsive Design Guide - WeddingMate Frontend

## Breakpoints

| Name    | Min Width | Target Devices              |
|---------|----------|-----------------------------|
| mobile  | 375px    | Mobile phones               |
| tablet  | 768px    | Tablets, small laptops      |
| desktop | 1280px   | Desktops, large laptops     |
| wide    | 1536px   | Wide monitors, 4K displays  |

## Usage in Tailwind CSS

```tsx
// Mobile-first approach: base styles apply to mobile
<div className="px-4 tablet:px-6 desktop:px-8">
  <h1 className="text-xl tablet:text-2xl desktop:text-3xl">Title</h1>
</div>
```

## Grid Patterns

```tsx
// Product grid: 1 col mobile, 2 cols tablet, 3-4 cols desktop
<div className="grid grid-cols-1 gap-4 tablet:grid-cols-2 desktop:grid-cols-3 wide:grid-cols-4">
  {items.map(item => <Card key={item.id} />)}
</div>
```

## Container

Use the `wedding-container` utility class for consistent max-width and padding:

```tsx
<div className="wedding-container">
  {/* Content constrained to 1280px max-width with responsive padding */}
</div>
```

## Section Spacing

Use the `wedding-section` utility class for consistent section padding:

```tsx
<section className="wedding-section">
  {/* px-4 py-6 on mobile, px-6 py-8 on tablet, px-8 py-10 on desktop */}
</section>
```

## Bottom Navigation

The bottom navigation is fixed at 64px (h-16) height. Main content should have `pb-16` to prevent overlap.

## Safe Areas

Use `safe-top` and `safe-bottom` utility classes for iOS safe area insets:

```tsx
<header className="safe-top">...</header>
<nav className="safe-bottom">...</nav>
```

## Typography Scale

| Class     | Size    | Use Case              |
|-----------|---------|-----------------------|
| text-xs   | 12px    | Captions, timestamps  |
| text-sm   | 14px    | Body small, labels    |
| text-base | 16px    | Body text             |
| text-lg   | 18px    | Subtitles             |
| text-xl   | 20px    | Section headers       |
| text-2xl  | 24px    | Page titles (mobile)  |
| text-3xl  | 30px    | Page titles (desktop) |
| text-4xl  | 36px    | Hero text             |
