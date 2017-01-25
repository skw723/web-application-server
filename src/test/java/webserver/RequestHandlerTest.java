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
}
