package http.parser.impl;

import com.google.common.base.Strings;
import http.HttpMethod;
import http.parser.HttpParser;
import http.request.HttpRequestRawData;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DefaultHttpRequestParser implements HttpParser {
    private static final int REQUEST_LINE_TOKEN_COUNT = 3;
    private static final int METHOD_POS = 0;
    private static final int URI_POS = 1;
    private static final int HTTP_VERSION_POS = 2;

    @Override
    public HttpRequestRawData parse(InputStream in) throws IOException {
        HttpRequestRawData rawData = new HttpRequestRawData();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        parseRequestLine(reader, rawData);
        parseHeader(reader, rawData);
        parseBody(reader, rawData);
        return rawData;
    }

    private void parseBody(BufferedReader reader, HttpRequestRawData rawData) throws IOException {
        if (rawData.getMethod() == HttpMethod.POST) {
            String body = IOUtils.readData(reader, Integer.parseInt(rawData.getHeaders().get("Content-Length")));
            rawData.setContent(Strings.nullToEmpty(body));
            rawData.getParameters().putAll(HttpRequestUtils.parseQueryString(rawData.getContent()));
        }
    }

    private void parseHeader(BufferedReader reader, HttpRequestRawData rawData) throws IOException {
        String line = null;
        Map<String, String> headers = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                break;
            }
            HttpRequestUtils.Pair header = HttpRequestUtils.parseHeader(line);
            headers.put(header.getKey(), header.getValue());
        }
        rawData.setHeaders(headers);
    }

    private void parseRequestLine(BufferedReader reader, HttpRequestRawData rawData) throws IOException {
        String line = reader.readLine();
        if (Strings.isNullOrEmpty(line)) {
            throw new IOException("Request-Line is empty");
        }

        String[] token = line.split(" ");
        if (token.length != REQUEST_LINE_TOKEN_COUNT) {
            throw new IllegalArgumentException("Request-Line requires 3 token");
        }

        rawData.setMethod(HttpMethod.valueOf(token[METHOD_POS]));
        parseUri(rawData, token[URI_POS]);
        rawData.setHttpVersion(token[HTTP_VERSION_POS]);
    }

    private void parseUri(HttpRequestRawData rawData, String uri) {
        String[] token = uri.split("\\?");
        rawData.setUri(token[0]);
        if (isIncludeQueryString(token) && rawData.getMethod() == HttpMethod.GET) {
            rawData.getParameters().putAll(HttpRequestUtils.parseQueryString(token[1]));
        }
    }

    private boolean isIncludeQueryString(String[] token) {
        return token.length == 2;
    }
}
