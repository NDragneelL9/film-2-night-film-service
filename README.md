# Film 2 Night (film service)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/NDragneelL9/the-undermine-bot/blob/main/LICENSE)
[![Maintainability](https://api.codeclimate.com/v1/badges/e1fbf05b623beac6820f/maintainability)](https://codeclimate.com/github/NDragneelL9/film-2-night-film-service/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/e1fbf05b623beac6820f/test_coverage)](https://codeclimate.com/github/NDragneelL9/film-2-night-film-service/test_coverage)  <br>
**Author**: [Tim](https://github.com/NDragneelL9) <br>
# About application
**F2N film service** is created as a micro-service to main F2N Application. (Both of those are created with educational purposes). Main feature of the app is an API to work around films data from [Unofficial Kinopoisk API](https://kinopoiskapiunofficial.tech/)

# Technical Stack
The project is developing with pure [JAVA](https://www.java.com/en/) code using [HTTP Servlets](https://docs.oracle.com/cd/E13222_01/wls/docs81/servlet/overview.html) to build API, [PostreSQL](https://www.postgresql.org/) as database and [Maven](https://maven.apache.org/) as a building tool. As well everything runs inside [Docker](https://www.docker.com/) containers.

# Getting started
1. Clone repository 
```
git clone https://github.com/NDragneelL9/film-2-night-film-service.git
```
2. Change directory to `film-2-night-film-service`. Add `.env` file based on `.env.example` and build application.
```
cd film-2-night-film-service
# skipping tests because we have no docker containers running yet
mvn clean package -DskipTests 
```
3. Run docker container with docker-compose in detached mode (*make sure all needed ports are free*):
```
docker-compose up -d
```
4. To run tests:
```
mvn test
```
5. To apply changes:
```
# to apply servlet changes rebuild war file
mvn package
docker-compose restart
```
# Contributing
Just fork the repository from the develop branch, follow [Getting started](#getting-started) section, implement changes you want to propose and make a pull request. Also, there are issues in repository, feel free to submit a new one or participate in existing.