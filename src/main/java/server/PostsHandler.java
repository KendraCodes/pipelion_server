package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.PostsDAO;
import model.JsonUtils;
import model.Post;
import model.PostFilters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        getPosts, sorted, filtered by:
//        - asset (asset1 OR asset2)
//        AND
//                - people (artist1 OR artist2)
//        AND
//                - department (department1 OR department2)
//        and give me an ID and how many you want
        // body { id: "idThing" or null, howMany: 5,
        //  departmentFilters = [ "one" ], artistFilters = [ "two" ], assetFilters = [ "three" ]

        try {
            JsonObject reqBody = new Gson().fromJson(new InputStreamReader(exchange.getRequestBody()), JsonObject.class);
            //lets code run with default values if no request body is given
            if (reqBody == null)
                reqBody = new JsonObject();

            int howMany = 10;
            if (reqBody.has("howMany")) {
                howMany = reqBody.get("howMany").getAsInt();
            }
            PostFilters filters = new PostFilters();
            if (reqBody.has("departmentFilters")) {
                filters.departments = new Gson().fromJson(reqBody.get("departmentFilters"), String[].class);
            }
            if (reqBody.has("artistFilters")) {
                filters.artists = new Gson().fromJson(reqBody.get("artistFilters"), String[].class);
            }
            if (reqBody.has("assetFilters")) {
                filters.assets = new Gson().fromJson(reqBody.get("assetFilters"), String[].class);
            }

            List<Post> posts;
            String id = reqBody.has("id") ? reqBody.get("id").getAsString() : null;
            if (id != null && id.length() > 0) {
                posts = new PostsDAO().getPosts(howMany, filters, id);
            } else {
                posts = new PostsDAO().getPosts(howMany, filters);
            }
            PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(posts));
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }
    }
}
