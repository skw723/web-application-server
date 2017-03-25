package http;

import java.io.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

    @Test
    public void responseSetCookie() throws FileNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(bos);
        response.addCookie("cookie1", "value");
        response.sendRedirect("/index.html");

        assertEquals("value", response.getCookie("cookie1"));
        assertTrue(bos.toString().contains("Set-Cookie: cookie1=value"));
    }
}