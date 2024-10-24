package es.upm.miw.bantumi.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "partidas")
public class Partida {
    @PrimaryKey(autoGenerate = true)
    private long id;  // Identificador único
    private Date fecha; // Fecha de la partida
    private int puntuacionJ1; // Puntuación del Jugador 1
    private int puntuacionJ2; // Puntuación del Jugador 2

    // Constructor
    public Partida(Date fecha, int puntuacionJ1, int puntuacionJ2) {
        this.fecha = fecha;
        this.puntuacionJ1 = puntuacionJ1;
        this.puntuacionJ2 = puntuacionJ2;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPuntuacionJ1() {
        return puntuacionJ1;
    }

    public void setPuntuacionJ1(int puntuacionJ1) {
        this.puntuacionJ1 = puntuacionJ1;
    }

    public int getPuntuacionJ2() {
        return puntuacionJ2;
    }

    public void setPuntuacionJ2(int puntuacionJ2) {
        this.puntuacionJ2 = puntuacionJ2;
    }
}
