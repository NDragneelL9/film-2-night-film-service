CREATE USER f2n_admin with encrypted password 'film2night';

SET ROLE f2n_admin;

CREATE TYPE "film_type" AS ENUM (
  'FILM',
  'VIDEO',
  'TV_SERIES',
  'MINI_SERIES',
  'TV_SHOW'
);

CREATE TABLE "films" (
  "id" int PRIMARY KEY,
  "kinopoiskId" int NOT NULL UNIQUE,
  "imbdId" int NOT NULL UNIQUE,
  "nameRU" varchar,
  "nameEn" varchar,
  "nameOriginal" varchar NOT NULL,
  "reviewsCount" int,
  "ratingKinopoisk" real,
  "ratingKinopoiskVoteCount" int,
  "ratingImdb" real,
  "ratingImdbVoteCount" int,
  "webUrl" varchar,
  "year" int NOT NULL,
  "filmLength" int NOT NULL,
  "description" varchar,
  "type" film_type NOT NULL,
  "ratingMpaa" varchar,
  "ratingAgeLimits" varchar,
  "hasImax" bool,
  "has3D" bool,
  "lastSync" varchar
);

CREATE TABLE "countries" (
  "id" int PRIMARY KEY,
  "country" varchar NOT NULL UNIQUE
);

CREATE TABLE "genres" (
  "id" int PRIMARY KEY,
  "genre" varchar NOT NULL UNIQUE
);

CREATE TABLE "films_genres" (
  "film_id" int,
  "genre_id" int,
  CONSTRAINT films_genres_pkey PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE "films_countries" (
  "film_id" int,
  "country_id" int,
  CONSTRAINT films_countries_pkey PRIMARY KEY (film_id, country_id)
);

ALTER TABLE "films_genres" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "films_genres" ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("id");

ALTER TABLE "films_countries" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "films_countries" ADD FOREIGN KEY ("country_id") REFERENCES "countries" ("id");
