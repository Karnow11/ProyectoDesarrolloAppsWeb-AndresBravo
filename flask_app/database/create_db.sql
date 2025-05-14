-- Active: 1747187032312@@127.0.0.1@3306@mysql
-- Crear Base de datos
CREATE DATABASE IF NOT EXISTS appsWeb_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Darle permisos al usuario
GRANT ALL ON appWeb_db.* TO dbadmin@localhost;
USE `appsWeb_db` ;
-- Crear Tabla de usuarios
CREATE TABLE actividad(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);

-- Crear Tabla de Confesiones
CREATE TABLE confesiones(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  conf_text VARCHAR(255) NOT NULL,
  conf_img VARCHAR(255) NOT NULL,
  user_id BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (user_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
