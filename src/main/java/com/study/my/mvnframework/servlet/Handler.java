package com.study.my.mvnframework.servlet;

import com.mvnframework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Handler {
  
  protected Object controller;
  
  protected Method method;
  
  protected Pattern pattern;
  
  protected Map<String, Integer> paramIndexMapping;
  
  public Handler(Object controller, Method method, Pattern pattern) {
    this.controller = controller;
    this.method = method;
    this.pattern = pattern;
    paramIndexMapping = new HashMap<String, Integer>();
    putParamIndexMapping(method);
  }
  
  private void putParamIndexMapping(Method method) {
    Annotation[][] pa = method.getParameterAnnotations();
    for (int i = 0; i < pa.length; i++) {
      for (Annotation annotations : pa[i]) {
        if (annotations instanceof RequestParam) {
          String paramName = ((RequestParam)annotations).value();
          if (!"".equals(paramName)) {
            paramIndexMapping.put(paramName, i);
          }
        }
      }
    }
    
    Class<?>[] parameterTypes = method.getParameterTypes();
    for (int i = 0; i < parameterTypes.length; i++) {
      Class<?> type = parameterTypes[i];
      if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
        paramIndexMapping.put(type.getName(), i);
      }
    }
  }
  

  
  
  

}
