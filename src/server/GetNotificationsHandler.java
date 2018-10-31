package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Kendra on 10/30/2018.
 */
public class GetNotificationsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // return a chunked list of notifications for the artist
        //chunking works just like posts or assets
        // URL = /notifications/get/[artistID]
    }
}
