<?php
/**
 * Leaf Travel Theme functions
 */

if (!defined('ABSPATH')) { exit; }

// Theme setup
add_action('after_setup_theme', function () {
    add_theme_support('title-tag');
    add_theme_support('post-thumbnails');
    add_theme_support('menus');
    register_nav_menus([
        'primary' => __('Primary Menu', 'leaf-travel'),
    ]);
});

// Enqueue styles and scripts
add_action('wp_enqueue_scripts', function () {
    $theme_version = wp_get_theme()->get('Version');
    wp_enqueue_style('leaf-travel-style', get_stylesheet_uri(), [], $theme_version);
    wp_enqueue_style('leaf-travel-brand', get_template_directory_uri() . '/assets/css/brand.css', ['leaf-travel-style'], $theme_version);
    wp_enqueue_script('leaf-travel-nav', get_template_directory_uri() . '/assets/js/nav.js', [], $theme_version, true);
});

// Register Tour custom post type (lightweight)
add_action('init', function () {
    $labels = [
        'name' => __('Tours', 'leaf-travel'),
        'singular_name' => __('Tour', 'leaf-travel'),
    ];
    register_post_type('tour', [
        'labels' => $labels,
        'public' => true,
        'has_archive' => true,
        'menu_icon' => 'dashicons-location-alt',
        'supports' => ['title', 'editor', 'thumbnail', 'excerpt'],
        'rewrite' => ['slug' => 'tours'],
        'show_in_rest' => true,
    ]);

    // Meta fields via register_post_meta for price/duration/destination
    register_post_meta('tour', 'tour_price', [
        'type' => 'string', 'single' => true, 'show_in_rest' => true,
        'auth_callback' => function() { return current_user_can('edit_posts'); }
    ]);
    register_post_meta('tour', 'tour_duration', [
        'type' => 'string', 'single' => true, 'show_in_rest' => true,
        'auth_callback' => function() { return current_user_can('edit_posts'); }
    ]);
    register_post_meta('tour', 'tour_destination', [
        'type' => 'string', 'single' => true, 'show_in_rest' => true,
        'auth_callback' => function() { return current_user_can('edit_posts'); }
    ]);
});

// Helper to get meta with fallback
function leaf_get_meta(string $key, $post_id = null): string {
    $post_id = $post_id ?: get_the_ID();
    $value = get_post_meta($post_id, $key, true);
    return is_string($value) ? $value : '';
}


