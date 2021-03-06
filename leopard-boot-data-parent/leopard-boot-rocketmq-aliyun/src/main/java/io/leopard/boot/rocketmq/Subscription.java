package io.leopard.boot.rocketmq;

public class Subscription {

	private String groupId;
	private String topic;
	private String expression;

	public Subscription() {
	}

	public Subscription(String groupId, String topic) {
		this.groupId = groupId;
		this.topic = topic;
	}

	public Subscription(String groupId, String topic, String expression) {
		this.groupId = groupId;
		this.topic = topic;
		this.expression = expression;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
