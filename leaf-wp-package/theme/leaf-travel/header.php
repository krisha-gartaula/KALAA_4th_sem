<?php if (!defined('ABSPATH')) { exit; } ?><!doctype html>
<html <?php language_attributes(); ?>>
<head>
    <meta charset="<?php bloginfo('charset'); ?>" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <?php wp_head(); ?>
</head>
<body <?php body_class(); ?>>
<header class="site-header">
  <div class="container site-brand">
    <span class="logo" aria-hidden="true"></span>
    <div class="site-title">Leaf Tour and Travels</div>
    <button class="nav-toggle" aria-label="Toggle navigation">☰</button>
    <nav class="site-nav" id="primary-nav">
      <?php wp_nav_menu([
        'theme_location' => 'primary',
        'container' => false,
        'menu_class' => '',
        'items_wrap' => '<ul>%3$s</ul>'
      ]); ?>
    </nav>
  </div>
</header>
<main class="site-main">


