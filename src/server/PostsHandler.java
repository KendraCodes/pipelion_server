package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.PostsDAO;
import model.JsonUtils;
import model.Post;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<Post> posts = new PostsDAO().getAllPosts();
        byte[] data = JsonUtils.prettyPrintJson(posts).getBytes();
        PipelionServer.sendResponse(exchange, 200, data);
//        getPosts, sorted, filtered by:
//        - asset (asset1 OR asset2)
//        AND
//                - people (artist1 OR artist2)
//        AND
//                - department (department1 OR department2)
//        and give me an index and how many you want, or an ID and how many I want
        // body { index: 4 or -1, id: "idThing" or null, howMany: 5,
        //  departmentFilters = [ "one" ], artistFilters = [ "two" ], assetFilters = [ "three" ]
    }
}
