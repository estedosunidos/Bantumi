package es.upm.miw.bantumi.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import es.upm.miw.bantumi.data.dao.GameStateDao;
import es.upm.miw.bantumi.data.dao.ResultadoDao;
import es.upm.miw.bantumi.data.dao.partidoDao;
import es.upm.miw.bantumi.data.entities.GameState;
import es.upm.miw.bantumi.data.entities.Partida;
import es.upm.miw.bantumi.util.Converters;


@Database(entities = {Partida.class}, version = 1)
@TypeConverters({Converters.class}) // Para convertir la fecha
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract GameStateDao gameStateDao();
    public abstract partidoDao partidaDao();
    // Add migrations
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
