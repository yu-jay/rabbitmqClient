package com.github.yu_jay.rabbitmqClient.core;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.github.yu_jay.rabbitmqClient.it.ExchangeInfo;
import com.github.yu_jay.rabbitmqClient.it.QueueHandler;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

class Exchange {

	private String name;
	private Boolean durable;
	private Boolean autoDelete;
	private String type;
	private List<Queue> queues;
	private Channel publishChannel;

	Exchange(ExchangeInfo exchange, List<QueueHandler> lstHandler) {
		if (exchange != null) {
			this.name = exchange.name;
			this.durable = exchange.durable;
			this.autoDelete = exchange.autoDelete;
			switch (exchange.type) {
			case TOPIC:
				this.type = "topic";
				break;
			case DIRECT:
				this.type = "direct";
				break;
			case FANOUT:
				this.type = "fanout";
				break;
			default:
				break;
			}
		}
		if (lstHandler != null) {
			this.queues = lstHandler.stream().filter(q -> q != null).map((q) -> new Queue(q))
					.collect(Collectors.toList());
		}
	}

	void start(Connection conn) throws Exception {
		synchronized (this) {
			create(conn);
			bind(conn);
			publishChannel = conn.createChannel();
		}
	}

	boolean publish(String routingKey, String message) {
		if (publishChannel != null) {
			synchronized (this) {
				try {
					if (publishChannel != null) {
						publishChannel.basicPublish(this.name, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN,
								MQUtil.encode(message));
						return true;
					}
				} catch (Exception e) {
					publishChannel = null;
				}
			}
		}

		return false;
	}

	void disablePublish() {
		synchronized (this) {
			publishChannel = null;
		}
	}

	private void create(Connection conn) throws Exception {
		Channel channel = null;
		try {
			channel = conn.createChannel();
			channel.exchangeDeclare(this.name, this.type, this.durable, this.autoDelete, null);

			if(this.queues != null) {
				for (Queue queue : queues) {
					queue.create(channel, this.name);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			MQUtil.close(channel);
		}
	}

	private void bind(Connection conn) throws IOException {
		if(this.queues != null) {
			for (Queue queue : queues) {
				queue.bind(conn);
			}
		}
	}

	void close() {
		synchronized (this) {
			try {
				if(publishChannel != null) {
				    publishChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
			publishChannel = null;
			if (queues != null) {
				for (Queue queue : queues) {
					queue.close();
				}
			}
		}
	}

}
