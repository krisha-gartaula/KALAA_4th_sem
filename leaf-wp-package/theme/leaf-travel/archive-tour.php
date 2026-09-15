<?php if (!defined('ABSPATH')) { exit; }
get_header(); ?>

<section class="section">
  <div class="container">
    <h1>Tours</h1>
    <div class="grid cols-3" style="margin-top:2vh;">
      <?php if (have_posts()) : while (have_posts()) : the_post(); ?>
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
      <?php endwhile; else: ?>
        <p>No tours found.</p>
      <?php endif; ?>
    </div>
  </div>
</section>

<?php get_footer();


