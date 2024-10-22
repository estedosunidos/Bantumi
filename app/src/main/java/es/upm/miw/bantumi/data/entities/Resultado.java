package es.upm.miw.bantumi.data.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "resultado")
public class Resultado {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombreJugador; // Nombre del jugador
    private String fecha; // Fecha y hora de la partida
    private int[] semillas; // Array que guarda el número de semillas en cada almacén

    public Resultado(String nombreJugador, String fecha, int[] semillas) {
        this.nombreJugador = nombreJugador;
        this.fecha = fecha;
        this.semillas = semillas;
    }

    // Getters y Setters
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int[] getSemillas() {
        return semillas;
    }

    public void setSemillas(int[] semillas) {
        this.semillas = semillas;
    }
}
