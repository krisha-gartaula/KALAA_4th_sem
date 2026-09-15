-- Update stories table to include content field
USE kalaa;

-- Add content column to stories table
ALTER TABLE `stories` ADD COLUMN `content` TEXT AFTER `category`;

-- Update existing sample story with some content
UPDATE `stories` SET `content` = 'This is a sample story content for testing purposes.' WHERE `story_id` = 1;
