CREATE DATABASE grabber;
\c grabber;

CREATE TABLE post (
	id serial primary key,
	name varchar (300),
	text text,
	link varchar (300) UNIQUE,
	created timestamp
);