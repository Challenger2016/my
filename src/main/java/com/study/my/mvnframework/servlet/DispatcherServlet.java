package com.study.my.mvnframework.servlet;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

public class DispatcherServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    this.doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      this.doDispatcher(req, resp);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void doDispatcher(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    if (HandlerMapping.handleMaping.isEmpty()) {
      return;
    }

    Handler handler = this.getHandler(req);
    if (handler == null) {
      resp.getWriter().write("404 Not Found");
    }

    Class<?>[] parameterTypes = handler.method.getParameterTypes();
    Object[] parameterValues = new Object[parameterTypes.length];
    Map<String, String[]> params = req.getParameterMap();
    for (Entry<String, String[]> param : params.entrySet()) {
      String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");

      if (!handler.paramIndexMapping.containsKey(param.getKey())) {
        continue;
      }
      int index = handler.paramIndexMapping.get(param.getKey());
      parameterValues[index] = this.convert(parameterTypes[index], value);
    }

    if (handler.paramIndexMapping.get(HttpServletRequest.class.getName()) != null) {
      int reqIndex = handler.paramIndexMapping.get(HttpServletRequest.class.getName());
      parameterValues[reqIndex] = req;
    }

    if (handler.paramIndexMapping.get(HttpServletResponse.class.getName()) != null) {
      int resIndex = handler.paramIndexMapping.get(HttpServletResponse.class.getName());
      parameterValues[resIndex] = resp;
    }

    Object result = handler.method.invoke(handler.controller, parameterValues);
    if (result != null) {
      resp.getWriter().write(JSON.toJSON(result).toString());
    }

  }

  private Object convert(Class<?> type, String value) {
    if (Integer.class == type) {
      return Integer.valueOf(value);
    }

    return value;
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    String application = config.getInitParameter("contextConfigLocation");
    InitDispatcherServlet initDispatcherServlet = new InitDispatcherServlet();
    initDispatcherServlet.init(application);
    
  }


  public Handler getHandler(HttpServletRequest req) {
    if (HandlerMapping.handleMaping.isEmpty()) {
      return null;
    }
    String url = req.getRequestURI();
    String contextPath = req.getContextPath();
    url = url.replace(contextPath, "").replaceAll("/+", "/");

    for (Handler handler : HandlerMapping.handleMaping) {
      Matcher matcher = handler.pattern.matcher(url);
      if (matcher.matches()) {
        return handler;
      }
    }
    return null;
  }

  

}

