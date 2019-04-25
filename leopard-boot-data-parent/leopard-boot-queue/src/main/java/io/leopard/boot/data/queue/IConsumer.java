package io.leopard.boot.data.queue;

public interface IConsumer {

	void consume(String message) throws Exception;

}
