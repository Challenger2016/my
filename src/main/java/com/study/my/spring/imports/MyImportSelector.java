package com.study.my.spring.imports;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.study.my.spring.bean.Blue", "com.study.my.spring.bean.Yellow"};
    }
}
