CREATE TYPE film_type AS ENUM (
  'FILM',
  'VIDEO',
  'TV_SERIES',
  'MINI_SERIES',
  'TV_SHOW'
);
CREATE TABLE films (
  film_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "kinopoiskId" int NOT NULL UNIQUE,
  "imdbId" varchar UNIQUE,
  "nameRu" varchar,
  "nameEn" varchar,
  "nameOriginal" varchar,
  "posterUrl" varchar,
  "reviewsCount" int,
  "ratingKinopoisk" varchar,
  "ratingKinopoiskVoteCount" int,
  "ratingImdb" varchar,
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
  "lastSync" timestamp,
  "banned" bool
);
CREATE TABLE countries (
  country_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "country" varchar NOT NULL UNIQUE
);
CREATE TABLE genres (
  genre_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "genre" varchar NOT NULL UNIQUE
);
CREATE TABLE films_genres (
  film_id int REFERENCES films (film_id) ON DELETE CASCADE,
  genre_id int REFERENCES genres (genre_id) ON DELETE CASCADE,
  CONSTRAINT films_genres_pkey PRIMARY KEY (film_id, genre_id)
);
CREATE TABLE films_countries (
  film_id int REFERENCES films (film_id) ON DELETE CASCADE,
  country_id int REFERENCES countries (country_id) ON DELETE CASCADE,
  CONSTRAINT films_countries_pkey PRIMARY KEY (film_id, country_id)
);