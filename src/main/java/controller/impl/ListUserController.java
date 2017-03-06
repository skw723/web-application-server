package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import util.HttpRequestUtils;

import java.io.IOException;

public class ListUserController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        boolean isLogin = isLogin(request.getHeader("Cookie"));
        if (isLogin) {
            StringBuilder content = new StringBuilder();
            for (User user : DataBase.findAll()) {
                content.append(user.getUserId());
                content.append(" ");
                content.append(user.getName());
                content.append(" ");
                content.append(user.getEmail());
                content.append("<br/>");
            }
            response.forwardBody(content.toString());
        } else {
            response.sendRedirect("/index.html");
        }
    }

    private boolean isLogin(String cookie) {
        return Boolean.valueOf(HttpRequestUtils.parseCookies(cookie).get("logined"));
    }
}
