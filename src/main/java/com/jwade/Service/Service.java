package com.jwade.Service;

import com.jwade.Data.GameDao;
import com.jwade.Models.Game;
import com.jwade.Models.Round;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Repository
public class Service {

    public Game newGame(){
        Game game = new Game();
        Random rand = new Random();

        StringBuilder answer = new StringBuilder();

        for (int i=0; i<4; i++){
            int num = rand.nextInt(10);
            answer.append(num);
        }

        game.setAnswer(answer.toString());
        game.setFinished(false);
        return game;
    }

    public Game getGames(Game game){
        if (!game.getIsFinished()){
            game.setAnswer("****");
        }
        return game;
    }

    public void getAllGames(List<Game> games){
        for (Game game : games){
            if(!game.getIsFinished()){
                game.setAnswer("****");
            }
        }
    }

    public Round guessNumber(Game game, String guess, GameDao gameDao){
        Round round = checkGuess(game, guess);
        if (game.getIsFinished()){
            gameDao.updateGame(game);
        }
        setTimeStamp(round);
        round.setGame_id(game.getGame_id());
        return round;
    }

    public Round checkGuess(Game game, String guess){
        Round round = new Round();
        round.setGuess(guess);

        int partial = 0;
        int exact = 0;
        String answer = game.getAnswer();
        if(guess.equals(answer)){
            game.setFinished(true);
        }
        String resultsFormat = "e:%d:p:%d";

        if(guess.length() != 4){
            round.setGuess_result(String.format(resultsFormat, exact, partial));
            return round;
        }

        for(int i = 0; i <4; i++){
            String guessLetter = String.valueOf(guess.charAt(i));
            if(guess.charAt(i) == answer.charAt(i)){
                exact += 1;
            } else if (answer.contains(guessLetter)) {
                partial += 1;
            }
        }

        round.setGuess_result(String.format(resultsFormat, exact, partial));
        return round;
    }

    public void setTimeStamp(Round round){
        Calendar calendar = Calendar.getInstance();
        Timestamp guessTime = new Timestamp(calendar.getTime().getTime());
        round.setGuess_time(guessTime);
    }
}
