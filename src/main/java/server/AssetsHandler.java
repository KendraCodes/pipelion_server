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
import java.util.List;


/**
 * Created by Kendra on 10/18/2018.
 */
public class AssetsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // getAssets, sorted, filtered by department (OR all the options)
        // tell me how many you want, optional: which id to start with and department filters
        // body { id: "id_of_thing" or null, howMany: 5, departmentFilters = [ "one", "two" ] }

        try {
            JsonObject reqBody = new Gson().fromJson(new InputStreamReader(exchange.getRequestBody()), JsonObject.class);
            int howMany = 10;
            if (reqBody.has("howMany")) {
                howMany = reqBody.get("howMany").getAsInt();
            }
            String[] departments = {};
            if (reqBody.has("departmentFilters")) {
                departments = new Gson().fromJson(reqBody.get("departmentFilters"), String[].class);
            }

            List<Asset> assets;
            String id = reqBody.has("id") ? reqBody.get("id").getAsString() : null;
            if (id != null && id.length() > 0) {
                assets = new AssetsDAO().getAssets(howMany, departments, id);
            } else {
                assets = new AssetsDAO().getAssets(howMany, departments);
            }
            PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(assets));
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }
    }
}
