package http.request;

import http.HttpMethod;
import http.parser.HttpParserFactory;
import http.parser.impl.DefaultHttpParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream을 생성자로 받아 HTTP 메서드, URL, 헤더, 본문으로 분리
 */
public class HttpRequest {
    private HttpRequestRawData rawData;

    public HttpRequest(InputStream in) throws IOException {
        this(in, new DefaultHttpParserFactory());
    }

    public HttpRequest(InputStream in, HttpParserFactory factory) throws IOException {
        rawData = factory.getParser().parse(in);
    }

    public HttpMethod getMethod() {
        return rawData.getMethod();
    }

    public String getPath() {
        return rawData.getUri();
    }

    public String getHeader(String name) {
        return rawData.getHeaders().get(name);
    }

    public String getParameter(String name) {
        return rawData.getParameters().get(name);
    }
}
