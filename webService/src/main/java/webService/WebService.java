package webService;

import com.sun.net.httpserver.HttpServer;
import handler.IndexHandler;
import handler.SaveHandler;
import handler.StyleHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebService {
    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/style", new StyleHandler());
            server.createContext("/save", new SaveHandler());
            server.createContext("/", new IndexHandler());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        server.setExecutor(null); // creates a default executor

        System.out.println("Starting HTTP service on :8080...");
        server.start();
    }
}
