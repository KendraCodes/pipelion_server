package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Kendra on 10/18/2018.
 */
public class SearchHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PipelionServer.sendResponse(exchange, 200, "this is the search handler");
    }
}
