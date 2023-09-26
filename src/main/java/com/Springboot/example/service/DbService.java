package com.Springboot.example.service;



import com.Springboot.example.model.Database;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DbService {
	 public List<Database> createOrUpdateDb(Database database);
	 
     public List<Database> getAllDb();

     public void deleteDatabase(Long id);
     
     public List<Database> getOracleDb();
     
     public List<Database> getSqlServerDb();
     
     public List<Database> getPostgresDb();

     public String VerifyConnexion(Database db, HttpSession httpSession);
     
     
}
