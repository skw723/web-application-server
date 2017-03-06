package http.parser;

public interface HttpParserFactory {
    HttpParser getParser();
    HttpParser getParser(String type);
}