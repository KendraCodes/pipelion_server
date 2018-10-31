package model;

import java.time.ZonedDateTime;

/**
 * Created by Kendra on 10/18/2018.
 */
public class Notification extends SortableByTime {
    String id;
    String artistID;
    String postID;
    String message;
    boolean isSeen;

    public String getId() {
        return id;
    }

    public String getArtistID() {
        return artistID;
    }

    public String getPostID() {
        return postID;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSeen() {
        return isSeen;
    }
}
