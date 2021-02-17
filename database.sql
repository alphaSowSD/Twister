DROP DATABASE IF EXISTS sow_pidery;

CREATE DATABASE sow_pidery;

USE sow_pidery;

CREATE TABLE user(
	id INTEGER PRIMARY KEY auto_increment,
	login VARCHAR (255) UNIQUE,
	nom VARCHAR (255),
	prenom VARCHAR (255),
	pwd BLOB
);

CREATE TABLE new_session(
	idSession INTEGER PRIMARY KEY auto_increment,
	idUser INTEGER,
	cle VARCHAR (255),
	dateConnexion TIMESTAMP,
	isRoot BOOLEAN
);

CREATE TABLE friends(
	idUser INTEGER,
	friend INTEGER, 
	ntimestamp TIMESTAMP,
	PRIMARY KEY (idUser, friend)
);





