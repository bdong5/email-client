package com.bdong5.emailclient;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(final String username, final String password, String to, String subject, String text) throws MessagingException {

        Properties properties = getProperties();

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(session.getStore().getURLName().getUsername()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Email failed to send");
            e.printStackTrace(); // print error
        }
    }



    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true"); // enable smtp authentication
        properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS secure comm.
        properties.put("mail.smtp.host", "smtp.gmail.com"); // setup gmail host server
        properties.put("mail.smtp.port", "587"); // set port number as string
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // setup ssl trust to gmail
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Adjust version as necessary
        return properties;
    }


    public static void main(String[] args) throws MessagingException {

        for(int i =0; i<100; i++){
            String username = "username";
            String password = "password";
            String to = "to";
            String subject = "subject";
            String text = "text";
            sendEmail(username, password, to, subject, text);
        }


    }
}
