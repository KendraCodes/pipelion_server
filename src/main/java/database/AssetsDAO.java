package database;

import model.Asset;
import model.JsonUtils;

import java.util.List;


/**
 * Created by Kendra on 10/18/2018.
 */
public class AssetsDAO {

    public List<Asset> getAllAssets() {
        //SQL could probably do the start at ___ thing
        return JsonUtils.loadAssets();
    };

}
