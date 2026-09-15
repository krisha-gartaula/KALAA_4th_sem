-- Generated SQL schema for Kalaa project
CREATE DATABASE IF NOT EXISTS kalaa DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kalaa;

CREATE TABLE IF NOT EXISTS `user` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role ENUM('user','admin') DEFAULT 'user',
  is_verified TINYINT(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `categories` (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  description TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Authors table for story attribution
CREATE TABLE IF NOT EXISTS `authors` (
  author_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  bio TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `stories` (
  story_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author_id INT NOT NULL,
  category INT,
  content TEXT,
  image_path VARCHAR(255) NULL,
  FOREIGN KEY (author_id) REFERENCES `user`(id) ON DELETE CASCADE,
  FOREIGN KEY (category) REFERENCES `categories`(category_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- sample admin user (password is 'admin', securely stored as BCrypt hash; backward compatibility also supports legacy plain text)
INSERT INTO `user` (username, email, password, role, is_verified) VALUES ('admin', 'admin@example.com', '$2a$12$LWhSTdmfz7wl61wrxFlWre9gToHwDaq8JNqNSvW7kSaJymJP/7W2C', 'admin', 1);

-- sample category
INSERT INTO `categories` (name, description) VALUES ('Fiction', 'Fictional stories');

-- sample story (author_id 1 = admin)
INSERT INTO `stories` (title, author_id, category) VALUES ('Sample Story', 1, 1);

-- Password reset tokens for forgot/reset password feature
CREATE TABLE IF NOT EXISTS `password_resets` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(150) NOT NULL,
  token VARCHAR(100) NOT NULL UNIQUE,
  expiry_time DATETIME NOT NULL,
  is_used TINYINT(1) DEFAULT 0,
  INDEX idx_reset_token (token),
  INDEX idx_reset_email (email),
  FOREIGN KEY (email) REFERENCES `user`(email) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Email verification tokens (6-digit OTP) for user registration
CREATE TABLE IF NOT EXISTS `email_verifications` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(150) NOT NULL,
  code VARCHAR(10) NOT NULL,
  expiry_time DATETIME NOT NULL,
  is_used TINYINT(1) DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_verif_email (email),
  INDEX idx_verif_code (code),
  FOREIGN KEY (email) REFERENCES `user`(email) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;