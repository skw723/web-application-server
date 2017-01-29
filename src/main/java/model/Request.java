package model;

import java.util.Map;

public class Request {
	private String url;
	private String method;
	private Map<String, String> queryString;
	
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
}
