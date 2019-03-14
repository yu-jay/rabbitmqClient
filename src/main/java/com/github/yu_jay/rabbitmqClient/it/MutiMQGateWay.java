package com.github.yu_jay.rabbitmqClient.it;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.github.yu_jay.rabbitmqClient.core.Server;

public class MutiMQGateWay {

	static Map<String, Server> serverMap = null;

	/**
	 * 初始化与MQ的连接
	 * 
	 * @param mqAddress MQ地址
	 * @param bindRelations 绑定信息
	 * @param infoLog 信息日志
	 * @param errorLog 错误日志
	 * @throws Exception 异常
	 */
	public static void init(Consumer<String> infoLog, Consumer<String> errorLog,
			MQService... mqServices) throws Exception {
		Logs.regLog(infoLog, errorLog);
		serverMap = new HashMap<String, Server>(mqServices.length);
		for (MQService mq : mqServices) {
			serverMap.put(mq.mqAddress.mqIp,
					new Server(mq.mqAddress, mq.bindRelations));
		}

		serverMap.values().stream().parallel().forEach(svr -> {
			try {
				svr.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 停止连接
	 * 
	 * @throws Exception 异常
	 */
	public static void stop() throws Exception {
		for (Server svr : serverMap.values()) {
			svr.stop();
		}
	}

	/**
	 * 向mq发送消息
	 * 
	 * @param routingKey
	 *            发布时的消息关键字
	 * @param message
	 *            发送内容
	 * @return 发送是否成功, true:成功; false:失败;
	 */
	public static Boolean publish(String mqIp, String routingKey,
			String message) {
		if (serverMap != null) {
			return serverMap.get(mqIp).publish(routingKey, message);
		}

		return false;
	}
}
