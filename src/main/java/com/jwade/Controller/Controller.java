package com.jwade.Controller;

import com.jwade.Data.GameDao;
import com.jwade.Data.RoundDao;
import com.jwade.Models.Game;
import com.jwade.Models.Round;
import com.jwade.Service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final GameDao gameDao;
    private final RoundDao roundDao;

    private final Service service;

    public Controller(GameDao gameDao, RoundDao roundDao, Service service){
        this.gameDao = gameDao;
        this.roundDao = roundDao;
        this.service = service;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(){

        Game newGame = service.newGame();
        gameDao.addGame(newGame);
        return service.getGames(newGame);

    }

    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round guessNumber(@RequestBody Round body){
        Round newRound = service.guessNumber(gameDao.getGameById(body.getGame_id()), body.getGuess(), gameDao);
        roundDao.addRound(newRound);
        return newRound;

    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        List<Game> gamesList = gameDao.getAllGames();
        service.getAllGames(gamesList);
        return gamesList;

    }

    @GetMapping("/game/{game_id}")
    public Game getGameById(@PathVariable int game_id){
        Game game = gameDao.getGameById(game_id);
        return service.getGames(game);
    }

    @GetMapping("/rounds/{game_id}")
    public List<Round> getAllRoundsForGameId(@PathVariable int game_id){
        return roundDao.getAllRoundsOfGame(game_id);

    }

}
