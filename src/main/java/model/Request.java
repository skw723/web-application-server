package model;

import java.util.Map;

public class Request {
	private String url;
	private String method;
	private Map<String, String> queryString;
	private int contentLength;
	private Map<String, String> headers;
	private Map<String, String> cookies;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getQueryString() {
		return queryString;
	}

	public void setQueryString(Map<String, String> queryString) {
		this.queryString = queryString;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
}
