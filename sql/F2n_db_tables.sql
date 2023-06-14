CREATE USER f2n_admin with encrypted password 'film2night';

SET ROLE f2n_admin;

CREATE TYPE film_type AS ENUM (
  'FILM',
  'VIDEO',
  'TV_SERIES',
  'MINI_SERIES',
  'TV_SHOW'
);

CREATE TABLE films (
  "id" SERIAL PRIMARY KEY,
  "kinopoiskId" int NOT NULL UNIQUE,
  "imbdId" int UNIQUE,
  "nameRu" varchar,
  "nameEn" varchar,
  "nameOriginal" varchar,
  "reviewsCount" int,
  "ratingKinopoisk" varchar,
  "ratingKinopoiskVoteCount" int,
  "ratingImdb" real,
  "ratingImdbVoteCount" int,
  "webUrl" varchar,
  "year" int,
  "filmLength" int,
  "description" varchar,
  "type" film_type,
  "ratingMpaa" varchar,
  "ratingAgeLimits" varchar,
  "hasImax" bool,
  "has3D" bool,
  "lastSync" varchar
);

CREATE TABLE countries (
  "id" SERIAL PRIMARY KEY,
  "country" varchar NOT NULL UNIQUE
);

CREATE TABLE genres (
  "id" SERIAL PRIMARY KEY,
  "genre" varchar NOT NULL UNIQUE
);

CREATE TABLE films_genres (
  "film_id" int,
  "genre_id" int,
  CONSTRAINT films_genres_pkey PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE films_countries (
  "film_id" int,
  "country_id" int,
  CONSTRAINT films_countries_pkey PRIMARY KEY (film_id, country_id)
);

ALTER TABLE films_genres ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE films_genres ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("id");

ALTER TABLE films_countries ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE films_countries ADD FOREIGN KEY ("country_id") REFERENCES "countries" ("id");
