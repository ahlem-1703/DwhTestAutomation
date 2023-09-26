package com.Springboot.example.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Component
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {
    private final ApplicationContext applicationContext;

    @Autowired
    public AutowiringSpringBeanJobFactory(@Lazy ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
        return job;
    }
}
