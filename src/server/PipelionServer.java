package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Kendra on 10/18/2018.
 */
public class PipelionServer {

    private static HttpServer server;

    private static void initContexts() {

        server.createContext("/assets/create", new CreateAssetHandler());
        server.createContext("/assets", new AssetsHandler());
        server.createContext("/posts/publish", new PublishPostHandler());
        server.createContext("/posts", new PostsHandler());

        server.createContext("/artists/add", new AddArtistHandler());
        server.createContext("/artists/watching", new WatchingHandler());

        server.createContext("/notifications/get", new GetNotificationsHandler());
        server.createContext("/notifications/update", new UpdateNotificationsHandler());



        //handle all other data requests not covered by assets or posts
        server.createContext("/get/", new GetHandler());

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                sendResponse(exchange, HTTP_OK, "Hunter wanted a funnier error message");
            }
        });

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
            System.out.println("server.PipelionServer started");
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

    public static JsonObject getRequestBody(HttpExchange exchange) {
        JsonObject reqBody = new Gson().fromJson(new InputStreamReader(exchange.getRequestBody()), JsonObject.class);
        //lets code run with default values if no request body is given
        if (reqBody == null)
            return new JsonObject();
        else
            return reqBody;
    }

    public static int getChunkValue(JsonObject jsonObj) {
        if (jsonObj.has("howMany")) {
            return jsonObj.get("howMany").getAsInt();
        } else {
            return 10;
        }
    }

    public static String[] getStringArray(JsonObject jsonObj, String name) {
        if (jsonObj.has(name)) {
            return new Gson().fromJson(jsonObj.get(name), String[].class);
        } else {
            String[] retVal = {};
            return retVal;
        }
    }

    public static String getString(JsonObject jsonObj, String str) {
        String id = null;
        if (jsonObj.has(str)) {
            id = jsonObj.get(str).getAsString();
            if (id.length() == 0)
                id = null;
        }
        return id;
    }

}
