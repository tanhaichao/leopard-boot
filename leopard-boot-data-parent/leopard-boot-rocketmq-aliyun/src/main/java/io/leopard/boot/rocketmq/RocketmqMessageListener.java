package io.leopard.boot.rocketmq;

import com.aliyun.openservices.ons.api.Message;

public interface RocketmqMessageListener {

	void consumeMessage(Message message) throws Exception;
}
