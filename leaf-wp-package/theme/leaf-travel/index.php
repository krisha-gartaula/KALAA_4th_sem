<?php if (!defined('ABSPATH')) { exit; }
get_header(); ?>

<section class="hero">
  <div class="container">
    <h1>Experience Nepal with Leaf Tour & Travels</h1>
    <p>Curated tours, trusted guides, and unforgettable journeys.</p>
    <a class="btn btn-primary" href="<?php echo esc_url(home_url('/tours')); ?>">Explore Tours</a>
  </div>
</section>

<section class="section">
  <div class="container">
    <h2>Popular Tours</h2>
    <div class="grid cols-3">
      <?php
      $q = new WP_Query(['post_type' => 'tour', 'posts_per_page' => 6]);
      if ($q->have_posts()) :
        while ($q->have_posts()) : $q->the_post(); ?>
          <article class="card">
            <?php if (has_post_thumbnail()) { the_post_thumbnail('large'); } ?>
            <div class="card-body">
              <h3 class="card-title"><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h3>
              <div class="meta">
                <span><?php echo esc_html(leaf_get_meta('tour_duration')); ?></span>
                <span><?php echo esc_html(leaf_get_meta('tour_price')); ?></span>
              </div>
            </div>
          </article>
      <?php endwhile; wp_reset_postdata(); else: ?>
        <p>No tours yet. Add some from the dashboard.</p>
      <?php endif; ?>
    </div>
  </div>
</section>

<section class="section section-muted">
  <div class="container">
    <h2>Our Services</h2>
    <div class="grid cols-3">
      <div class="card"><div class="card-body"><h3 class="card-title">Domestic & International Ticketing</h3><p>End-to-end flight booking support.</p></div></div>
      <div class="card"><div class="card-body"><h3 class="card-title">Inbound & Outbound Tours</h3><p>From Kathmandu Valley to international escapes.</p></div></div>
      <div class="card"><div class="card-body"><h3 class="card-title">Vehicle Rental</h3><p>Comfortable, reliable cars and vans.</p></div></div>
    </div>
  </div>
</section>

<?php get_footer();


