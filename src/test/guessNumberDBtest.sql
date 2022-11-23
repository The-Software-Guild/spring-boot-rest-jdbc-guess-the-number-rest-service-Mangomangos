DROP DATABASE IF EXISTS guessNumberDBTest;
CREATE DATABASE guessNumberDBTest;

USE guessNumberDBTest;

CREATE TABLE game(
game_id INT PRIMARY KEY AUTO_INCREMENT,
answer VARCHAR(4) NOT NULL,
isFinished BOOLEAN NOT NULL);

CREATE TABLE round(
round_id INT PRIMARY KEY AUTO_INCREMENT,
game_id INT NOT NULL,
guess_time TIMESTAMP NOT NULL,
guess VARCHAR(10) NOT NULL,
guess_result VARCHAR(10) NOT NULL,
FOREIGN KEY(game_id) REFERENCES game(game_id));