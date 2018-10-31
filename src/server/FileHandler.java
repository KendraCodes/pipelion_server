package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Kendra on 10/30/2018.
 */
public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

//        String[] pieces = exchange.getRequestURI().getPath().substring(1).split("/");///episodes/all

        String absolutePath = "C:\\Users\\Kendra\\Documents\\byu\\240\\newfamilymapserver\\familymapserver\\web\\";
        String relativePath = exchange.getRequestURI().getPath().substring(1);
        if (relativePath.length() == 0) {
            relativePath = "index.html";
        }
        byte[] data;
        try {
            data = Files.readAllBytes(Paths.get(absolutePath + relativePath));
        } catch (Exception e) {
            data = "oops".getBytes();
        }
        PipelionServer.sendResponse(exchange, HTTP_OK, data);
    }
}
