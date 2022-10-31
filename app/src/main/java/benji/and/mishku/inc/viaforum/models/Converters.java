package benji.and.mishku.inc.viaforum.models;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.time.Instant;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Instant instantFromTimestamp(Long value){
        return value==null ? null : Instant.ofEpochSecond(value);
    }

    @TypeConverter

    public static Long instantToTimestamp(Instant value){
        return value==null ? null : value.getEpochSecond();
    }
}
