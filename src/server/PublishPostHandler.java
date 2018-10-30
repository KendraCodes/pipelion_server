package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Kendra on 10/30/2018.
 */
public class PublishPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //todo call postsDAO and publish a thing and return success or failure
    }
}
