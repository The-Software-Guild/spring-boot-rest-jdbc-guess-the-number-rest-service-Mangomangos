package com.jwade.Data;

import com.jwade.Models.Game;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("memory")
public class GameDaoInMemory implements GameDao{

    private static final List<Game> games = new ArrayList<>();


    @Override
    public Game addGame(Game game) {
        int nextId = games.stream()
                .mapToInt(Game::getGame_id)
                .max()
                .orElse(0)+1;

        game.setGame_id(nextId);
        games.add(game);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    @Override
    public Game getGameById(int id) {
        return games.stream()
                .filter(i -> i.getGame_id() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateGame(Game game) {
        int index = 0;
        while (index < games.size()
                && games.get(index).getGame_id() != game.getGame_id()){
            index++;
        }
        if (index < games.size()){
            games.set(index, game);
        }
        return index < games.size();
    }

    @Override
    public boolean deleteGameById(int id) {
        return games.removeIf(i -> i.getGame_id() == id);
    }
}
