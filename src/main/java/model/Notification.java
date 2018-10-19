package model;

import java.time.ZonedDateTime;

/**
 * Created by Kendra on 10/18/2018.
 */
public class Notification implements Comparable<Notification> {
    String id;
    String artistID;
    String postID;
    String message;
    String timestamp;
    boolean isSeen;

    public ZonedDateTime getTimestamp() {
        return DartUtils.convertFromDartDate(timestamp);
    }

    @Override
    public int compareTo(Notification n) {
        return getTimestamp().compareTo(n.getTimestamp());
    }
}
