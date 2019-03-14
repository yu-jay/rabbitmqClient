package com.github.yu_jay.rabbitmqClient.it;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.github.yu_jay.rabbitmqClient.core.Server;

public class MutiMQGateWay {

	static Map<String, Server> serverMap = null;

	/**
	 * ��ʼ����MQ������
	 * 
	 * @param mqAddress
	 * @param bindRelations
	 * @param infoLog
	 * @param errorLog
	 * @throws Exception
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
	 * ��ֹ��MQ������
	 * 
	 * @throws Exception
	 */
	public static void stop() throws Exception {
		for (Server svr : serverMap.values()) {
			svr.stop();
		}
	}

	/**
	 * ��MQ������Ϣ
	 * 
	 * @param routingKey
	 *            ����ʱ����Ϣ�ؼ���
	 * @param message
	 *            ��������Ϣ�ı�
	 * @return �����Ƿ�ɹ�, true:�����ɹ�; false:����ʧ��;
	 */
	public static Boolean publish(String mqIp, String routingKey,
			String message) {
		if (serverMap != null) {
			return serverMap.get(mqIp).publish(routingKey, message);
		}

		return false;
	}
}
