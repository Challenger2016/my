package com.study.my.spring.xml;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public void t(){
        for (int i = 0; i < 1000000000; i++) {
            System.out.println("1");

        }
    }
}
