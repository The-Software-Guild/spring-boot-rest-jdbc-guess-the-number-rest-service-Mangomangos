package com.jwade.Data;

import com.jwade.Models.Game;
import com.jwade.Models.Round;
import com.jwade.Service.Service;
import com.jwade.TestApplicationConfiguration;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GameDaoDBTest extends TestCase {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    Service service;

    public GameDaoDBTest (){
    }

    @BeforeEach
    public void setUp(){
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round : rounds){
            roundDao.deleteRoundById(round.getRound_id());
        }

        List<Game> games = gameDao.getAllGames();
        for(Game game : games){
            gameDao.deleteGameById(game.getGame_id());
        }
    }

    @Test
    void addAndGetGame() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGame_id());
        assertEquals(game, fromDao);
    }

    @Test
    void getAllGames() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Game game2 = service.newGame();
        gameDao.addGame(game2);

        Game game3 = service.newGame();
        gameDao.addGame(game3);

        List<Game> games = gameDao.getAllGames();

        assertEquals(3, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
        assertTrue(games.contains(game3));
    }


    @Test
    void updateGame() {
        Game game = service.newGame();
        gameDao.addGame(game);
        game.setFinished(true);
        gameDao.updateGame(game);
        Game updated = gameDao.getGameById(game.getGame_id());
        assertTrue(updated.getIsFinished());
    }

    @Test
    void deleteGameById() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Round round = new Round();
        round.setGame_id(game.getGame_id());
        service.setTimeStamp(round);
        round.setGuess("1234");
        round.setGuess_result("e:2:p:1");
        roundDao.addRound(round);

        gameDao.deleteGameById(game.getGame_id());

        Game fromDao = gameDao.getGameById(game.getGame_id());
        assertNull(fromDao);

    }
}