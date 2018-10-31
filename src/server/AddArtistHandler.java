package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.ArtistsDAO;
import model.Artist;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Kendra on 10/30/2018.
 */
public class AddArtistHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            JsonObject reqBody = PipelionServer.getRequestBody(exchange);
            Artist a = new Gson().fromJson(reqBody, Artist.class);
            new ArtistsDAO().addArtist(a);
            PipelionServer.sendResponse(exchange, 200, "Successfully added artist");
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }

    }
}
