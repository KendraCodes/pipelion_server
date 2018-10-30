package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.AssetsDAO;
import model.Asset;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Kendra on 10/30/2018.
 */
public class CreateAssetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //takes an asset object in the request body and adds it to the database

    }
}
