package server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.ArtistsDAO;
import model.Artist;
import model.JsonUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by Kendra on 10/30/2018.
 */
public class WatchingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //artists/watching/set takes in { "artistID", "assetID", "watching": true }
        //artists/watching/[artistID] returns a list of the assetIDs the artist watches

        //if I publish to Grendel, I watch him by default
        try {
            String pieces[] = exchange.getRequestURI().toString().substring(1).split("/");
            if (pieces.length >= 3) {
                if (pieces[2].equals("set")) {
                    JsonObject jsonObj = PipelionServer.getRequestBody(exchange);
                    String artistID = jsonObj.get("artistID").getAsString();
                    String assetID = jsonObj.get("assetID").getAsString();
                    boolean watching = jsonObj.get("watching").getAsBoolean();
                    new ArtistsDAO().setWatching(artistID, assetID, watching);
                    String msg = "Artist " + artistID + " is now " + (watching ? "" : "not") + " watching asset " + assetID;
                    PipelionServer.sendResponse(exchange, 200, msg);
                } else {
                    List<String> watching = new ArtistsDAO().getWatchedAssets(pieces[2]);
                    PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(watching));
                }
            } else {
                throw new MalformedURLException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }

    }
}
