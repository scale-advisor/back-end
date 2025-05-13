ALTER TABLE `user` ADD COLUMN `confirmed` CHAR(1) NOT NULL DEFAULT 'N' AFTER `login_type`;
