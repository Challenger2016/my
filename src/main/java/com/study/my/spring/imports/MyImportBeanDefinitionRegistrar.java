package com.study.my.spring.imports;

import com.study.my.spring.bean.While;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * Register bean definitions as necessary based on the given annotation metadata of
     * the importing {@code @Configuration} class.
     * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
     * registered here, due to lifecycle constraints related to {@code @Configuration}
     * class processing.
     *
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry               current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean b = registry.containsBeanDefinition("com.study.my.spring.bean.Color");
        if (b){
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(While.class);
            registry.registerBeanDefinition("While", rootBeanDefinition);
            registry.removeBeanDefinition("com.study.my.spring.bean.Yellow");
        }
    }
}
