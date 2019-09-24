package com.study.my.spring.xml;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        annotation();

    }

    public static void xml(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person bean = classPathXmlApplicationContext.getBean(Person.class);
        System.out.println(bean.getName());
        System.out.println(bean.getAge());
        String[] beanDefinitionNames = classPathXmlApplicationContext.getBeanDefinitionNames();
        for (String beanName:
                beanDefinitionNames) {
            System.out.println(beanName);
        }
    }

    public static void annotation(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
//        Person bean = annotationConfigApplicationContext.getBean(Person.class);
//        bean.setName("lisa");
//        System.out.println(bean.getName());
//        Person bean2 = annotationConfigApplicationContext.getBean(Person.class);
//        System.out.println(bean2.getName());
//        System.out.println(bean.getAge());
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String beanName:
                beanDefinitionNames) {
            System.out.println(beanName);
        }
        annotationConfigApplicationContext.getBean("car");
        annotationConfigApplicationContext.getBean("car");
        annotationConfigApplicationContext.close();
    }

}
