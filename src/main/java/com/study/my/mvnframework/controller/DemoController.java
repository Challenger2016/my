package com.study.my.mvnframework.controller;

import com.mvnframework.annotation.Autowired;
import com.mvnframework.annotation.Controller;
import com.mvnframework.annotation.RequestMapping;
import com.mvnframework.annotation.RequestParam;
import com.mvnframework.service.DemoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("demo")
public class DemoController {

  @Autowired
  private DemoService demoService;
  
  @RequestMapping("hello")
  public void hello(@RequestParam("name") String name, HttpServletResponse response) {
    String result = demoService.hello(name);
    
    try {
      response.getWriter().write(result);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @RequestMapping("hello2")
  public String hello2(@RequestParam("name") String name) {
    return demoService.hello(name);
  }
}
