package com.report.sendPdfViaMail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class sendMail {

    public void sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String destination) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);
        
        
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            msg.setSubject(subject);

            //3) create MimeBodyPart object and set your message text     
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(message);

            //4) create new MimeBodyPart object and set DataHandler object to this object      
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = "C:\\ssadvt_repository\\SmartMeterSurvey\\Report\\sm.pdf";//change accordingly  
            System.err.println("destination---"+destination);
            filename=destination;
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            //5) create Multipart object and add MimeBodyPart objects to this object      
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object  
            msg.setContent(multipart);

            //7) send message  
            Transport.send(msg);

            System.out.println("message sent....");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        
        
        

        // creates a new e-mail message
//        Message msg = new MimeMessage(session);
//
//        msg.setFrom(new InternetAddress(userName));
//        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
//        msg.setRecipients(Message.RecipientType.TO, toAddresses);
//        msg.setSubject(subject);
//        msg.setSentDate(new Date());
//        // set plain text message
//        msg.setText(message);
//
//        // sends the e-mail
//        Transport.send(msg);





    }

    /**
     * Test the send e-mail method
     *
     */
    public String sentMail(String destination,String mail_id) {
        // SMTP server information
        
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "smartmeter.apogee@gmail.com";
        String password = "jpss1277";

        // outgoing message information
      //  String mailTo = "komal.apogee@gmail.com";
         String mailTo=mail_id;
        String subject = "Report";
        String message = "Hello Sir/Mam, Please find attached pdf file for water data report....";

        sendMail mailer = new sendMail();

        try {
            mailer.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message, destination);
            System.out.println("Email sent.");
            

            
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
        return "strrrrrr";
    }
}
