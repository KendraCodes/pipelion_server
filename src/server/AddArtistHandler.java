package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Kendra on 10/30/2018.
 */
public class AddArtistHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //TODO take in an artistName, artistID and add to database
        // URL = artists/add
        // "artistName", "artistID"
    }
}
