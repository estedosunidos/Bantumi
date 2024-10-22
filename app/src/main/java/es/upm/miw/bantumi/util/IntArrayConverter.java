package es.upm.miw.bantumi.util;

import androidx.room.TypeConverter;
import java.util.Arrays;
public class IntArrayConverter {
    // Convierte int[] a String para guardar en la base de datos
    @TypeConverter
    public static String fromIntArray(int[] array) {
        if (array == null) {
            return null;
        }
        // Convertir el array a una cadena de texto separada por comas
        return Arrays.toString(array).replaceAll("[\\[\\] ]", "");
    }

    // Convierte una String a int[] para recuperar de la base de datos
    @TypeConverter
    public static int[] toIntArray(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        // Separar la cadena en enteros
        String[] strings = data.split(",");
        int[] array = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            array[i] = Integer.parseInt(strings[i]);
        }
        return array;
    }
}
