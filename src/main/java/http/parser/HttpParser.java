package http.parser;

import http.request.HttpRequestRawData;

        import java.io.IOException;
        import java.io.InputStream;

public interface HttpParser {
    HttpRequestRawData parse(InputStream in) throws IOException;
}
