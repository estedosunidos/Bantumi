package es.upm.miw.bantumi.util;
import androidx.room.TypeConverter;
import java.util.Date;
public class DateConverter {
    @TypeConverter
    public static Long fromDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime(); // Convertir Date a Long
        }
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return new Date(timestamp); // Convertir Long a Date
        }
    }
}
