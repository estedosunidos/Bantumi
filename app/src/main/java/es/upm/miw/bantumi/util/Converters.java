package es.upm.miw.bantumi.util;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static String fromIntArray(int[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i).append(",");
        }
        return sb.toString();
    }

    @TypeConverter
    public static int[] toIntArray(String data) {
        if (data == null) {
            return null;
        }
        String[] stringArray = data.split(",");
        int[] result = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            result[i] = Integer.parseInt(stringArray[i]);
        }
        return result;
    }
}
