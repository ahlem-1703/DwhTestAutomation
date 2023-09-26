package com.Springboot.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("emailSenderService")

public class EmailSenderService {



  private JavaMailSender javaMailSender;



  @Autowired
  public EmailSenderService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  

  @Async
  public void sendEmail(SimpleMailMessage email) {
	  javaMailSender.send(email);
  }
  public void sendEmailWithCSVAttachment(String[] to, String subject, String text, String csvFilePath) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text,true);

      FileSystemResource file = new FileSystemResource(new File(csvFilePath));
      helper.addAttachment("VueDetaille.csv", file);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      // Handle the exception appropriately
      e.printStackTrace();
    }
  }

}