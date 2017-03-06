package http.parser.impl;

import http.parser.HttpParser;
import http.parser.HttpParserFactory;

public class DefaultHttpParserFactory implements HttpParserFactory {
    @Override
    public HttpParser getParser() {
        return getParser("default");
    }

    @Override
    public HttpParser getParser(String type) {
        if (type.equals("default")) {
            return new DefaultHttpRequestParser();
        } else {
            //Default Parser
            return new DefaultHttpRequestParser();
        }
    }
}
