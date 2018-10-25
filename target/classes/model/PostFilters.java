package model;

import java.util.HashSet;

/**
 * Created by Kendra on 10/19/2018.
 */
public class PostFilters {
    public String[] artists = {};
    public String[] assets = {};
    public String[] departments = {};


    public boolean postMatches(Post p) {
        //make sets for each type of filters for easier searching
        HashSet<String> artistFilters = new HashSet<>();
        for (String s : artists) artistFilters.add(s.toLowerCase());
        HashSet<String> assetFilters = new HashSet<>();
        for (String s : assets) assetFilters.add(s.toLowerCase());
        HashSet<String> departmentFilters = new HashSet<>();
        for (String s : departments) departmentFilters.add(s.toLowerCase());


        //each attribute must match at least one thing in each filter
        //if the set of filters is empty, default to always matching
        if (artistFilters.size() > 0 && !artistFilters.contains(p.getArtistID().toLowerCase()))
            return false;
        if (assetFilters.size() > 0 && !assetFilters.contains(p.getAssetID().toLowerCase()))
            return false;
        if (departmentFilters.size() > 0 && !departmentFilters.contains(p.getDepartment().toLowerCase()))
            return false;

        return true;
    }
}
