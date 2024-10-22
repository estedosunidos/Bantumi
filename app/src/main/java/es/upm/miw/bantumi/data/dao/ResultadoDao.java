package es.upm.miw.bantumi.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import es.upm.miw.bantumi.data.entities.Resultado;

@Dao
public interface ResultadoDao {
    @Insert
    void guardarResultado(Resultado resultado);

    @Query("SELECT * FROM resultado ORDER BY id DESC LIMIT 10")
    List<Resultado> obtenerMejoresResultados();

    @Query("DELETE FROM resultado")
    void eliminarResultados();
}
