package com.github.yu_jay.rabbitmqClient.core;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.github.yu_jay.rabbitmqClient.it.IReceiver;
import com.github.yu_jay.rabbitmqClient.it.QueueHandler;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

class Queue {
	public String name;
	public Boolean durable;
	public Boolean autoDelete;
	public String[] routingKeys;
	Channel channelQueue;
	IReceiver receiver;
	MQConsumer consumer;

	public Queue(QueueHandler queueHandler) {
		this.name = queueHandler.name;
		this.durable = queueHandler.durable;
		this.autoDelete = queueHandler.autoDelete;
		this.routingKeys = queueHandler.routingKeys;
		this.receiver = queueHandler.receiver;
	}

	public void create(Channel channel, String exchangeName) throws IOException {
//		Map<String, Object> args = new HashMap<String, Object>();
//		args.put("x-max-length", 10);
		
		channel.queueDeclare(this.name, this.durable, false, this.autoDelete, null);
		for (String routKey : this.routingKeys) {
			channel.queueBind(this.name, exchangeName, routKey);
		}
	}

	public void bind(Connection conn) throws IOException {
		channelQueue = conn.createChannel();
		consumer = new MQConsumer(channelQueue, receiver, this.name);

		channelQueue.basicQos(20);
		channelQueue.basicConsume(this.name, false, this.name, consumer);
	}

	public void close() {		
		if(consumer != null){
			consumer.stopConsumer();
		}
		
		if (channelQueue != null) {
			try {
				channelQueue.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}
