package com.github.yu_jay.rabbitmqClient.it;

public class ExchangeInfo {
	public String name;
	public Boolean durable;
	public Boolean autoDelete;
	public ExchangeType type;

	public ExchangeInfo(String name, Boolean durable, Boolean autoDelete,
			ExchangeType type) {
		this.name = name;
		this.durable = durable;
		this.autoDelete = autoDelete;
		this.type = type;
	}
}
