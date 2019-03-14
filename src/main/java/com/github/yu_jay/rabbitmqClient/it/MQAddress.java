package com.github.yu_jay.rabbitmqClient.it;

public class MQAddress {
	public String mqIp;
	public Integer port = null;
	public String vHost;
	public String user;
	public String passWord;

	public MQAddress(String mqIp, String vHost, String user, String passWord) {
		this.mqIp = mqIp;
		this.vHost = vHost;
		this.user = user;
		this.passWord = passWord;
	}

	public MQAddress(String mqIp, int port, String vHost, String user,
			String passWord) {
		this(mqIp, vHost, user, passWord);		
		this.port = port;
	}
	
	
}
