# WeddingMate Icon Guidelines

## Base Specifications

| Property       | Value           |
|----------------|-----------------|
| Base size      | 24x24 px        |
| Stroke width   | 1.5px           |
| Stroke cap     | Round           |
| Stroke join    | Round           |
| View box       | 0 0 24 24       |
| Padding        | 2px (live area: 20x20) |
| Fill           | none (outline style) |

## Size Variants

| Name   | Size    | Context                           |
|--------|---------|-----------------------------------|
| xs     | 14px    | Inline with caption text, badges  |
| sm     | 16px    | Inline with body2 text, buttons   |
| md     | 20px    | Default in buttons, inputs        |
| base   | 24px    | Standard standalone icon          |
| lg     | 32px    | Empty states, feature highlights  |
| xl     | 48px    | Onboarding illustrations          |

## Color Usage

- **Default**: `{color.neutral.600}` (#6B6B6B)
- **Active / Selected**: `{color.primary.500}` (#B76E79)
- **Disabled**: `{color.neutral.300}` (#BFBFBF)
- **On primary background**: `#FFFFFF`
- **Semantic**: Match the semantic color for status icons
  - Success: `{color.semantic.success}`
  - Warning: `{color.semantic.warning}`
  - Error: `{color.semantic.error}`
  - Info: `{color.semantic.info}`

## Variants

Navigation icons and interactive icons (heart, bookmark, notification) have two variants:
- **Outline**: Default / unselected state. Stroke only, no fill.
- **Filled**: Active / selected state. Solid fill with matching color.

## Design Rules

1. All icons must sit on a **pixel grid** for crisp rendering at 1x.
2. Maintain **consistent visual weight** across all icons.
3. Use **2px padding** from the 24x24 bounding box. Draw within the 20x20 live area.
4. Use **round stroke caps and joins** for a friendly, approachable feel consistent with the brand.
5. Avoid overly detailed icons. Keep paths simple with minimal anchor points.
6. Ensure all icons are **optically balanced** -- centered within the bounding box.
7. Export as **SVG** with `currentColor` for dynamic color application.

## SVG Export Template

```svg
<svg xmlns="http://www.w3.org/2000/svg"
     width="24" height="24"
     viewBox="0 0 24 24"
     fill="none"
     stroke="currentColor"
     stroke-width="1.5"
     stroke-linecap="round"
     stroke-linejoin="round">
  <!-- paths here -->
</svg>
```

## Naming Convention

- Lowercase kebab-case: `arrow-left`, `check-circle`
- Filled variants suffix: `heart-filled`, `home-filled`
- File naming: `{icon-name}.svg`, `{icon-name}-filled.svg`

## Touch Targets

When icons are interactive (buttons, toggles), ensure a minimum touch target of **44x44px** regardless of icon size. Use padding to achieve this.
