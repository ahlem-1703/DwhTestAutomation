package com.Springboot.example.service;

import com.Springboot.example.model.Vue_Detaillé;
import com.Springboot.example.model.Vue_Globale;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class VueDetailServiceImp implements VueDetailSevice {

    @PersistenceContext
    EntityManager em ;
    @Autowired
    private EmailSenderService emailSenderService;
    @Transactional
    @Override
    public void InsertOrUpdate(Vue_Detaillé r) {
        em.createNativeQuery("INSERT into vue_detaille" +
                " (code_requete,date,gap,name_kpi,val_kpi1,val_kpi2) " +
                "VALUES\n" +
                "(?,?,?,?,?,?)\n" +
                "ON CONFLICT  (date,code_requete,groupement)\n" +
                "DO update set val_kpi1=excluded.val_kpi1 , val_kpi2=excluded.val_kpi2 , gap=excluded.gap ")
                .setParameter(2,r.date)
                .setParameter(1,r.code_requete)
                .setParameter(3,r.gap)
                .setParameter(5,r.name_kpi)
        
                .setParameter(6,r.val_kpi1)
                .setParameter(7,r.val_kpi2)
                .executeUpdate()   ;
    }

    @Override
    public void writeObjectsToCsv(List<Vue_Detaillé> objects, String[] ColumnNames, List<Vue_Globale> vueGlobaleList,String[] to) throws IOException {
        String filePath = System.getProperty("user.home") + "\\Bureau\\output.csv";
        char delimiter = ';';
        try (CSVWriter writer = new CSVWriter(
                new FileWriter(filePath), ';', '"', '"', "\n")) {
            // Write header
            String[] header = ColumnNames; // Replace with your object fields
            writer.writeNext(header);

            // Write data rows
            for (Vue_Detaillé object : objects) {
                String[] data = {object.date, String.valueOf(object.code_requete),
                        String.valueOf(object.val_kpi1), String.valueOf(object.val_kpi2),
                        object.name_kpi,object.val_dim,
                        String.valueOf(object.gap),object.getGAP_par_100(),
                        String.valueOf(object.getNbreRecordNotOk())}; // Replace with your object fields
                writer.writeNext(data);
            }
            StringBuilder htmlContent = mailTemplate(vueGlobaleList);

            String subject = "Email with CSV Attachment";
            String text = "This email contains the global and detailed vue result.\n" + htmlContent.toString();

            emailSenderService.sendEmailWithCSVAttachment(to, subject, text, filePath);
        }
    }
    public StringBuilder mailTemplate(List<Vue_Globale> vueGlobaleList){
        // Create the HTML content with a table
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><body>");
        htmlContent.append("<h2>").append("Vue Globale").append("</h2>");
        htmlContent.append("<p>").append("").append("</p>");

        // Add the table
        htmlContent.append("<table border='1'>");
        htmlContent.append("<tr>")
        .append("<th>Start Date</th>")
        .append("<th>End Date</th>")
        .append("<th >Query Code</th>")
        .append("<th>KPI Name</th>")
        .append("<th>Total Kpi in DB1</th>")
        .append("<th>Total Kpi in DB2</th>")
        .append("<th>GAP</th>")
        .append("<th>GAP %</th>")
        .append("<th>Data Quality</th>")
        .append("<th>Acceptance</th>")
        .append("<th>Number Records OK</th>")
        .append("<th>Number Records Not OK</th>")
        .append("<th>Execution Time</th>")
        .append("<th>Execution Date</th>")
        .append("</tr>");
        for(Vue_Globale vueGlobale:vueGlobaleList){
            htmlContent.append("<tr>");
            htmlContent.append("<td>"+vueGlobale.getDateDeb()+"</td>")
            .append("<td>"+vueGlobale.getDateFin()+"</td>")
            .append("<td>"+vueGlobale.getCode_requete()+"</td>")
            .append("<td>"+vueGlobale.getName_kpi()+"</td>")
            .append("<td>"+vueGlobale.getVal_kpi1()+"</td>")
            .append("<td>"+vueGlobale.getVal_kpi2()+"</td>")
            .append("<td>"+vueGlobale.getGap()+"</td>")
            .append("<td>"+vueGlobale.getGAP_par_100()+"</td>")
            .append("<td>"+vueGlobale.getDataQualite()+"</td>")
            .append("<td>"+vueGlobale.getAcceptation()+"</td>")
            .append("<td>"+vueGlobale.getNbreRecordOk()+"</td>")
            .append("<td>"+vueGlobale.getNbreRecordNotOk()+"</td>")
            .append("<td>"+vueGlobale.getTempsExec()+"</td>")
            .append("<td>"+vueGlobale.getDateExec()+"</td>");
            htmlContent.append("</tr>");
        }
        htmlContent.append("</table>");
        htmlContent.append("</body></html>");
        return htmlContent;
    }
}
