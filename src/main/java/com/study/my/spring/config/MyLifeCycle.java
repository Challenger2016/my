package com.study.my.spring.config;

import com.study.my.spring.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyLifeCycle {
    @Scope("prototype")
    @Bean(value = "car", initMethod = "init", destroyMethod = "dest")
    public Car car(){
        System.out.println("Car实例化bean");
        return new Car();
    }

}
