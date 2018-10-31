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
        try {
            JsonObject reqBody = PipelionServer.getRequestBody(exchange);
            new AssetsDAO().addAsset(new Gson().fromJson(reqBody, Asset.class));
            PipelionServer.sendResponse(exchange, 200, "Succesfully added asset");
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }


    }
}
