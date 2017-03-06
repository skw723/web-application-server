package webserver;

import controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try {
            HttpRequest request = new HttpRequest(connection.getInputStream());
            HttpResponse response = new HttpResponse(connection.getOutputStream());
            Controller controller = RequestMapping.getMapping(request.getPath());
            if (controller == null) {
                response.forward(request.getPath());
            } else {
                controller.service(request, response);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (IOException ignore) {

            }
        }
    }
}
