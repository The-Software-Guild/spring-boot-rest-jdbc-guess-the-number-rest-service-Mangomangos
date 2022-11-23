package com.jwade.Models;

import java.util.Objects;

public class Game {
    private int game_id;
    private String answer;
    private boolean isFinished;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        String game_id = String.valueOf(this.getGame_id());
        String answer = this.getAnswer();
        String isFinished = String.valueOf(this.getIsFinished());

        return String.format("Id: %s \nanswer: %s \nisFinished: %s", game_id, answer, isFinished);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getGame_id() == game.getGame_id() && getIsFinished() == game.getIsFinished() && Objects.equals(getAnswer(), game.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame_id(), getAnswer(), getIsFinished());
    }
}
