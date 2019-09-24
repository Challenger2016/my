package com.study.my.mvnframework.service;

import com.mvnframework.annotation.Service;

@Service
public class DemoServiceImpl implements DemoService {

  public String hello(String name) {
    // TODO Auto-generated method stub
    return "hello," + name;
  }

}
