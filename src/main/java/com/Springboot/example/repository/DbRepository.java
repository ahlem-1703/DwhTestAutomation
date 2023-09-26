package com.Springboot.example.repository;


import com.Springboot.example.model.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbRepository extends JpaRepository<Database, Long> {

    Database findDatabaseByNameAndSystem(String name,String system);
    List<Database> findDatabaseBySystem(String system);

    Database findDatabaseById(long id);

	
}
