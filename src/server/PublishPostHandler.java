package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.ArtistsDAO;
import database.AssetsDAO;
import database.PostsDAO;
import model.Artist;
import model.Post;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Kendra on 10/30/2018.
 */
public class PublishPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            JsonObject reqBody = PipelionServer.getRequestBody(exchange);
            Post p = new Gson().fromJson(reqBody, Post.class);
            new PostsDAO().addPost(p);
            new ArtistsDAO().addArtist(new Artist(p.getArtistID(), p.getArtistName()));
            new ArtistsDAO().setWatching(p.getArtistID(), p.getAssetID(), true);
            new AssetsDAO().updateTime(p.getAssetID(), p.getTimestampString());
            PipelionServer.sendResponse(exchange, 200, "Successfully published post");
        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }

    }
}
