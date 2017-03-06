package controller;

import http.HttpMethod;
import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getMethod() == HttpMethod.POST) {
            doPost(request, response);
        } else {
            // Default는 Get
            doGet(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        response.forward(request.getPath(), HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.forward(request.getPath(), HttpStatusCode.METHOD_NOT_ALLOWED);
    }
}
