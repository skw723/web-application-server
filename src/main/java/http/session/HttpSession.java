package http.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
    private String sessionId;

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public String getId() {
        return sessionId;
    }

    public void invalidate() {
        HttpSessions.removeSession(sessionId);
    }
}
