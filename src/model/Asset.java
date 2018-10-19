package model;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class Asset implements Comparable<Asset> {
    String id;
    String thumbnail;
    String name;
    List<String> postIDs;
    List<String> departments;
    String timestamp;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public List<String> getPostIDs() {
        return postIDs;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public ZonedDateTime getTimestamp() {
        return DartUtils.convertFromDartDate(timestamp);
    }

    @Override
    public int compareTo(Asset a) {
        return getTimestamp().compareTo(a.getTimestamp());
    }
}
