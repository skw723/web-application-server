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

import model.User;

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
		byte[] expected = new User(id, password, name, email).toString().getBytes();
		BDDMockito.given(socket.getInputStream()).willReturn(new ByteArrayInputStream(String.format("GET /user/create?userId=%s&password=%s&name=%s&email=%s HTTP/1.1", id, password, name, email).getBytes()));
		BDDMockito.given(socket.getOutputStream()).willReturn(new ByteArrayOutputStream());
		
		RequestHandler handler = new RequestHandler(socket);
		//when
		handler.run();
		//then
		byte[] response = socket.getOutputStream().toString().getBytes();
		byte[] actual = new byte[expected.length];
		System.arraycopy(response, response.length - expected.length, actual, 0, expected.length);
		assertArrayEquals(expected, actual);
	}
}
