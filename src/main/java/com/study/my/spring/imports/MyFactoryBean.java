package com.study.my.spring.imports;

import com.study.my.spring.bean.Black;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Black> {

    @Override
    public Black getObject() throws Exception {
        System.out.println("MyFactoryBean.getObject");
        return new Black();
    }


    @Override
    public Class<?> getObjectType() {
        return Black.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
