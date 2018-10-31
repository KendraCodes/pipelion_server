package server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.NotificationsDAO;
import model.JsonUtils;
import model.Notification;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kendra on 10/30/2018.
 */
public class GetNotificationsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // return a chunked list of notifications for the artist
        //chunking works just like posts or assets
        // URL = /notifications/get/[artistID]

        try {
            String[] pieces = exchange.getRequestURI().getPath().substring(1).split("/");
            String artistID = pieces[2];

            JsonObject reqBody = PipelionServer.getRequestBody(exchange);

            int howMany = PipelionServer.getChunkValue(reqBody);
            String id = PipelionServer.getString(reqBody, "id");

            List<Notification> notifications;
            if (id != null) {
                notifications = new NotificationsDAO().getNotifications(howMany, id);
            } else {
                notifications = new NotificationsDAO().getNotifications(howMany);
            }
            Collections.sort(notifications);
            PipelionServer.sendResponse(exchange, 200, JsonUtils.prettyPrintJson(notifications));

        } catch (Exception e) {
            e.printStackTrace();
            PipelionServer.sendResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }

    }
}


