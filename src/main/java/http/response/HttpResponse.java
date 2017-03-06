package http.response;

import http.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    public void forward(String uri) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + uri).toPath());
        addHeader("Content-Length", String.valueOf(body.length));
        if (uri.endsWith(".css")) {
            addHeader("Content-Type", "text/css;charset=utf-8");
        } else {
            addHeader("Content-Type", "text/html;charset=utf-8");
        }
        response200Header();
        responseBody(body);
    }

    public void forwardBody(String body) throws IOException {
        addHeader("Content-Length", String.valueOf(body.length()));
        addHeader("Content-Type", "text/html;charset=utf-8");
        response200Header();
        responseBody(body.getBytes());
    }

    public void sendRedirect(String location) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found \r\n");
        addHeader("Location", location);
        processHeader();
        dos.close();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private void response200Header() throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        processHeader();
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body);
        dos.close();
    }

    private void processHeader() throws IOException {
        for (String key : headers.keySet()) {
            dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
        }
        dos.writeBytes("\r\n\r\n");
    }

    public void forward(String uri, HttpStatusCode code) throws IOException {
        if (code == HttpStatusCode.METHOD_NOT_ALLOWED) {
            String body = uri + "Method Not Allowed";
            addHeader("Content-Length", String.valueOf(body.length()));
            response405Header();
            responseBody(body.getBytes());
        } else {
            forward(uri);
        }
    }

    private void response405Header() throws IOException {
        dos.writeBytes("HTTP/1.1 405 Method Not Allowed \r\n");
        processHeader();
    }
}
