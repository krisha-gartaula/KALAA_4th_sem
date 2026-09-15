## Leaf Tour and Travels — Lightweight WP Starter

This package includes a minimal WordPress theme and demo content inspired by the layout of globalrisingtravel.com (not a copy), optimized to stay well under 900 MB when exported via All-in-One WP Migration.

Contents:
- theme/leaf-travel: Minimal theme (dark green branding)
- content/demo.xml: Demo content (pages + sample tours)
- scripts/setup-wpcli.sh and setup-wpcli.ps1: Optional WP-CLI bootstrap

Recommended WordPress version: 6.3+ (PHP 7.4+ or 8.x)

### Quick Install (No CLI)
1) In your WordPress Admin, go to Appearance > Themes > Add New > Upload Theme and upload `theme/leaf-travel` as a .zip.
   - To zip: compress the `leaf-travel` folder only, not the entire package.
2) Activate the theme.
3) Install the Gutenberg block editor (built-in) and optionally a forms plugin (e.g., Contact Form 7 or WPForms).
4) Import demo content:
   - Tools > Import > WordPress > Install importer > Run importer.
   - Upload `content/demo.xml`.
   - Assign a user to imported content. Check the option to import attachments (no large media included).
5) Go to Settings > Reading and set "Your homepage displays" to "A static page" with Homepage: Home.
6) Appearance > Menus: set Primary menu to `Main Menu`.
7) Customize brand color if needed: Appearance > Customize > Additional CSS and adjust the `--brand-dark-green` variable.

### Optional: WP-CLI Setup
If you have SSH:
```bash
bash scripts/setup-wpcli.sh
```
Or on Windows PowerShell:
```powershell
./scripts/setup-wpcli.ps1
```

These scripts will:
- Create pages (Home, Tours, Services, About, Contact)
- Create the `tour` custom post type and sample posts (done by theme + WP-CLI meta)
- Set the static front page and menu

### Notes
- Brand color default: #1F7A5C. Adjust in `assets/css/brand.css` or via Customizer.
- Keep media assets lightweight. Replace placeholders after import to keep exports < 900 MB.
- This theme avoids heavy builders; it uses core blocks and simple templates.

### Pages Included (Demo)
- Home: Hero, Popular Tours, Services, Reviews, Contact callout
- Tours (archive): Lists tour posts with price & duration
- Tour (single): Gallery placeholder, highlights, itinerary sections
- Services, About, Contact: Simple starter sections

### Support
If you need the final All-in-One WP Migration `.wpress` export, import this package on a fresh WP site, swap your assets/content, then export via All-in-One WP Migration. The resulting file will be far smaller than 900 MB unless you add heavy media.


