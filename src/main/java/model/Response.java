package model;

public class Response {
	private HttpStatusCode status = HttpStatusCode.OK;
	private String contentType = "Content-Type: text/html;charset=utf-8";
	private byte[] content;

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
}
