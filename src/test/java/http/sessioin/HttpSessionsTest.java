package http.sessioin;

import http.session.HttpSession;
import http.session.HttpSessions;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class HttpSessionsTest {
    @Test
    public void getSessionNull() {
        HttpSession session = HttpSessions.getSession(null);
        assertNull(session);
    }
}
