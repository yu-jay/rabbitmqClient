package com.github.yu_jay.rabbitmqClient.it;

import java.util.List;

public class BindRelation {
	public ExchangeInfo bindToExchange;
	public List<QueueHandler> queueHandlers;

	public BindRelation(ExchangeInfo bindToExchange,
			List<QueueHandler> queueHandlers) {
		this.bindToExchange = bindToExchange;
		this.queueHandlers = queueHandlers;
	}
}
