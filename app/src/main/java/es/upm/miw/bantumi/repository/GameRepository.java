package es.upm.miw.bantumi.repository;
import android.content.Context;
import androidx.room.Room;
import java.util.List;

import es.upm.miw.bantumi.data.dao.GameStateDao;
import es.upm.miw.bantumi.data.dao.ScoreDao;
import es.upm.miw.bantumi.data.database.AppDatabase;
import es.upm.miw.bantumi.data.entities.GameState;
import es.upm.miw.bantumi.data.entities.Score;

public class GameRepository {
    private final ScoreDao scoreDao;
    private final GameStateDao gameStateDao;
    private static GameRepository instance;

    private GameRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "bantumi_db").build();
        scoreDao = db.scoreDao();
        gameStateDao = db.gameStateDao();
    }

    public static synchronized GameRepository getInstance(Context context) {
        if (instance == null) {
            instance = new GameRepository(context);
        }
        return instance;
    }

    // Métodos para manejar los datos de puntuaciones
    public void insertScore(Score score) {
        new Thread(() -> scoreDao.insertScore(score)).start();
    }

    public List<Score> getTopScores() {
        return scoreDao.getTopScores();
    }

    public void deleteAllScores() {
        new Thread(() -> scoreDao.deleteAllScores()).start();
    }

    // Métodos para manejar el estado del juego
    public void saveGameState(GameState gameState) {
        new Thread(() -> gameStateDao.saveGameState(gameState)).start();
    }

    public GameState getGameState() {
        return gameStateDao.getGameState();
    }

    public void deleteGameState() {
        new Thread(() -> gameStateDao.deleteGameState()).start();
    }
}
