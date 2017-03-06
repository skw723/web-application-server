package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.IOException;

public class CreateUserController extends AbstractController {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        DataBase.addUser(user);

        response.sendRedirect("/index.html");
    }
}
