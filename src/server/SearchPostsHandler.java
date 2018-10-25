package server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.AssetsDAO;
import database.PostsDAO;
import model.JsonUtils;
import model.Post;
import model.PostFilters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kendra on 10/21/2018.
 */
public class SearchPostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // search - gives me a search term and a chunk
        // which things are people likely to search?
        // POSTS
        //   artist's name
        //   asset's name
        //   department
        //   slack message

        try {

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
