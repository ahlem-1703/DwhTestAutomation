package com.Springboot.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TriggerInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;
    @CreatedDate
    private LocalDateTime latestRun;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "taskScheduler_id",nullable = false)
    private TaskScheduler taskScheduler;

    public TriggerInformation() {
    }

    public TriggerInformation(Status status, TaskScheduler taskScheduler) {
        this.status = status;
        this.taskScheduler = taskScheduler;
    }

    public TriggerInformation(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getLatestRun() {
        return latestRun;
    }

    public void setLatestRun(LocalDateTime latestRun) {
        this.latestRun = latestRun;
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public String toString() {
        return "TriggerInformation{" +
                "id=" + id +
                ", status=" + status +
                ", latestRun=" + latestRun +
                '}';
    }
}
