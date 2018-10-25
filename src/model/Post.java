package model;

import java.time.ZonedDateTime;

/**
 * Created by Kendra on 10/18/2018.
 */
public class Post extends SortableByTime {
    String id;
    String artistID;
    String artistName;
    String assetID;
    String assetName;
    ContentAPI contentAPI;
    String contentID;
    String department;
    String slackLink;
    String slackMessage;

    public String getId() {
        return id;
    }

    public String getArtistID() {
        return artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAssetID() {
        return assetID;
    }

    public String getAssetName() {
        return assetName;
    }

    public ContentAPI getContentAPI() {
        return contentAPI;
    }

    public String getContentID() {
        return contentID;
    }

    public String getDepartment() {
        return department;
    }

    public String getSlackLink() {
        return slackLink;
    }

    public String getSlackMessage() {
        return slackMessage;
    }

}
