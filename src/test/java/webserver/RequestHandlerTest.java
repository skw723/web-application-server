package webserver;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

import org.junit.Test;
import org.mockito.BDDMockito;

public class RequestHandlerTest {
	@Test
	public void response_index_html() throws IOException {
		//given
		Socket socket = mock(Socket.class);
		BDDMockito.given(socket.getInputStream()).willReturn(new ByteArrayInputStream("GET /index.html HTTP/1.1".getBytes()));
		BDDMockito.given(socket.getOutputStream()).willReturn(new ByteArrayOutputStream());

		RequestHandler handler = new RequestHandler(socket);

		byte[] expected = Files.readAllBytes(new File("./webapp" + "/index.html").toPath());
		//when
		handler.run();
		//then
		byte[] response = socket.getOutputStream().toString().getBytes();
		byte[] actual = new byte[expected.length];
		System.arraycopy(response, response.length - expected.length, actual, 0, expected.length);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void create_user_by_get() throws IOException {
		//given
		Socket socket = mock(Socket.class);
		String id = "javajigi";
		String password = "password";
		String name = "JaeSung";
		String email = "javajigi@slipp.net";
		BDDMockito.given(socket.getInputStream()).willReturn(new ByteArrayInputStream(String.format("GET /user/create?userId=%s&password=%s&name=%s&email=%s HTTP/1.1", id, password, name, email).getBytes()));
		BDDMockito.given(socket.getOutputStream()).willReturn(new ByteArrayOutputStream());

		RequestHandler handler = new RequestHandler(socket);

		byte[] expected = Files.readAllBytes(new File("./webapp" + "/index.html").toPath());
		byte[] expectedStatus = "HTTP/1.1 302 Found".getBytes();
		//when
		handler.run();
		//then
		byte[] response = socket.getOutputStream().toString().getBytes();
		byte[] actual = new byte[expected.length];
		byte[] actualStatus = new byte[expectedStatus.length];
		System.arraycopy(response, response.length - expected.length, actual, 0, expected.length);
		System.arraycopy(response, 0, actualStatus, 0, expectedStatus.length);
		assertArrayEquals(expected, actual);
		assertArrayEquals(expectedStatus, actualStatus);
	}

	@Test
	public void create_user_by_post() throws IOException {
		//given
		Socket socket = mock(Socket.class);
		String id = "javajigi";
		String password = "password";
		String name = "JaeSung";
		BDDMockito.given(socket.getInputStream()).willReturn(new ByteArrayInputStream(String.format("POST /user/create HTTP/1.1\nContent-Length: 59\n\nuserId=%s&password=%s&name=%s", id, password, name).getBytes()));
		BDDMockito.given(socket.getOutputStream()).willReturn(new ByteArrayOutputStream());

		RequestHandler handler = new RequestHandler(socket);

		byte[] expected = Files.readAllBytes(new File("./webapp" + "/index.html").toPath());
		byte[] expectedStatus = "HTTP/1.1 302 Found".getBytes();
		//when
		handler.run();
		//then
		byte[] response = socket.getOutputStream().toString().getBytes();
		byte[] actual = new byte[expected.length];
		byte[] actualStatus = new byte[expectedStatus.length];
		System.arraycopy(response, response.length - expected.length, actual, 0, expected.length);
		System.arraycopy(response, 0, actualStatus, 0, expectedStatus.length);
		assertArrayEquals(expected, actual);
		assertArrayEquals(expectedStatus, actualStatus);
	}
}
