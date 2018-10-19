package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PipelionServer {

    private static HttpServer server;

    private static void initContexts() {

//        getAssets, sorted, filtered by:
//        - department
//        and give me an index and how many you want, or an ID and how many I want
        // body { index: 4 or -1, id: "id_of_thing" or null, howMany: 5, departmentFilters = [ "one", "two" ] }
        server.createContext("/assets", new AssetsHandler());


//        getPosts, sorted, filtered by:
//        - asset (asset1 OR asset2)
//        AND
//                - people (artist1 OR artist2)
//        AND
//                - department (department1 OR department2)
//        and give me an index and how many you want, or an ID and how many I want
        // body { index: 4 or -1, id: "idThing" or null, howMany: 5,
        //  departmentFilters = [ "one" ], artistFilters = [ "two" ], assetFilters = [ "three" ]
        server.createContext("/posts", new PostsHandler());


        //getDepartments - static list. /get/departments, leave flexible to include other info
        server.createContext("/get/", new GetHandler());

//        search - gives me a search term and a chunk OPTIONAL artist: id to give only artists
//                - matching artists, total matches
//                - matching assets, total matches
//                - matching posts
        //body { term: "searchTerm", howMany: 5, id: "id of thing" or null }
        //search/[ assets | posts | artists ] body
        server.createContext("/search/", new SearchHandler());

        server.createContext("/", new DefaultHandler());

    }

    public static void main(String[] args) {
        int port = 8113;
        if (args.length == 1) { //allow for port number to be passed in
            port = Integer.parseInt(args[0]);
        }

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            initContexts();
            System.out.print("Contexts initialized\n");
            server.setExecutor(null);
            server.start();
            System.out.print("server.PipelionServer started");
        } catch (IOException e) {
            System.out.println("Failed to create server, try a different port");
        }
    }


    public static void sendResponse(HttpExchange exchange, int code, byte[] data) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        OutputStream respBody = exchange.getResponseBody();
        try {
            exchange.sendResponseHeaders(code, 0);
            respBody.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                respBody.close();
            } catch (IOException e) {
                e.printStackTrace(); //Java is stupid.
            }
        }
    }

    public static void sendResponse(HttpExchange exchange, int code, String msg) {
        sendResponse(exchange, code, msg.getBytes());
    }

}
