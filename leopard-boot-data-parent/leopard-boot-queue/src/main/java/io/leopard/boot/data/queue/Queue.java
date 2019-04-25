package io.leopard.boot.data.queue;

public interface Queue {

	void publish(String key, String message);

	void subscribe(String key, IConsumer callback);

}
