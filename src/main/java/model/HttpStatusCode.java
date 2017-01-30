package model;

public enum HttpStatusCode {
	OK("200"), Found("302");

	private String code;

	private HttpStatusCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
