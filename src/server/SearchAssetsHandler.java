package server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.AssetsDAO;
import model.Asset;
import model.JsonUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Kendra on 10/21/2018.
 */
public class SearchAssetsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //        search - gives me a search term and a chunk OPTIONAL artist: id to give only artists
//                - matching artists, total matches
//                - matching assets, total matches
//                - matching posts

//        which things are people likely to search?
//        ASSETS
//          name
//          department


        try {
            JsonObject reqBody = PipelionServer.getRequestBody(exchange);
            int howMany = PipelionServer.getChunkValue(reqBody);
            String[] departments = PipelionServer.getStringArray(reqBody, "departmentFilters");
            String id = PipelionServer.getString(reqBody, "id");
            String searchTerm = PipelionServer.getString(reqBody, "searchTerm");

            List<Asset> assets = null;


            if (id != null) {
                if (searchTerm != null) {
                    assets = new AssetsDAO().searchAssets(searchTerm, howMany, departments, id);
                } else {
                    assets = new AssetsDAO().getAssets(howMany, departments, id);
                }
            } else {
                if (searchTerm != null) {
                    assets = new AssetsDAO().searchAssets(searchTerm, howMany, departments);
                } else {
                    assets = new AssetsDAO().getAssets(howMany, departments);
                }
            }
            PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(assets));

        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }


    }
}
