package com.jwade.Data;

import com.jwade.Models.Round;

import java.util.List;

public interface RoundDao {

    Round addRound(Round round);

    List<Round> getAllRounds();

    List<Round> getAllRoundsOfGame(int gameId);

    Round findRoundById(int id);

    boolean updateRound (Round round);

    boolean deleteRoundById(int id);
}
