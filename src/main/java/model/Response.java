package model;

import java.util.Map;

public class Response {
	private HttpStatusCode status = HttpStatusCode.OK;
	private String contentType = "Content-Type: text/html;charset=utf-8";
	private byte[] content;
	private Map<String, Object> cookies;
	private Map<String, Object> headers;

	public HttpStatusCode getStatus() {
		return status;
	}

	public void setStatus(HttpStatusCode status) {
		this.status = status;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Map<String, Object> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, Object> cookies) {
		this.cookies = cookies;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}
}
