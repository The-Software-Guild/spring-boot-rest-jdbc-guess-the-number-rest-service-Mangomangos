package com.jwade.Data;

import com.jwade.Models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
@Profile("database")
public class GameDaoDB implements GameDao{

    private final JdbcTemplate jdbc;

    @Autowired
    public GameDaoDB(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private static final class GameMapper implements RowMapper<Game>{
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException{
            Game game = new Game();
            game.setGame_id(rs.getInt("game_id"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("isFinished"));
            return game;
        }
    }

    @Override
    public Game addGame(Game game) {

        final String sql = "INSERT INTO game(answer, isFinished) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.getIsFinished());
            return statement;

        }, keyHolder);

        game.setGame_id(keyHolder.getKey().intValue());

        return game;

        }

    @Override
    public List<Game> getAllGames() {
        try {
            final String SELECT_ALL_GAMES = "SELECT * FROM game";
            return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Game getGameById(int game_id) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE game_id = ?";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), game_id);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public boolean updateGame(Game game) {
        try {
            final String UPDATE_GAME = "UPDATE game SET answer = ?, isFinished = ?";
            jdbc.update(UPDATE_GAME,
                    game.getAnswer(),
                    game.getIsFinished());
            return true;
        } catch (DataAccessException ex){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteGameById(int game_id) {
        try {
            final String DELETE_ROUNDS_BY_GAMEID = "DELETE FROM round WHERE game_id = ?";
            jdbc.update(DELETE_ROUNDS_BY_GAMEID, game_id);

            final String DELETE_GAME_BY_ID = "DELETE FROM game WHERE game_id = ?";
            jdbc.update(DELETE_GAME_BY_ID, game_id);
            return true;
        } catch (DataAccessException ex){
            return false;
        }
    }
}
