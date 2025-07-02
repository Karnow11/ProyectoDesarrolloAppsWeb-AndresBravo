-- Active: 1747187032312@@127.0.0.1@3306@tarea2
-- Crear Base de datos
CREATE DATABASE IF NOT EXISTS tarea2 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Darle permisos al usuario
GRANT ALL ON tarea2.* TO dbadmin@localhost;
GRANT ALL ON tarea2.* TO cc5002@localhost;
USE `tarea2` ;
-- Crear Tabla de actividades
CREATE TABLE actividad(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  celular VARCHAR(255) NOT NULL,
  inicio DATETIME NOT NULL,
  fin DATETIME NOT NULL,
  descripcion VARCHAR(255) NOT NULL,
  region VARCHAR(255) NOT NULL,
  comuna VARCHAR(255) NOT NULL,
  sector VARCHAR(255) NOT NULL
);

-- Crear Tabla de temas
CREATE TABLE actividad__tema(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  tema VARCHAR(255) NOT NULL
);

-- Crear Tabla de contactos
CREATE TABLE contactar_por(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(255) NOT NULL,
  contacto VARCHAR(255) NOT NULL
);

-- Crear Tabla de archivos
--Guarda la ruta del archivo
CREATE TABLE archivo(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  imagen VARCHAR(255) NOT NULL
);
