package com.study.my.spring.xml;

import com.study.my.spring.bean.Color;
import com.study.my.spring.bean.Red;
import com.study.my.spring.config.MyBeanPostProcessor;
import com.study.my.spring.config.MyLifeCycle;
import com.study.my.spring.imports.MyFactoryBean;
import com.study.my.spring.imports.MyImportBeanDefinitionRegistrar;
import com.study.my.spring.imports.MyImportSelector;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(value = "com.study.my.spring", includeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION, classes = {Service.class
, Configuration.class})}, useDefaultFilters = false)
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class
, MyFactoryBean.class, MyBeanPostProcessor.class})
public class Config {


    @Bean("myFactoryBean")
    public MyFactoryBean getMyFactoryBean(){
       return new MyFactoryBean();
    }

    @Lazy
    @Scope("singleton")
    @Bean("person")
    public Person getBean(){
        Person person = new Person();
        person.setAge(10);
        person.setName("tom");
        System.out.println("初始化bean");
        return person;
    }

    @Bean("windows")
    @Conditional(ConditionalWindows.class)
    public Person windows(){
        Person person = new Person();
        person.setAge(10);
        person.setName("tom");
        System.out.println("初始化bean");
        return person;
    }
    @Bean("linux")
    @Conditional(ConditionalLinux.class)
    public Person linux(){
        Person person = new Person();
        person.setAge(10);
        person.setName("tom");
        System.out.println("初始化bean");
        return person;
    }

}
