package es.upm.miw.bantumi.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_state")
public class GameState {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int[] playerField;  // Las 6 casillas del jugador
    private int[] opponentField; // Las 6 casillas del oponente
    private int playerStore;
    private int opponentStore;
    private boolean isPlayerTurn;

    public GameState(int[] playerField, int[] opponentField, int playerStore, int opponentStore, boolean isPlayerTurn) {
        this.playerField = playerField;
        this.opponentField = opponentField;
        this.playerStore = playerStore;
        this.opponentStore = opponentStore;
        this.isPlayerTurn = isPlayerTurn;
    }

    // Getters y Setters
    // ...
}
