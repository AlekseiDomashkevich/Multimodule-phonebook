package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import phonebook.dao.PhonebookDao;
import phonebook.entity.Person;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class SaveHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var inputStream = exchange.getRequestBody();
        int c = 0;
        var body = new StringBuilder();
        while ((c = inputStream.read()) != -1) {
            body.append((char) c);
        }
        var args = Arrays.stream(body.toString().split("&")).collect(Collectors.toList());
        var fields = new HashMap<String, String>();
        args.forEach(arg -> fields.put(arg.split("=")[0], arg.split("=")[1]));


        var dao = new PhonebookDao();
        args.add(0, "");
        args.forEach(System.out::println);
        var person = new Person();
        person.setFirstname(fields.get("firstname"));
        person.setLastname(fields.get("lastname"));
        person.setAge(Integer.parseInt(fields.get("age")));
        person.setPhoneNumber(fields.get("phone"));
        person.setAddress(fields.get("address"));
        dao.save(person);
        var file = new File("./webService/public/save.html");
        var response = Files.readAllBytes(Paths.get(file.getPath()));
        exchange.sendResponseHeaders(200, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }
}
