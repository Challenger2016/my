package com.study.my.rocketmq;
/**
 * @(#)Snippet.java
 *
 * @author Challenger
 * @version 1.0 2019年9月2日
 *
 * Copyright (C) 2000,2019 , TeamSun, Inc.
 */


import java.util.List;
import java.util.Random;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 
 * Purpose:
 * 
 * @author Challenger
 * @see	    
 * @since   6.1.0
 */

public class Consumer {
  static int sum = 0;
  public static void main(String[] args) throws MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GID_coupon_test", new AclClientRPCHook(new SessionCredentials("RocketMQ", "12345678")), new AllocateMessageQueueAveragely());
    //设置为云上接入点
    consumer.setNamesrvAddr("192.168.1.214:9876");
    // 云上消息轨迹需要设置为 CLOUD
    consumer.setAccessChannel(AccessChannel.CLOUD);
    // 设置为云上创建的 Topic
    consumer.subscribe("coupon_test", "*");
    consumer.registerMessageListener(new MessageListenerConcurrently() {

      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        // TODO Auto-generated method stub
        try {
          Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        
        System.out.println(msgs.get(0).getQueueId());
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      
      }});
    consumer.start();
    
  }
  
}



/**
 * Revision history
 * -------------------------------------------------------------------------
 * 
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2019年9月2日 Challenger 创建版本
 */