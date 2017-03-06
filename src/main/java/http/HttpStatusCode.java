package http;

public enum HttpStatusCode {
    OK(200),
    FOUND(302),
    METHOD_NOT_ALLOWED(405);

    private int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
