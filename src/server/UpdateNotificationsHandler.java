package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Kendra on 10/30/2018.
 */
public class UpdateNotificationsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // update whether a notification is read or unread
        // URL = /notifications/update/[artistID] to mark read or unread
        // { "notification_id", "isRead" } and "notification_id" might be "all"
        PipelionServer.sendResponse(exchange, 200, "Notification has been updated");
    }
}
