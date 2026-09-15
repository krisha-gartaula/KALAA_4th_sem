param()

wp option update blogname "Leaf Tour and Travels"
wp option update blogdescription "Experience Nepal with Leaf Tour & Travels"

$homeId = wp post create --post_type=page --post_status=publish --post_title=Home --porcelain
$toursId = wp post create --post_type=page --post_status=publish --post_title=Tours --porcelain
$servicesId = wp post create --post_type=page --post_status=publish --post_title=Services --porcelain
$aboutId = wp post create --post_type=page --post_status=publish --post_title=About --porcelain
$contactId = wp post create --post_type=page --post_status=publish --post_title=Contact --porcelain

wp option update show_on_front page
wp option update page_on_front $homeId

$menuId = wp menu create "Main Menu"
wp menu location assign $menuId primary
wp menu item add-post $menuId $homeId
wp menu item add-post $menuId $toursId
wp menu item add-post $menuId $servicesId
wp menu item add-post $menuId $aboutId
wp menu item add-post $menuId $contactId

$tour1 = wp post create --post_type=tour --post_status=publish --post_title="Pokhara City Escape — 3D/2N" --post_content="Highlights: Fewa Lake, World Peace Pagoda, Davis Falls." --porcelain
wp post meta set $tour1 tour_price "Rs. 40,000"
wp post meta set $tour1 tour_duration "3 Days"
wp post meta set $tour1 tour_destination "Pokhara"

$tour2 = wp post create --post_type=tour --post_status=publish --post_title="Chitwan Jungle Safari — 2D/1N" --post_content="Highlights: Tharu culture, canoeing, jungle walk." --porcelain
wp post meta set $tour2 tour_price "Rs. 11,000"
wp post meta set $tour2 tour_duration "2 Days"
wp post meta set $tour2 tour_destination "Chitwan"

Write-Host "Setup complete."


