package http.sessioin;

import http.session.HttpSession;
import http.session.HttpSessions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpSessionTest {
    @Test
    public void sessionCreate() {
        HttpSession session = new HttpSession("sessionId");

        assertEquals("sessionId", session.getId());
    }

    @Test
    public void sessionAttribute() {
        HttpSession session = new HttpSession("sessionId");
        HttpSessions.addSession(session);
        // set
        session.setAttribute("attr1", "value");
        session.setAttribute("attr2", new ArrayList<String>());

        assertEquals("value", session.getAttribute("attr1"));
        assertTrue(session.getAttribute("attr2") instanceof ArrayList);

        // remove
        session.removeAttribute("attr2");

        assertTrue(session.getAttribute("attr2") == null);

        // invalidate;
        session.invalidate();

        assertTrue(HttpSessions.getSession(session.getId()) == null);
    }
}
