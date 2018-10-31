package database;

import model.Artist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kendra on 10/30/2018.
 */
public class ArtistsDAO {

    public String getArtistName(String artistID) {
        //todo
        return artistID;
    }

    public void addArtist(Artist a) {
        //todo for watching
    }

    public void addArtist(Collection<Artist> artists) {
        //todo for watching
    }

    public void setWatching(String artistID, String assetID, boolean isWatching) {
        //todo for watching
    }

    public List<String> getWatchedAssets(String artistID) {
        //todo for watching
        return new ArrayList<>();
    }

}
