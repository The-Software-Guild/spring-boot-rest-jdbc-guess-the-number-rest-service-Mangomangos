package com.jwade.Data;

import com.jwade.Models.Game;
import com.jwade.Models.Round;
import com.jwade.Service.Service;
import com.jwade.TestApplicationConfiguration;
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
class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    Service service;

    public RoundDaoDBTest(){

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
    void addAndGetRound() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Round round = new Round();
        round.setGame_id(game.getGame_id());
        service.setTimeStamp(round);
        round.setGuess("1234");
        round.setGuess_result("e:2:p:1");
        roundDao.addRound(round);
        Round fromDao = roundDao.findRoundById(round.getRound_id());

        assertEquals(round, fromDao);
    }

    @Test
    void getAllRounds() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Game game2 = service.newGame();
        gameDao.addGame(game2);

        Round round = new Round();
        round.setGuess("1111");
        round.setGuess_result("e:2:p:1");
        service.setTimeStamp(round);
        round.setGame_id(game.getGame_id());

        Round round2 = new Round();
        round2.setGuess("2222");
        round2.setGuess_result("e:1:p:1");
        service.setTimeStamp(round2);
        round2.setGame_id(game2.getGame_id());

        round = roundDao.addRound(round);
        round2 = roundDao.addRound(round2);

        List<Round> rounds = roundDao.getAllRounds();
        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
    }

    @Test
    void getAllRoundsOfGame() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1111");
        round.setGuess_result("e:2:p:1");
        service.setTimeStamp(round);
        round.setGame_id(game.getGame_id());

        Round round2 = new Round();
        round2.setGuess("2222");
        round2.setGuess_result("e:1:p:1");
        service.setTimeStamp(round2);
        round2.setGame_id(game.getGame_id());

        Round round3 = new Round();
        round3.setGuess("3333");
        round3.setGuess_result("e:1:p:0");
        service.setTimeStamp(round3);
        round3.setGame_id(game.getGame_id());

        roundDao.addRound(round);
        roundDao.addRound(round2);
        roundDao.addRound(round3);

        List<Round> rounds = roundDao.getAllRoundsOfGame(game.getGame_id());
        assertEquals(3, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
        assertTrue(rounds.contains(round3));

    }


    @Test
    void updateRound() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1111");
        round.setGuess_result("e:2:p:1");
        service.setTimeStamp(round);
        round.setGame_id(game.getGame_id());

        roundDao.addRound(round);

        Round fromDao = roundDao.findRoundById(round.getRound_id());
        assertEquals(round.getRound_id(), fromDao.getRound_id());

        round.setGuess("2222");

        roundDao.updateRound(round);

        assertNotEquals(round.getGuess(), fromDao.getGuess());

        fromDao = roundDao.findRoundById(round.getRound_id());

        assertEquals(round.getGuess(), fromDao.getGuess());

    }

    @Test
    void deleteRoundById() {
        Game game = service.newGame();
        gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1111");
        round.setGuess_result("e:2:p:1");
        service.setTimeStamp(round);
        round.setGame_id(game.getGame_id());

        roundDao.addRound(round);

        roundDao.deleteRoundById(round.getRound_id());

        Round fromDao = roundDao.findRoundById(round.getRound_id());

        assertNull(fromDao);

    }
}