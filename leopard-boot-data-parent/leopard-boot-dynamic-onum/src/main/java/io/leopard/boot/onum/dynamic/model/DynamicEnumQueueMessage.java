package io.leopard.boot.onum.dynamic.model;

/**
 * 动态枚举队列的消息
 * 
 * @author 谭海潮
 *
 */
public class DynamicEnumQueueMessage {
	/**
	 * 消息发送者
	 */
	private String sender;

	/**
	 * 消息
	 */
	private String message;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
