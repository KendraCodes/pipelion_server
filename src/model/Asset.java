package model;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class Asset extends SortableByTime {
    String id;
    String thumbnail;
    String name;
    List<String> postIDs;
    List<String> departments;

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

}
