package webserver;

import controller.Controller;
import controller.impl.CreateUserController;
import controller.impl.ListUserController;
import controller.impl.LoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> REQUEST_MAPPING = new HashMap<>();

    static {
        REQUEST_MAPPING.put("/user/create", new CreateUserController());
        REQUEST_MAPPING.put("/user/login", new LoginController());
        REQUEST_MAPPING.put("/user/list", new ListUserController());
    }

    public static Controller getMapping(String uri) {
        return REQUEST_MAPPING.get(uri);
    }
}
