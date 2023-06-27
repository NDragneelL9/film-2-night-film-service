#!/bin/bash
set -e

psql -c "CREATE USER f2n_admin WITH PASSWORD 'film2night';"
psql -c "CREATE DATABASE film2night WITH OWNER f2n_admin;"
psql -c "CREATE DATABASE film2night_test WITH OWNER f2n_admin;"
psql -U f2n_admin -d film2night -a -f /home/F2n_db_tables.sql
psql -U f2n_admin -d film2night_test -a -f /home/F2n_db_tables.sql
