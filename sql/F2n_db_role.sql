SET ROLE postgres;
DROP DATABASE IF EXISTS film2night;
DROP USER IF EXISTS f2n_admin;

CREATE USER f2n_admin with encrypted password 'film2night';

CREATE DATABASE film2night
  WITH 
  OWNER = f2n_admin
  ENCODING = 'UTF8';

SET ROLE f2n_admin;
