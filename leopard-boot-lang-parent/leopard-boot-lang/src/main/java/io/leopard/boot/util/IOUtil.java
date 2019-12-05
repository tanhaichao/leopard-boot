package io.leopard.boot.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public static ByteArrayInputStream toByteArrayInputStream(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = input.read(data, 0, 1024)) != -1) {
			output.write(data, 0, count);
		}
		data = null;
		input.close();
		return new ByteArrayInputStream(output.toByteArray());
	}
}
