package com.Springboot.example.model;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import java.time.DayOfWeek;

import java.time.LocalDateTime;

import java.time.LocalTime;

import java.util.Date;

import java.util.List;

import java.util.Set;




@Entity

@EntityListeners(AuditingEntityListener.class)

public class TaskScheduler {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @NotNull

    private String scheduleName;

    private TriggerType triggerType;

    @CreatedDate

    private LocalDateTime creationDate;

    private Date endScheduler;



    @OneToMany(mappedBy = "taskScheduler",fetch = FetchType.EAGER)

    private Set<TriggerInformation> triggerInformation;




    public TaskScheduler() {

    }




    public TaskScheduler(String scheduleName, TriggerType triggerType, Date endScheduler) {

        this.scheduleName = scheduleName;

        this.triggerType = triggerType;

        this.endScheduler = endScheduler;

    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {

        this.scheduleName = scheduleName;

    }




    public TriggerType getTriggerType() {

        return triggerType;

    }




    public void setTriggerType(TriggerType triggerType) {

        this.triggerType = triggerType;

    }




    public LocalDateTime getCreationDate() {

        return creationDate;

    }




    public void setCreationDate(LocalDateTime creationDate) {

        this.creationDate = creationDate;

    }




    public Date getEndScheduler() {

        return endScheduler;

    }




    public void setEndScheduler(Date endScheduler) {

        this.endScheduler = endScheduler;

    }




    public Set<TriggerInformation> getTriggerInformation() {

        return triggerInformation;

    }




    public void setTriggerInformation(Set<TriggerInformation> triggerInformation) {

        this.triggerInformation = triggerInformation;

    }




    @Override

    public String toString() {

        return "TaskScheduler{" +

                "id=" + id +

                ", scheduleName='" + scheduleName + '\'' +

                ", triggerType=" + triggerType +

                ", creationDate=" + creationDate +

                ", endScheduler=" + endScheduler +

                ", triggerInformation=" + triggerInformation +

                '}';

    }

}