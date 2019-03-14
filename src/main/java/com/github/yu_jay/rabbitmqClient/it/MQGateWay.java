package com.github.yu_jay.rabbitmqClient.it;

import java.util.List;
import java.util.function.Consumer;

import com.github.yu_jay.rabbitmqClient.core.Server;

/**
 * MQ���������
 * 
 * @author STJ
 *
 */
public class MQGateWay {

	static Server server = null;

	/**
	 * ��ʼ����MQ������
	 * 
	 * @param mqAddress
	 *            MQ��ַ
	 * @param bindRelations
	 *            MQ�������Ͷ��а󶨹�ϵ
	 * @throws Exception
	 */
	public static void init(MQAddress mqAddress, List<BindRelation> bindRelations) throws Exception {
		if (server == null) {
			server = new Server(mqAddress, bindRelations);
			server.start();
		}
	}

	/**
	 * ��ʼ����MQ������
	 * 
	 * @param mqAddress
	 * @param bindRelations
	 * @param infoLog
	 * @param errorLog
	 * @throws Exception
	 */
	public static void init(MQAddress mqAddress, List<BindRelation> bindRelations, Consumer<String> infoLog,
			Consumer<String> errorLog) throws Exception {
		Logs.regLog(infoLog, errorLog);
		init(mqAddress, bindRelations);
	}

	/**
	 * ��ʼ����MQ������(ͬ��)
	 * 
	 * @param mqAddress
	 * @param bindRelations
	 * @param infoLog
	 * @param errorLog
	 * @throws Exception
	 */
	public static void synInit(MQAddress mqAddress, List<BindRelation> bindRelations, Consumer<String> infoLog,
			Consumer<String> errorLog) throws Exception {
		Logs.regLog(infoLog, errorLog);
		if (server == null) {
			server = new Server(mqAddress, bindRelations);
			server.synStart();
		}
	}

	/**
	 * ��ֹ��MQ������
	 * 
	 * @throws Exception
	 */
	public static void stop() throws Exception {
		if (server != null) {
			server.stop();
			server = null;
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
	public static Boolean publish(String routingKey, String message) {
		if (server != null) {
			return server.publish(routingKey, message);
		}
		return false;
	}
}
