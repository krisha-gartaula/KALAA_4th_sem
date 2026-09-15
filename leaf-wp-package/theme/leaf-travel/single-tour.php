<?php if (!defined('ABSPATH')) { exit; }
get_header(); ?>

<section class="section">
  <div class="container">
    <?php if (have_posts()) : while (have_posts()) : the_post(); ?>
      <article>
        <h1 class="card-title"><?php the_title(); ?></h1>
        <div class="meta" style="margin: 1vh 0 2vh;">
          <span><?php echo esc_html(leaf_get_meta('tour_destination')); ?></span>
          <span><?php echo esc_html(leaf_get_meta('tour_duration')); ?></span>
          <span><?php echo esc_html(leaf_get_meta('tour_price')); ?></span>
        </div>
        <?php if (has_post_thumbnail()) { the_post_thumbnail('large'); } ?>
        <div class="entry-content"><?php the_content(); ?></div>
        <p><a class="btn btn-primary" href="<?php echo esc_url(home_url('/contact')); ?>">Enquire Now</a></p>
      </article>
    <?php endwhile; endif; ?>
  </div>
</section>

<?php get_footer();


