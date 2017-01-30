package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import model.Request;
import model.User;
import util.HttpRequestUtils;
import util.HttpRequestUtils.Pair;
import util.IOUtils;

public class RequestHandler extends Thread {
	private static final int INDEX_METHOD = 0;

	private static final int INDEX_URL = 1;

	private static final int MINIMUM_COUNT = 3;

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			Request request = createRequest(reader);
			byte[] body = getBody(request);

			DataOutputStream dos = new DataOutputStream(out);
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private Request createRequest(BufferedReader reader) throws IOException {
		String firstLine = reader.readLine();
		if (Strings.isNullOrEmpty(firstLine)) {
			throw new IllegalStateException("invalid request: request is empty");
		}
		String[] methodAndUrl = firstLine.split(" ");
		if (methodAndUrl.length < MINIMUM_COUNT) {
			throw new IllegalStateException("invalid request: request url is required");
		}

		Request request = new Request();
		request.setMethod(methodAndUrl[INDEX_METHOD]);
		setUrlAndQueryString(request, methodAndUrl[INDEX_URL]);
		setHeader(reader, request);
		
		if (request.getMethod().equals("POST")) {
			String body = IOUtils.readData(reader, Integer.parseInt(request.getHeaders().get("Content-Length")));
			request.setQueryString(HttpRequestUtils.parseQueryString(body));			
		}

		return request;
	}

	private void setHeader(BufferedReader reader, Request request) throws IOException {
		String line;
		Map<String, String> headers = new HashMap<>();
		while ((line = reader.readLine()) != null) {
			if (line.equals("")) {
				break;
			}
			Pair header = HttpRequestUtils.parseHeader(line);
			headers.put(header.getKey(), header.getValue());
		}
		
		request.setHeaders(headers);
	}

	private void setUrlAndQueryString(Request request, String url) {
		String[] token = url.split("\\?");
		request.setUrl(token[0]);
		String queryString = null;
		if (token.length > 1) {
			queryString = token[1];
		}
		request.setQueryString(HttpRequestUtils.parseQueryString(queryString));
	}

	/**
	 * View Resolver 우선순위 설정 필요
	 */
	private byte[] getBody(Request request) throws IOException {
		if (request.getUrl().equals("/user/create")) {
			Map<String, String> queryString = request.getQueryString();
			User user = new User(queryString.get("userId"), queryString.get("password"), queryString.get("name"), queryString.get("email"));
			return user.toString().getBytes();
		} else {
			return Files.readAllBytes(new File("./webapp" + request.getUrl()).toPath());
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
