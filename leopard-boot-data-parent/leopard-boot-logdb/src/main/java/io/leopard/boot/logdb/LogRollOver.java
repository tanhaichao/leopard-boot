package io.leopard.boot.logdb;

public interface LogRollOver {

	String getFilename();

	void autoRollOver() throws Exception;
}
