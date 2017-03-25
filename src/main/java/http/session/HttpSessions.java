package http.session;

import com.google.common.base.Strings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessions {
    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession addSession(HttpSession session) {
        return sessions.put(session.getId(), session);
    }

    public static HttpSession getSession(String sessionId) {
        if (Strings.isNullOrEmpty(sessionId)) {
            return null;
        }
        return sessions.get(sessionId);
    }

    public static HttpSession removeSession(String sessionId) {
        return sessions.remove(sessionId);
    }
}
