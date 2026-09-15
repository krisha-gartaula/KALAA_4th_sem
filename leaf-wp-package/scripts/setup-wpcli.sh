#!/usr/bin/env bash
set -euo pipefail

# Usage: bash scripts/setup-wpcli.sh

wp option update blogname "Leaf Tour and Travels"
wp option update blogdescription "Experience Nepal with Leaf Tour & Travels"

# Pages
HOME_ID=$(wp post create --post_type=page --post_status=publish --post_title=Home --porcelain)
TOURS_ID=$(wp post create --post_type=page --post_status=publish --post_title=Tours --porcelain)
SERVICES_ID=$(wp post create --post_type=page --post_status=publish --post_title=Services --porcelain)
ABOUT_ID=$(wp post create --post_type=page --post_status=publish --post_title=About --porcelain)
CONTACT_ID=$(wp post create --post_type=page --post_status=publish --post_title=Contact --porcelain)

wp option update show_on_front page
wp option update page_on_front $HOME_ID

# Menu
MENU_ID=$(wp menu create "Main Menu")
wp menu location assign "$MENU_ID" primary || true
wp menu item add-post "$MENU_ID" $HOME_ID
wp menu item add-post "$MENU_ID" $TOURS_ID
wp menu item add-post "$MENU_ID" $SERVICES_ID
wp menu item add-post "$MENU_ID" $ABOUT_ID
wp menu item add-post "$MENU_ID" $CONTACT_ID

# Sample Tours (CPT must exist; activate theme first)
TOUR1=$(wp post create --post_type=tour --post_status=publish --post_title="Pokhara City Escape — 3D/2N" --post_content="Highlights: Fewa Lake, World Peace Pagoda, Davis Falls." --porcelain)
wp post meta set $TOUR1 tour_price "Rs. 40,000"
wp post meta set $TOUR1 tour_duration "3 Days"
wp post meta set $TOUR1 tour_destination "Pokhara"

TOUR2=$(wp post create --post_type=tour --post_status=publish --post_title="Chitwan Jungle Safari — 2D/1N" --post_content="Highlights: Tharu culture, canoeing, jungle walk." --porcelain)
wp post meta set $TOUR2 tour_price "Rs. 11,000"
wp post meta set $TOUR2 tour_duration "2 Days"
wp post meta set $TOUR2 tour_destination "Chitwan"

echo "Setup complete. Set your homepage to 'Home' if not already."


