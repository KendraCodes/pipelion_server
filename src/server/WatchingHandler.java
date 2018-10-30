package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Kendra on 10/30/2018.
 */
public class WatchingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //TODO artists/watching/set takes in { "artistID", "assetID", "watching": true }
        //TODO artists/watching/[artistID] returns a list of the assetIDs the artist watches

        //if I publish to Grendel, I watch him by default

    }
}
