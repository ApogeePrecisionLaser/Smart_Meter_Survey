/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Shobha
 */
public class SendMailModel {

    private String host;
    private String port;
    private String user_name;
    private String user_password;
   // private List<String> CcEmailList;
    private List<String> to;
    private List<String> bcc;
    private List<String> cc;
    private String subject;
    private String bodyText;
    private String fileName;    // Fully qualified file name. e.g. "c:/test/abc.pdf".
    private Connection con;

    public SendMailModel(){

    }
     public SendMailModel(Connection connection) {
        this.con = connection;
    }

     public String sendMail() {

        System.out.println("sendMail() - Mail sending process starts here");
        String result = "Mail sent successfully.";
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", user_name);
            props.put("mail.smtp.password", user_password);
            props.put("mail.smtp.port", port);
            //props.put("mail.smtp.ccAddress", CcEmailList);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.transport.protocol", "smpts");
            InternetAddress[] toAddress = null, bccAddress = null, ccAddress = null;
            InternetAddress fromAddress = null;
            try {
                toAddress = new InternetAddress[to.size()];
                for (int i = 0; i < to.size(); i++) {
                    toAddress[i] = new InternetAddress(to.get(i));
                }
                if (bcc != null) {
                    bccAddress = new InternetAddress[bcc.size()];
                    for (int i = 0; i < bcc.size(); i++) {
                        bccAddress[i] = new InternetAddress(bcc.get(i));
                    }
                }
                if (cc != null) {
                    ccAddress = new InternetAddress[cc.size()];
                    for (int i = 0; i < cc.size(); i++) {
                        ccAddress[i] = new InternetAddress(cc.get(i));
                    }
                }
                fromAddress = new InternetAddress(user_name);
            } catch (AddressException ae) {
                result = "Address Exception: " + ae;
            }
            Session session = Session.getDefaultInstance(props, null);
            Message message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.setRecipients(RecipientType.TO, toAddress);
            if (bcc != null) {
                message.setRecipients(RecipientType.BCC, bccAddress);
            }
            if (cc != null) {
                message.setRecipients(RecipientType.CC, ccAddress);
            }

            Multipart body = new MimeMultipart();
            BodyPart bodyPart_text = new MimeBodyPart();

            if (bodyText != null) {
                bodyPart_text.setText(bodyText);
                body.addBodyPart(bodyPart_text);
            } else {
                bodyPart_text.setText("");
                body.addBodyPart(bodyPart_text);
            }
            BodyPart bodyPart_attachment = new MimeBodyPart();
            if (fileName != null && !fileName.equals("")) {
                File attachment = new File(fileName);
                if (attachment.isFile()) {
                    FileDataSource fileDataSource = new FileDataSource(fileName);
                    bodyPart_attachment.setDataHandler(new DataHandler(fileDataSource));
                    bodyPart_attachment.setFileName(attachment.getName());
                    body.addBodyPart(bodyPart_attachment);
                }
            }
            if (subject != null) {
                message.setSubject(subject);
            }
            if ((bodyText != null) || (fileName != null && !fileName.equals(""))) {
                message.setContent(body);
            } else {
                message.setContent(body);
            }
            message.setSentDate(new Date());

            Transport transport = session.getTransport("smtps");
            int numberOfParts = body.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                System.out.println("sendMail() transport process Start");
                MimeBodyPart part = (MimeBodyPart) body.getBodyPart(partCount);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    try {
                        transport.connect(host, user_name, user_password);
                        transport.sendMessage(message, message.getAllRecipients());
                        result = "Mail sent successfully.";
                    } catch (MessagingException e) {
                        System.out.println("sendMail() error -Exception Occur in Sending mail.." + e);
                    }


                } else {
                    result = "Mail Sending failed...";
                    System.out.println("sendMail() ATTACHMENT error -" +result);
                }
            }

            transport.close();
        } catch (MessagingException me) {
            result = "Messaging Exception: " + me;
            System.out.println("sendMail() error -" +result);
        }
        System.out.println("sendMail() - " +result);
        return result;
    }




     public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
