package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Kendra on 10/18/2018.
 */
public class DartUtils {

    private static final String DART_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static ZonedDateTime convertFromDartDate(String timestamp) {
        //2018-07-09 20:19:04Z
        String updatedTimestamp = timestamp.substring(0, timestamp.length() - 1) + " UTC";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DART_FORMAT + " z");
        return ZonedDateTime.parse(updatedTimestamp, fmt);
    }

    public static String convertToDartDate(ZonedDateTime time) {
        //2018-07-09 20:19:04Z
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DART_FORMAT);
        String timestamp = fmt.format(time);
        return timestamp + "Z";
    }

}
