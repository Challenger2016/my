package com.study.my.rocketmq;


import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

public class ProducerTest {
    public static void main(String[] args) throws MQClientException {

      DefaultMQProducer producer = new DefaultMQProducer("GID_coupon_test", new AclClientRPCHook(new SessionCredentials("RocketMQ", "12345678")));
      //设置为自己的云上接入点
      producer.setNamesrvAddr("192.168.1.214:9876");
        // 云上消息轨迹需要设置为 CLOUD
        producer.setAccessChannel(AccessChannel.CLOUD);
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        producer.start();

        for (int j = 7; j >=0; j--) {
        //循环发送消息
          for (int i = 0; i < 100; i++){
              Message msg = new Message( //
                  // Message 所属的 Topic
                  "coupon_test",
                  // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                  "TagA",
                  // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预，
                  // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                  (i + "").getBytes());

              try {
                
                  SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                      // TODO Auto-generated method stub
                      
                      return mqs.get((Integer)arg);
                    }
                  }, j);
                  // 同步发送消息，只要不抛异常就是成功
                  if (sendResult != null) {
                      System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMsgId());
                  }
              }
              catch (Exception e) {
                  // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                  System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
                  e.printStackTrace();
              }
          }
        }
        

        // 在应用退出前，销毁 Producer 对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
        
        
    }
}