package es.upm.miw.bantumi.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.upm.miw.bantumi.data.entities.Score;

@Dao
public interface ScoreDao {
    @Insert
    void insertScore(Score score);

    @Query("SELECT * FROM scores ORDER BY playerSeeds DESC LIMIT 10")
    List<Score> getTopScores();

    @Query("DELETE FROM scores")
    void deleteAllScores();
}