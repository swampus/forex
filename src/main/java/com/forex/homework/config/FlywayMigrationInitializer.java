package com.forex.homework.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Flyway flyway;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        flyway.migrate();
    }
}
