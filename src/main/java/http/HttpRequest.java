package http;

import http.session.HttpSession;
import http.session.HttpSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> headers = new HashMap<String, String>();

    private Map<String, String> params;

    private RequestLine requestLine;

    private Map<String, String> cookies;

    public HttpRequest(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            log.debug("request line : {}", line);

            if (line == null) {
                return;
            }

            requestLine = new RequestLine(line);

            while (!line.equals("")) {
                line = br.readLine();
                log.debug("header : {}", line);
                if (!line.equals("")) {
                    String[] tokens = line.split(":");
                    headers.put(tokens[0], tokens[1].trim());
                }
            }

            if (requestLine.isPost()) {
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            } else {
                params = requestLine.getParams();
            }

            cookies = HttpRequestUtils.parseCookies(headers.get("Cookie"));
        } catch (IOException io) {
            log.error(io.getMessage());
        }
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public HttpSession getSession() {
        return getSession(true);
    }

    public HttpSession getSession(boolean isCreate) {
        HttpSession session = HttpSessions.getSession(cookies.get("JSESSIONID"));
        if (session == null && isCreate) {
            session = new HttpSession(UUID.randomUUID().toString());
            HttpSessions.addSession(session);
        }
        return session;
    }
}
