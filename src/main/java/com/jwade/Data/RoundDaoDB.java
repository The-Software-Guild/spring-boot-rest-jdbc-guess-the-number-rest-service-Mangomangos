package com.jwade.Data;

import com.jwade.Models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class RoundDaoDB implements RoundDao{

    private final JdbcTemplate jdbc;

    @Autowired
    public RoundDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public static final class RoundMapper implements RowMapper<Round>{
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException{
            Round round = new Round();
            round.setRound_id(rs.getInt("round_id"));
            round.setGame_id(rs.getInt("game_id"));
            round.setGuess_time(rs.getTimestamp("guess_time"));
            round.setGuess(rs.getString("guess"));
            round.setGuess_result(rs.getString("guess_result"));
            return round;
        }
    }

    @Override
    public Round addRound(Round round) {
        final String sql = "INSERT INTO round(game_id, guess_time, guess, guess_result)" +
                " VALUES(?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGame_id());
            statement.setTimestamp(2, round.getGuess_time());
            statement.setString(3, round.getGuess());
            statement.setString(4, round.getGuess_result());
            return statement;
        }, keyHolder);

        round.setRound_id(keyHolder.getKey().intValue());
        return round;

    }

    @Override
    public List<Round> getAllRounds() {
        try{
            final String SELECT_ALL_ROUNDS = "SELECT * FROM round";
            return jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
        } catch (DataAccessException ex){
            return null;
        }

    }


    @Override
    public List<Round> getAllRoundsOfGame(int game_id) {
        try{
            final String SELECT_ALL_ROUNDS_FOR_GAMEID = "SELECT * FROM round WHERE game_id = ? ORDER BY guess_time DESC";
            return jdbc.query(SELECT_ALL_ROUNDS_FOR_GAMEID, new RoundMapper(), game_id);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Round findRoundById(int round_id) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), round_id);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public boolean updateRound(Round round) {
        try {
            final String UPDATE_ROUND = "UPDATE round SET game_id = ?, guess_time = ?, guess = ?, guess_result = ?" +
                    " WHERE round_id = ?";
            jdbc.update(UPDATE_ROUND,
                    round.getGame_id(),
                    round.getGuess_time(),
                    round.getGuess(),
                    round.getGuess_result(),
                    round.getRound_id());
            return true;
        } catch (DataAccessException ex){
            return false;
        }
    }

    @Override
    public boolean deleteRoundById(int round_id) {
        try  {
            final String DELETE_ROUND_BY_ID = "DELETE FROM round WHERE round_id = ?";
            jdbc.update(DELETE_ROUND_BY_ID, round_id);
            return true;
        } catch (DataAccessException ex){
            return false;
        }
    }
}
