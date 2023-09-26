package com.Springboot.example.service;

import com.Springboot.example.model.DataSourceContextHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private Map<Object, Object> targetDataSources;
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.targetDataSources = targetDataSources;
    }
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (targetDataSources.isEmpty()) {
            throw new IllegalArgumentException("No target data sources were set");
        }
    }
    public void addDataSource(String name, String driverClassName, String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        DataSource dataSource = new HikariDataSource(config);
        targetDataSources.put(name, dataSource);
        setTargetDataSources(targetDataSources);
    }

    public void removeDataSource(String name) {
        targetDataSources.remove(name);
        setTargetDataSources(targetDataSources);
    }
}
