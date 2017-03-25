package http;

import http.session.HttpSession;
import http.session.HttpSessions;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_Cookie() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET_Cookie.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("123", request.getCookie("cookie1"));
    }

    @Test
    public void getSession() throws FileNotFoundException {
        // isCreate true
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        HttpSession session = request.getSession();

        assertTrue(HttpSessions.getSession(session.getId()) != null);

        // isCreate false
        session.invalidate();

        session = request.getSession(false);

        assertNull(session);
    }
}
