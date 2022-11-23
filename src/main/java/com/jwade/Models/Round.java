package com.jwade.Models;

import java.sql.Timestamp;
import java.util.Objects;

public class Round {
    private int round_id;
    int game_id;
    Timestamp guess_time;
    String guess;
    String guess_result;

    public int getRound_id() {
        return round_id;
    }

    public void setRound_id(int round_id) {
        this.round_id = round_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public Timestamp getGuess_time() {
        return guess_time;
    }

    public void setGuess_time(Timestamp guess_time) {
        this.guess_time = guess_time;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getGuess_result() {
        return guess_result;
    }

    public void setGuess_result(String guess_result) {
        this.guess_result = guess_result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return getRound_id() == round.getRound_id() && getGame_id() == round.getGame_id() && Objects.equals(getGuess(), round.getGuess()) && Objects.equals(getGuess_result(), round.getGuess_result());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRound_id(), getGame_id(), getGuess(), getGuess_result());
    }
}
