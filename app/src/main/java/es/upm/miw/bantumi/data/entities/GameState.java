package es.upm.miw.bantumi.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_state")
public class GameState {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String estadoSemillas; // Cadena que almacena el estado de las semillas
    private String turnoActual; // Turno actual en formato de cadena

    // Constructor, getters y setters
    public GameState(String estadoSemillas, String turnoActual) {
        this.estadoSemillas = estadoSemillas;
        this.turnoActual = turnoActual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstadoSemillas() {
        return estadoSemillas;
    }

    public void setEstadoSemillas(String estadoSemillas) {
        this.estadoSemillas = estadoSemillas;
    }

    public String getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }
}