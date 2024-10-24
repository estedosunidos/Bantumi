package es.upm.miw.bantumi.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.upm.miw.bantumi.data.entities.Partida;

@Dao
public interface partidoDao {
    @Insert
    void insertarPartida(Partida partida);

    @Query("SELECT * FROM partidas ORDER BY fecha DESC")
    List<Partida> obtenerTodasLasPartidas();

    @Query("DELETE FROM partidas")
    void borrarTodasLasPartidas();
}
