package com.github.yu_jay.rabbitmqClient.it;

import java.util.List;
import java.util.function.Consumer;

import com.github.yu_jay.rabbitmqClient.core.Server;

/**
 * mq 交换入口
 * @author yujie
 *
 */
public class MQGateWay {

	static Server server = null;

	/**
	 * 初始化与MQ的连接
	 * 
	 * @param mqAddress
	 *            MQ地址ַ
	 * @param bindRelations
	 *            MQ交换机和队列绑定关系
	 * @throws Exception 异常
	 */
	public static void init(MQAddress mqAddress, List<BindRelation> bindRelations) throws Exception {
		if (server == null) {
			server = new Server(mqAddress, bindRelations);
			server.start();
		}
	}

	/**
	 * 初始化与MQ的连接
	 * 
	 * @param mqAddress MQ地址
	 * @param bindRelations 绑定信息
	 * @param infoLog 信息日志
	 * @param errorLog 错误日志
	 * @throws Exception 异常
	 */
	public static void init(MQAddress mqAddress, List<BindRelation> bindRelations, Consumer<String> infoLog,
			Consumer<String> errorLog) throws Exception {
		Logs.regLog(infoLog, errorLog);
		init(mqAddress, bindRelations);
	}

	/**
	 * 初始化与MQ的连接
	 * 
	 * @param mqAddress MQ地址
	 * @param bindRelations 绑定信息
	 * @param infoLog 信息日志
	 * @param errorLog 错误日志
	 * @throws Exception 异常
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
	 * 停止连接
	 * 
	 * @throws Exception 异常
	 */
	public static void stop() throws Exception {
		if (server != null) {
			server.stop();
			server = null;
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
	public static Boolean publish(String routingKey, String message) {
		if (server != null) {
			return server.publish(routingKey, message);
		}
		return false;
	}
}
