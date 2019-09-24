package com.study.my.spring.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Color implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("Color 销毁");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Color 初始化");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("Color postConstruct");
    }

    @PreDestroy
    public void PreDestroy(){
        System.out.println("Color PreDestroy");
    }


}