package es.upm.miw.bantumi.data.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.upm.miw.bantumi.data.dao.GameStateDao;
import es.upm.miw.bantumi.data.dao.ScoreDao;
import es.upm.miw.bantumi.data.entities.GameState;
import es.upm.miw.bantumi.data.entities.Score;

@Database(entities = {Score.class, GameState.class}, version = 1)
public  abstract class  AppDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();
    public abstract GameStateDao gameStateDao();
}
