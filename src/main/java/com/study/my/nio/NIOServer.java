package com.study.my.nio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * @(#)NIOServer.java
 *
 * @author Challenger
 * @version 1.0 2019年8月19日
 *
 * Copyright (C) 2000,2019 , TeamSun, Inc.
 */
/**
 * 
 * Purpose:
 * 
 * @author Challenger
 * @see	    
 * @since   6.1.0
 */
public class NIOServer {

  private int port = 8000;
  private Selector selector;
  private InetSocketAddress address;
  
  public static void main(String[] args) throws IOException {
    
    new NIOServer(8888).listen();
      

  }
  public NIOServer(int port) throws IOException {
    this.port = port;
    address = new InetSocketAddress(this.port);
    ServerSocketChannel server = ServerSocketChannel.open();
    server.bind(address);
    server.configureBlocking(false);
    selector = Selector.open();
    server.register(selector, SelectionKey.OP_ACCEPT);
    System.out.println("服务端已启动");
  }

  public void listen() {
    try {
      while (true) {
        int wait = this.selector.select();
        if (wait == 0) {continue;}
        Set<SelectionKey> keys = this.selector.selectedKeys();
        Iterator<SelectionKey> i = keys.iterator();
        while (i.hasNext()) {
          SelectionKey key = i.next();
          process(key);
          i.remove();
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
  
  public void process(SelectionKey key) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    if (key.isAcceptable()) {
      ServerSocketChannel server = (ServerSocketChannel) key.channel();
      SocketChannel accept = server.accept();
      accept.configureBlocking(false);
      accept.register(selector, SelectionKey.OP_READ);
    } else if (key.isReadable()) {
      SocketChannel client = (SocketChannel) key.channel();
      int leng = client.read(buffer);
      if (leng > 0) {
        buffer.flip();
        String content = new String(buffer.array(), 0, leng);
        System.out.println(content);
        client.register(selector, SelectionKey.OP_WRITE);
      }
      buffer.clear();
    } else if (key.isWritable()) {
      SocketChannel client = (SocketChannel) key.channel();
      client.write(buffer.wrap("hello".getBytes()));
      client.register(selector, SelectionKey.OP_READ);
    } 
  }

}



/**
 * Revision history
 * -------------------------------------------------------------------------
 * 
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2019年8月19日 Challenger 创建版本
 */