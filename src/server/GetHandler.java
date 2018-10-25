package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.DatabaseManager;
import model.JsonUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kendra on 10/18/2018.
 */
public class GetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] pieces = exchange.getRequestURI().getPath().substring(1).split("/");
        if (pieces.length >= 2) {
            if (pieces[1].equals("departments")) {
                List<String> departments = new DatabaseManager().getDepartmentsList();
                byte[] data = JsonUtils.prettyPrintJson(departments).getBytes();
                PipelionServer.sendResponse(exchange, 200, data);
                return;
            }
        }
        PipelionServer.sendResponse(exchange, 200, "this is the get handler");
    }
}
