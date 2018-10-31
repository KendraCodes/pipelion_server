package model;

import java.time.ZonedDateTime;

/**
 * Created by Kendra on 10/19/2018.
 */
public class SortableByTime implements Comparable<SortableByTime> {
    String timestamp;


    public String getTimestampString() {
        return timestamp;
    }

    public ZonedDateTime getTimestamp() {
        return DartUtils.convertFromDartDate(timestamp);
    }

    @Override
    public int compareTo(SortableByTime o) {
        return o.getTimestamp().compareTo(getTimestamp());
    }
}
