package es.upm.miw.bantumi.data.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "partidas")
public class Partida {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombreJugador;
    public Date fechaHora;
    public int semillasAlmacenJ1;
    public int semillasAlmacenJ2;

    public Partida(String nombreJugador, Date fechaHora, int semillasAlmacenJ1, int semillasAlmacenJ2) {
        this.nombreJugador = nombreJugador;
        this.fechaHora = fechaHora;
        this.semillasAlmacenJ1 = semillasAlmacenJ1;
        this.semillasAlmacenJ2 = semillasAlmacenJ2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getSemillasAlmacenJ1() {
        return semillasAlmacenJ1;
    }

    public void setSemillasAlmacenJ1(int semillasAlmacenJ1) {
        this.semillasAlmacenJ1 = semillasAlmacenJ1;
    }

    public int getSemillasAlmacenJ2() {
        return semillasAlmacenJ2;
    }

    public void setSemillasAlmacenJ2(int semillasAlmacenJ2) {
        this.semillasAlmacenJ2 = semillasAlmacenJ2;
    }
}
