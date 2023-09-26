package com.Springboot.example.service;

import com.Springboot.example.model.DatabaseInfo;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceProvider {
    private Map<String, DataSource> dataSources = new HashMap<>();

    public DataSource getDataSource(DatabaseInfo databaseInfo) {
        String key = databaseInfo.system + "-" + databaseInfo.url + "-" + databaseInfo.port + "-" + databaseInfo.name;
        DataSource dataSource = dataSources.get(key);
        if (dataSource == null) {
            dataSource = createDataSource(databaseInfo);
            dataSources.put(key, dataSource);
        }
        return dataSource;
    }

    private DataSource createDataSource(DatabaseInfo databaseInfo) {
        String jdbc = "";
        switch (databaseInfo.system) {
            case "oracle":
                jdbc = "jdbc:%s:thin:@%s:%d/%s";
            case "sqlserver":
                jdbc = "jdbc:%s://%s:%d;databaseName=%s";
            default:
                jdbc = "jdbc:%s://%s:%d/%s";
        }
        String url = String.format(jdbc, databaseInfo.system, databaseInfo.url, databaseInfo.getPort(), databaseInfo.getName());
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(databaseInfo.getUsername());
        dataSource.setPassword(databaseInfo.getPassword());
        return dataSource;
    }
}
