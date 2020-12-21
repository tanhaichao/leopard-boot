package io.leopard.boot.rocketmq;

import org.apache.rocketmq.common.message.Message;

public interface RocketmqMessageListener {

	void consumeMessage(Message message) throws Exception;
}
