package es.upm.miw.bantumi.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.upm.miw.bantumi.data.entities.GameState;

@Dao
public interface GameStateDao {
    @Insert
    void saveGameState(GameState gameState);

    @Query("SELECT * FROM game_state LIMIT 1")
    GameState getGameState();

    @Query("DELETE FROM game_state")
    void deleteGameState();
}
