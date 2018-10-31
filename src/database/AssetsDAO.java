package database;

import model.Asset;
import model.JsonUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 * Created by Kendra on 10/18/2018.
 */
public class AssetsDAO {

    public String getAssetName(String assetID) {
        //todo
        return assetID;
    }

    public List<Asset> getAllAssets() {
        //SQL could probably do the start at ___ thing
        return JsonUtils.loadAssets();
    };

    public void updateTime(String assetID, String timestamp) {
        //todo
    }

    public void addAsset(Asset a) {
        //todo
    }

    private List<Asset> getFilteredAssets(String[] departmentFilters) {
        List<Asset> allAssets = JsonUtils.loadAssets();
        if (departmentFilters.length == 0) {
            return allAssets;
        }
        List<Asset> assets = new ArrayList<>();
        HashSet<String> filters = new HashSet<>();
        for (String f : departmentFilters)
            filters.add(f.toLowerCase());

        //if any of the things in asset's departments matches anything in filters, include
        for (Asset a : allAssets) {
            for (String dpt : a.getDepartments()) {
                if (filters.contains(dpt.toLowerCase())) {
                    assets.add(a);
                    break; //it matches already, stop so we don't get duplicates
                }
            }
        }
        Collections.sort(assets);
        return assets;
    }

    private List<Asset> getIdSublist(List<Asset> assets, int howMany, String startingID) {
        HashMap<String, Asset> assetsMap = new HashMap<>();
        for (Asset a : assets) {
            assetsMap.put(a.getId(), a);
        }
        int startIdx = assets.indexOf(assetsMap.get(startingID)) + 1;
        int endIdx = startIdx + howMany;
        endIdx = endIdx > assets.size() ? assets.size() : endIdx;
        return assets.subList(startIdx, endIdx);
    }

    private List<Asset> getSublist(List<Asset> assets, int howMany) {
        int endIdx = howMany;
        endIdx = endIdx > assets.size() ? assets.size() : endIdx;
        return assets.subList(0, endIdx);
    }

    public List<Asset> getAssets(int howMany, String[] departmentFilters) {
        return getSublist(getFilteredAssets(departmentFilters), howMany);
    }

    public List<Asset> getAssets(int howMany, String[] departmentFilters, String startingID) {
        return getIdSublist(getFilteredAssets(departmentFilters), howMany, startingID);
    }

    private List<Asset> searchAssets(List<Asset> original, String searchTerm) {
        HashSet<Asset> matchesSearch = new HashSet<>();
        String search = searchTerm.toLowerCase();
        for (Asset a : original) {
            if (a.getName().toLowerCase().contains(search)) {
                matchesSearch.add(a);
            }
        }
        List<Asset> matchingAssets = new ArrayList<>();
        matchingAssets.addAll(matchesSearch);
        return matchingAssets;
    }


    public List<Asset> searchAssets(String searchTerm, int howMany, String[] departmentFilters, String id) {
        List<Asset> assets = searchAssets(getFilteredAssets(departmentFilters), searchTerm);
        return getIdSublist(assets, howMany, id);
    }

    public List<Asset> searchAssets(String searchTerm, int howMany, String[] departmentFilters) {
        List<Asset> assets = searchAssets(getFilteredAssets(departmentFilters), searchTerm);
        return getSublist(assets, howMany);
    }
}
