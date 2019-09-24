package com.study.my.annotation;

import java.lang.reflect.Method;

import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;

public class Test {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Class test = Test.class;
    Class zhujie = DosomeThing.class;
    
    
    Method[] methods = test.getMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(zhujie)) {
        DosomeThing dosomeThing = (DosomeThing) method.getAnnotation(zhujie);
        
        System.out.println(dosomeThing.key());
        System.out.println(dosomeThing.cacheName());
        System.out.println(dosomeThing.needLog());
      }
    }
    

  }
  
  @DosomeThing(key="#student.id", cacheName="#student.name", needLog=true)
  public static void name(Student student) {
    System.out.println(student.getId());
    System.out.println(student.getName());
  }

}
