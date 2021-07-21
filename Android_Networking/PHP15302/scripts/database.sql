CREATE DATABASE if not EXISTS ecommerce;
use ecommerce;

create table if not exists tblUsers(
    id int(11) not null AUTO_INCREMENT PRIMARY KEY,
    hash_password varchar(60) not null,
    email varchar(255) not null UNIQUE
);

create table if not exists tblPasswordResets(
    id int(11) not null AUTO_INCREMENT PRIMARY KEY,
    token varchar(255) not null UNIQUE,
    email varchar(255) not null,
    created datetime not null DEFAULT now(),
    available bit not null DEFAULT 1
);

create table if not exists tblCategories(
    id int(11) not null AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null DEFAULT ''
);

create table if not exists tblProducts(
    id int(11) not null AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null DEFAULT '',
    price decimal(5,2) not null,
    quantity int(11) not null DEFAULT 0,
    image_url varchar(255) not null DEFAULT '',
    category_id int(11) not null,
    FOREIGN key (category_id) REFERENCES tblCategories(id)
);

INSERT into tblusers(email, hash_password) values('channn3@fpt.edu.vn', '$2y$10$m/1m0dr7Z4hwUBhI.ihAOeS/AkzbFTjTgBwU9wj1oy3q.fkZGxT6m');

INSERT into tblCategories(name) VALUES ('Mobile'), ('Laptop'), ('Desktop'),('Accessories');





