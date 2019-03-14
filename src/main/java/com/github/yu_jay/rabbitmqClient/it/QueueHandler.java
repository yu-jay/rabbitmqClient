package com.github.yu_jay.rabbitmqClient.it;

public class QueueHandler {
	public String name;
	public Boolean durable;
	public Boolean autoDelete;
	public String[] routingKeys;
	public IReceiver receiver;

	public QueueHandler(String name, Boolean durable, Boolean autoDelete, String routingKey, IReceiver receiver) {
		this.name = name;
		this.durable = durable;
		this.autoDelete = autoDelete;
		this.routingKeys = new String[] { routingKey };
		this.receiver = receiver;
	}

	public QueueHandler(String name, Boolean durable, Boolean autoDelete, String[] routingKeys, IReceiver receiver) {
		this.name = name;
		this.durable = durable;
		this.autoDelete = autoDelete;
		this.routingKeys = routingKeys;
		this.receiver = receiver;
	}
}
