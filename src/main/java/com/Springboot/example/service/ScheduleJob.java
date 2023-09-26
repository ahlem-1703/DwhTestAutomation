package com.Springboot.example.service;

import com.Springboot.example.helper.LoadDataHelper;
import com.Springboot.example.model.*;
import com.Springboot.example.repository.TaskSchedulerRepository;
import com.Springboot.example.repository.TriggerInformationRepository;
import com.google.gson.Gson;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleJob extends QuartzJobBean {

    @Autowired
    TaskSchedulerRepository taskSchedulerRepository;
    @Autowired
    private LoadDataHelper loadDataHelper;
    @Autowired
    private VueDetailSevice vueDetailSevice;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    TriggerInformationRepository triggerInformationRepository;

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String kpi = dataMap.getString("kpi");
        String dateDeb = dataMap.getString("dateDeb");
        String dateFin = dataMap.getString("dateFin");
        Long database1 = dataMap.getLong("database1");
        Long database2 = dataMap.getLong("database2");
        Long idScheduler = dataMap.getLong("idScheduler");
        String scheduleName = dataMap.getString("scheduleName");
        String[] emails = {"ahlem.kthiri@esprit.tn","mz.sahnoun@gmail.com","houcemsalem@gmail.com"};
        String[] kpis = new Gson().fromJson(kpi, String[].class);
        // Get the Trigger associated with this job
        Trigger trigger = jobExecutionContext.getTrigger();
        TaskScheduler taskScheduler = taskSchedulerRepository.findById(idScheduler).get();
        TriggerInformation triggerInformation = new TriggerInformation(taskScheduler);

        System.out.println("next trigger : " + trigger.getNextFireTime());

        try {

            String[] columnNames = {"date", "code_requete", "val_kpi1", "val_kpi2", "name_kpi", "val_dim", "gap", "GAP_par_100", "nbreRecordNotOk"};
            Map<String, List<?>> listMap = loadDataHelper.loadAllData(kpis,dateDeb,dateFin,database1,database2);
            vueDetailSevice.writeObjectsToCsv((List<Vue_Detaillé>) listMap.get("vueDetaille").get(0),columnNames, (List<Vue_Globale>) listMap.get("vueGlobale").get(0),emails);
            triggerInformation.setStatus(Status.Succeeded);

        } catch (Exception e) {
            triggerInformation.setStatus(Status.Failed);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("the execution of " + scheduleName + " has failed");
            simpleMailMessage.setText(" this is the error message :\n" + e.getMessage());
            simpleMailMessage.setTo(emails);
            emailSenderService.sendEmail(simpleMailMessage);
        }
        finally {
            triggerInformationRepository.save(triggerInformation);
        }
    }
}
