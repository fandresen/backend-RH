package com.fandresena.learn.service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Service;


@Service
public class SendEmailService {
    public static void sendEmail(String to,String subject,String name, String link,String template)throws IOException {
        String from = "eliotscript@gmail.com"; 
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); 
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String username = "eliotscript@gmail.com";
        String password = "xlrr ahaq klsc zkmf";

        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new  PasswordAuthentication(username,password);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            //Read template HTML
            String body = template.replace("{{Name}}",name).replace("{{link}}", link);

            message.setContent(body, "text/html; charset=utf-8");
            
            Transport.send(message);
            System.out.println("Email sent successfully to " + to);

        }
        catch(MessagingException e){
            e.printStackTrace();
        }
    }




}
