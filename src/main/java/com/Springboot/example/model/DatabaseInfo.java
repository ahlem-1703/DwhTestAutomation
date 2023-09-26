package com.Springboot.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseInfo {
    public String name;
    public String username;
    public String password;
    public int port;
    public String url;
    public String system;
}
