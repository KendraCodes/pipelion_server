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
import java.util.Collections;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        getPosts, sorted, searchable by artist or asset name, filtered by:
//        - asset (asset1 OR asset2)
//        AND
//                - people (artist1 OR artist2)
//        AND
//                - department (department1 OR department2)
//        and give me an ID and how many you want

        try {
            String[] pieces = exchange.getRequestURI().getPath().substring(1).split("/");
            if (pieces.length > 1) {
                Post p = new PostsDAO().getPostById(pieces[1]);
                PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(p));
                return;
            }

            JsonObject reqBody = PipelionServer.getRequestBody(exchange);
            int howMany = PipelionServer.getChunkValue(reqBody);
            String id = PipelionServer.getString(reqBody, "id");
            String searchTerm = PipelionServer.getString(reqBody, "searchTerm");

            PostFilters filters = new PostFilters();
            filters.departments = PipelionServer.getStringArray(reqBody, "departmentFilters");
            filters.artists = PipelionServer.getStringArray(reqBody, "artistFilters");
            filters.assets = PipelionServer.getStringArray(reqBody, "assetFilters");

            List<Post> posts = null;
            if (id != null) {
                if (searchTerm != null) {
                    posts = new PostsDAO().searchPosts(filters, howMany, searchTerm, id);
                } else {
                    posts = new PostsDAO().getPosts(howMany, filters, id);
                }
            } else {
                if (searchTerm != null) {
                    posts = new PostsDAO().searchPosts(filters, howMany, searchTerm);
                } else {
                    posts = new PostsDAO().getPosts(howMany, filters);
                }
            }
            Collections.sort(posts);
            PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(posts));
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }
    }
}
