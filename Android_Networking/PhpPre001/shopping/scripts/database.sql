
--

create database if not exists `Shopping`;

USE `Shopping`;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS `tblUsers` (
  `id` int(11) NOT NULL auto_increment PRIMARY KEY,      
  `hash_password` varchar(60)  NOT NULL,
  `email` varchar(255)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS `tblPasswordResets` (
  `id` int(11) NOT NULL auto_increment PRIMARY KEY,   
  `email` varchar(255)  NOT NULL,   
  `token` varchar(255)  NOT NULL UNIQUE ,
  `created` DATETIME not null DEFAULT now(),
  `available` bit not null DEFAULT 1
);

-- can phai co bien thoi gian tao created, bien da dung available


CREATE TABLE IF NOT EXISTS `tblCategories` (
  `id` int(11) NOT NULL auto_increment PRIMARY KEY,   
  `name` varchar(255)  NOT NULL
);

CREATE TABLE IF NOT EXISTS `tblProducts` (
  `id` int(11) NOT NULL auto_increment PRIMARY KEY,   
  `name` varchar(255) NOT NULL default '',       
  `price` decimal(5,2) NOT NULL ,     
  `created`  DATE NOT NULL default curdate(),     
  `category_id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL default '',    
   FOREIGN KEY(`category_id`) REFERENCES tblCategories(`id`)
);

INSERT INTO `tblUsers` (`hash_password`, `email`) VALUES('$2y$10$fFbvDy8znBjYM9qRCbg/YeBYTXK4WpSUvbQrI3kPhX73kFeFCKX6G', 'channn3@fpt.edu.vn');
INSERT INTO `tblCategories`(`name`) VALUES ('Mobile'), ('Laptop');

-- drop database Shopping
-- php -S 127.0.0.1:8081