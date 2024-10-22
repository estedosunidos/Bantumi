package es.upm.miw.bantumi.dominio.logica;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

import es.upm.miw.bantumi.data.dao.GameStateDao;
import es.upm.miw.bantumi.data.dao.ResultadoDao;
import es.upm.miw.bantumi.data.entities.GameState;
import es.upm.miw.bantumi.data.entities.Resultado;
import es.upm.miw.bantumi.util.Converters;

@Database(entities = {Resultado.class, GameState.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ResultadoDao resultadoDao();
    public abstract GameStateDao estadoJuegoDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "bantumi_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
