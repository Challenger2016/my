package com.study.my.mvnframework.servlet;

import com.mvnframework.annotation.Autowired;
import com.mvnframework.annotation.Controller;
import com.mvnframework.annotation.RequestMapping;
import com.mvnframework.annotation.Service;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class InitDispatcherServlet {

  private Properties properties = new Properties();

  private List<String> classNames = new ArrayList<String>();

  private Map<String, Object> ioc = new HashMap<String, Object>();

  public void init(String application) throws ServletException {

    this.doLoadConfig(application);

    this.doScanner(this.properties.getProperty("scanPackage"));

    try {
      this.doInstance();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      this.doAutoWired();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    this.initHandlerMapping();
  }

  private void initHandlerMapping() {
    if (this.ioc.isEmpty()) {
      return;
    }

    for (Map.Entry<String, Object> instance : this.ioc.entrySet()) {
      Class<?> clazz = instance.getValue().getClass();

      String url = "";
      if (clazz.isAnnotationPresent(RequestMapping.class)) {
        RequestMapping goPaoRequestMapping = clazz.getAnnotation(RequestMapping.class);
        url = goPaoRequestMapping.value();
      }

      Method[] methods = clazz.getMethods();
      for (Method method : methods) {
        if (!method.isAnnotationPresent(RequestMapping.class)) {
          continue;
        }
        RequestMapping goPaoRequestMapping = method.getAnnotation(RequestMapping.class);
        String mapping = goPaoRequestMapping.value();
        String regex = ("/" + url + "/" + mapping).replaceAll("/+", "/");
        Pattern pattern = Pattern.compile(regex);
        HandlerMapping.handleMaping.add(new Handler(instance.getValue(), method, pattern));
      }
    }
  }

  private void doAutoWired() throws IllegalArgumentException, IllegalAccessException {
    // TODO Auto-generated method stub
    if (this.ioc.isEmpty()) {
      return;
    }

    for (Map.Entry<String, Object> instance : this.ioc.entrySet()) {
      Field[] fields = instance.getValue().getClass().getDeclaredFields();
      for (Field field : fields) {
        if (!field.isAnnotationPresent(Autowired.class)) {
          continue;
        }

        Autowired goPaoAutowired = field.getAnnotation(Autowired.class);
        String beanName = goPaoAutowired.value().trim();
        if ("".equals(beanName)) {
          beanName = field.getType().getName();
        }
        field.setAccessible(true);
        field.set(instance.getValue(), this.ioc.get(beanName));
      }
      System.out.println(instance.getKey());
      System.out.println(instance.getValue());
    }


  }

  private void doInstance() throws InstantiationException, IllegalAccessException {
    // TODO Auto-generated method stub
    if (this.classNames.isEmpty()) {
      return;
    }

    for (String string : this.classNames) {
      Class<?> clazz = null;
      try {
        clazz = Class.forName(string);
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      if (clazz.isAnnotationPresent(Controller.class)) {
        char[] chars = string.toCharArray();
        chars[0] += 32;
        string = String.valueOf(chars);
        this.ioc.put(string, clazz.newInstance());

      } else if (clazz.isAnnotationPresent(Service.class)) {
        String beanName = clazz.getAnnotation(Service.class).value();
        Object newInstance = clazz.newInstance();

        if (!"".equals(beanName)) {
          this.ioc.put(beanName, newInstance);
          continue;
        }

        char[] chars = string.toCharArray();
        chars[0] += 32;
        string = String.valueOf(chars);
        this.ioc.put(string, newInstance);

        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> class1 : interfaces) {
          this.ioc.put(class1.getName(), newInstance);
        }
      }
    }
  }

  private void doScanner(String packageName) {
    // TODO Auto-generated method stub
    URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
    File dir = new File(url.getFile());

    for (File file : dir.listFiles()) {
      if (file.isDirectory()) {
        this.doScanner(packageName + "." + file.getName());;
      } else {
        this.classNames.add(packageName + "." + file.getName().replace(".class", "").trim());
      }
    }

  }

  private void doLoadConfig(String application) {
    InputStream is = this.getClass().getClassLoader().getResourceAsStream(application);
    try {
      this.properties.load(is);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

