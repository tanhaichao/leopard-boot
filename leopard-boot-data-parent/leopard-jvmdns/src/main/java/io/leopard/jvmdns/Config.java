package io.leopard.jvmdns;

import java.io.IOException;
import java.io.InputStream;

public interface Config {

	InputStream find() throws IOException;
}
