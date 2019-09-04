package com.study.my.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * Purpose:
 * 
 * @author Challenger
 * @see	    
 * @since   6.1.0
 */
public class JedisTest {

  private static final int COUNT_RUNNER = 5000;
  
  protected static AtomicInteger success = new AtomicInteger(0);
  
  protected static AtomicInteger fail = new AtomicInteger(0);

  static JedisPool jedisPool;
  static {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
   // 基本配置
   poolConfig.setMaxTotal(5000);           // 最大连接数
   poolConfig.setMaxIdle(32);              // 最大空闲连接数

   jedisPool = new JedisPool(poolConfig, "192.168.1.50", 6379, 30000, "bizvane");
  }
  
  public static Jedis getJedis() {
    return jedisPool.getResource();
  }
  

  protected static class Runner implements Runnable {

      private CountDownLatch countDownLatch;

      public Runner(CountDownLatch countDownLatch) {
          this.countDownLatch = countDownLatch;
      }

      public void run() {

        
        countDownLatch.countDown();
        try {
          countDownLatch.await();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
          try {
 
            Jedis jedis = getJedis();
            String lpop = jedis.lpop("key2");
            jedis.close();
            if (lpop != null) {
              // 乐观锁
              System.out.println("成功:" + (success.getAndIncrement()));

            } else {
              System.out.println("失败:" + (fail.getAndIncrement()));
            }
          } catch (Exception e) {
              e.printStackTrace();
          }

      
      }
  }
  
  public static void main(String[] args) throws Exception {

    Jedis jedis = getJedis();
    jedis.set("key", "10");
    jedis.lpush("key2", "a","a","a","a","a","a","a","a","a","a");
    jedis.close();
    CountDownLatch countDownLatch = new CountDownLatch(COUNT_RUNNER);

    // 创建3人跑道 / 装上门
    ExecutorService executorService = Executors.newFixedThreadPool(COUNT_RUNNER);

    for (int i = 0; i < COUNT_RUNNER; i++) {
        executorService.submit(new Runner(countDownLatch));
    }
    
    Thread.sleep(10000);
    System.out.println(getJedis().get("key"));
    System.out.println(success);
    System.out.println(fail);

  }
  
}



/**
 * Revision history
 * -------------------------------------------------------------------------
 * 
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2019年8月16日 Challenger 创建版本
 */