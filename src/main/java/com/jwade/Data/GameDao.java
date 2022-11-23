package com.jwade.Data;

import com.jwade.Models.Game;

import java.util.List;

public interface GameDao {

    Game addGame(Game game);

    List<Game> getAllGames();

    Game getGameById(int id);

    boolean updateGame(Game game);

    boolean deleteGameById(int id);
}
