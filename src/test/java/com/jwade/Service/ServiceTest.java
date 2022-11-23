package com.jwade.Service;

import com.jwade.Data.GameDao;
import com.jwade.Data.RoundDao;
import com.jwade.Models.Game;
import com.jwade.Models.Round;
import com.jwade.TestApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class ServiceTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    Service service;

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
    void newGame() {
        Game game = service.newGame();
        assertFalse(game.getIsFinished());
        assertEquals(4, game.getAnswer().length());
    }

    @Test
    void getGames() {
        Game game = service.newGame();
        game = service.getGames(game);
        assertEquals("****", game.getAnswer());
    }

    @Test
    void getAllGames() {
        Game game = service.newGame();
        game.setFinished(true);

        Game game2 = service.newGame();

        List<Game> games = new ArrayList<>();
        games.add(game);
        games.add(game2);

        service.getAllGames(games);

        assertEquals("****", game2.getAnswer());
        assertNotEquals("****", game.getAnswer());
    }

    @Test
    void guessNumber() {
        Game game = service.newGame();
        Round round = service.guessNumber(game, "1234", gameDao);
        assertEquals(round.getGame_id(), game.getGame_id());
    }

    @Test
    void guessNumberUpdateGameStatus() {
        Game game = service.newGame();
        game.setFinished(true);
        Round round = service.guessNumber(game, "1234", gameDao);
        assertEquals(round.getGame_id(), game.getGame_id());
        assertTrue(game.getIsFinished());
    }

    @Test
    void checkGuessLessThan4Chars() {
        Game game = service.newGame();
        Round round = service.checkGuess(game, "124");
        assertEquals("e:0:p:0", round.getGuess_result());
    }

    @Test
    void checkGuessGreaterThan4Chars() {
        Game game = service.newGame();
        Round round = service.checkGuess(game, "124234");
        assertEquals("e:0:p:0", round.getGuess_result());
    }

    @Test
    void checkGuess3ExactCorrect(){
        Game game = service.newGame();
        game.setAnswer("0234");
        Round round = service.checkGuess(game, "1234");
        assertEquals("e:3:p:0", round.getGuess_result());
    }

    @Test
    void checkGuess4PartialCorrect(){
        Game game = service.newGame();
        game.setAnswer("2341");
        Round round = service.checkGuess(game, "1234");
        assertEquals("e:0:p:4", round.getGuess_result());
    }

    @Test
    void setTimeStamp() {
        Game game = service.newGame();

        Round round = new Round();
        round.setGuess("1234");
        round.setGame_id(game.getGame_id());
        round.setGuess_result("e:1:p:0");
        service.setTimeStamp(round);

        assertNotNull(round.getGuess_time());

    }
}