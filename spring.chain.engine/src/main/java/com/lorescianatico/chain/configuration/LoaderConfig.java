package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.servicelocator.LoaderFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoaderConfig {

    @Bean("loaderFactory")
    public FactoryBean<Object> serviceLocatorFactoryBean(){
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(LoaderFactory.class);
        return factoryBean;
    }

}
