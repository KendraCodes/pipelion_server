package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.AssetsDAO;
import model.Asset;
import model.JsonUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class AssetsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //DONE - return a json list of all assets regardless of input
        //DONE - sort the list of assets by last updated (will be a data member)
        //TODO - return the right number of them based on howMany
        //TODO - return starting at correct index
        //TODO - return starting with correct id
        //TODO - if someone says both id and index or neither return an error
        //TODO - make filters actually do something


//        getAssets, sorted, filtered by:
//        - department
//        and give me an index and how many you want, or an ID and how many I want
        // body { index: 4 or -1, id: "id_of_thing" or null, howMany: 5, departmentFilters = [ "one", "two" ] }
        List<Asset> assets = new AssetsDAO().getAllAssets();
        byte[] data = JsonUtils.prettyPrintJson(assets).getBytes();
        PipelionServer.sendResponse(exchange, 200, data);
    }
}
