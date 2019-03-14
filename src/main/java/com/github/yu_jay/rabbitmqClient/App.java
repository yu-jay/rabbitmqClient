package com.github.yu_jay.rabbitmqClient;

import java.util.ArrayList;
import java.util.List;

import com.github.yu_jay.rabbitmqClient.it.BindRelation;
import com.github.yu_jay.rabbitmqClient.it.ExchangeInfo;
import com.github.yu_jay.rabbitmqClient.it.ExchangeType;
import com.github.yu_jay.rabbitmqClient.it.MQAddress;
import com.github.yu_jay.rabbitmqClient.it.MQGateWay;
import com.github.yu_jay.rabbitmqClient.it.QueueHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        MQAddress mqAddress = new MQAddress("192.168.10.8", "/", 
				"admin", "admin");
		List<BindRelation> bindRelations = new ArrayList<BindRelation>();
		List<QueueHandler> queueHandlers = new ArrayList<QueueHandler>();
		bindRelations.add(new BindRelation(new ExchangeInfo("SURVEY_CENTER", true, false,ExchangeType.TOPIC),
				queueHandlers));
		
		MQGateWay.init(mqAddress,bindRelations, (s)->{System.out.println(s);},
				(s)->{System.out.println(s);});
		
		Thread.sleep(3000);
		
		MQGateWay.publish("its_Province_Index_updated", "ok");
    }
}
