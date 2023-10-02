package com.Springboot.example.repository;

import com.Springboot.example.model.TaskScheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSchedulerRepository extends JpaRepository<TaskScheduler,Long> {

    boolean existsTaskSchedulerByScheduleName(String scheduleName);
}
