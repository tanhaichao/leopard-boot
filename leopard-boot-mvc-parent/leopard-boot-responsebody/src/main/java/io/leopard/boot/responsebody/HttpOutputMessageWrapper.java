package io.leopard.boot.responsebody;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;

public class HttpOutputMessageWrapper implements HttpOutputMessage {

	HttpOutputMessage outputMessage;

	OutputStream output;

	public HttpOutputMessageWrapper(HttpOutputMessage outputMessage, OutputStream output) {
		this.outputMessage = outputMessage;
		this.output = output;
	}

	@Override
	public HttpHeaders getHeaders() {
		return outputMessage.getHeaders();
	}

	@Override
	public OutputStream getBody() throws IOException {
		return output;
	}

}
