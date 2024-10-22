package es.upm.miw.bantumi.data.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
@Entity(tableName = "scores")

public class Score {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String playerName;
    private Date date;
    private int playerSeeds;
    private int opponentSeeds;

    public Score(String playerName, Date date, int playerSeeds, int opponentSeeds) {
        this.playerName = playerName;
        this.date = date;
        this.playerSeeds = playerSeeds;
        this.opponentSeeds = opponentSeeds;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getPlayerSeeds() { return playerSeeds; }
    public void setPlayerSeeds(int playerSeeds) { this.playerSeeds = playerSeeds; }

    public int getOpponentSeeds() { return opponentSeeds; }
    public void setOpponentSeeds(int opponentSeeds) { this.opponentSeeds = opponentSeeds; }
}
