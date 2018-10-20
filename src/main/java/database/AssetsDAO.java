package database;

import model.Asset;
import model.JsonUtils;

import java.util.*;


/**
 * Created by Kendra on 10/18/2018.
 */
public class AssetsDAO {

    public List<Asset> getAllAssets() {
        //SQL could probably do the start at ___ thing
        return JsonUtils.loadAssets();
    };

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

    public List<Asset> getAssets(int howMany, String[] departmentFilters) {
        List<Asset> assets = getFilteredAssets(departmentFilters);
        int endIdx = howMany;
        endIdx = endIdx > assets.size() ? assets.size() : endIdx;
        return assets.subList(0, endIdx);
    }

    public List<Asset> getAssets(int howMany, String[] departmentFilters, String startingID) {
        List<Asset> assets = getFilteredAssets(departmentFilters);
        HashMap<String, Asset> assetsMap = new HashMap<>();
        for (Asset a : assets) {
            assetsMap.put(a.getId(), a);
        }
        int startIdx = assets.indexOf(assetsMap.get(startingID)) + 1;
        int endIdx = startIdx + howMany;
        endIdx = endIdx > assets.size() ? assets.size() : endIdx;
        return assets.subList(startIdx, endIdx);
    }



}
