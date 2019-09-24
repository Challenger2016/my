/**
 * @(#)ThreadPool.java
 *
 * @author Challenger
 * @version 1.0 2019年9月5日
 *
 * Copyright (C) 2000,2019 , TeamSun, Inc.
 */
package com.study.my.thread.pool;

import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Purpose:
 * 
 * @author Challenger
 * @see	    
 * @since   6.1.0
 */
public class ThreadPool {

  private volatile static int j;
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println(Math.pow(33, 7));
    System.out.println(65%3233);
    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 120, TimeUnit.SECONDS, new PriorityBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    for (int i = 0; i < 10; i++) {
      executor.execute(new ThreadTest(j));
      
    }
    
    executor.execute(new Runnable() {
      public void run() {
        System.out.println("1234");
      }
    });
  }
  
  static class ThreadTest implements Runnable {
    private int i;
    
    public ThreadTest(int i) {
      this.i = i;
    }
    @Override
    public void run() {
      // TODO Auto-generated method stub
      i++;
      System.out.print(i);
    }
    
  }

}



/**
 * Revision history
 * -------------------------------------------------------------------------
 * 
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2019年9月5日 Challenger 创建版本
 */