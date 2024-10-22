package es.upm.miw.bantumi.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.upm.miw.bantumi.data.entities.GameState;

@Dao
public interface GameStateDao {
    @Insert
    void guardarEstado(GameState estadoJuego);

    @Query("SELECT * FROM game_state ORDER BY id DESC LIMIT 1")
    GameState obtenerUltimoEstado();
}
