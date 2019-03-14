package com.github.yu_jay.rabbitmqClient.it;

import java.util.List;

public class MQService {

	public final MQAddress mqAddress;
	public final List<BindRelation> bindRelations;
	public MQService(MQAddress mqAddress, List<BindRelation> bindRelations) {
		super();
		this.mqAddress = mqAddress;
		this.bindRelations = bindRelations;
	}
}
