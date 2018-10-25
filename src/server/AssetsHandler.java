package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import database.AssetsDAO;
import model.Asset;
import model.JsonUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;


/**
 * Created by Kendra on 10/18/2018.
 * returns a list of 10 assets by default
 *  - filtered by department
 *  - starting after a certain assetID
 *  - set the number returned with "howMany"
 *  - searchable by name
 */
public class AssetsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            JsonObject reqBody = PipelionServer.getRequestBody(exchange);

            int howMany = PipelionServer.getChunkValue(reqBody);
            String[] departments = PipelionServer.getStringArray(reqBody, "departmentFilters");
            String id = PipelionServer.getString(reqBody, "id");
            String searchTerm = PipelionServer.getString(reqBody, "searchTerm");

            List<Asset> assets;
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
            Collections.sort(assets);
            PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(assets));
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }
    }
}
