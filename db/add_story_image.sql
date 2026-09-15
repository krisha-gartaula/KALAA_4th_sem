-- Run this in phpMyAdmin on the kalaa database
USE kalaa;

ALTER TABLE stories
  ADD COLUMN image_path VARCHAR(255) NULL AFTER content;
