package com.github.yu_jay.rabbitmqClient.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

class MQUtil {

	private static final String ChartSet = "utf-8";

	static byte[] encode(String message) {
		if (message != null) {
			try {
				return message.getBytes(ChartSet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	static String decode(byte[] bytes) {
		if (bytes != null) {
			try {
				return new String(bytes, ChartSet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	static void close(Channel channel) {
		if (channel != null) {
			try {
					channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (java.util.concurrent.TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
	
	static ConnectionFactory createConnFactory(Server server, ExecutorService executor) {
		ConnectionFactory factory = new ConnectionFactory();
		if(executor != null) {
			factory.setSharedExecutor(executor);
		}
		factory.setHost(server.serverIp);
		if(server.port != null) {
			factory.setPort(server.port);
		}
		factory.setRequestedHeartbeat(10);
		factory.setUsername(server.user);
		factory.setPassword(server.passWord);
		factory.setVirtualHost(server.vHostName);
//		factory.setAutomaticRecoveryEnabled(true);
//		factory.setNetworkRecoveryInterval(5000);
		return factory;
	}
}
