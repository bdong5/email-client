package com.bdong5.emailclient;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;

public class EmailReceiver {

    public static boolean login(final String username, final String password) {
        Properties properties = getProperties();
        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Email> receiveEmails(final String username, final String password) {
        Properties properties = getProperties();
        ArrayList<Email> emails = new ArrayList<>();

        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] inbox = emailFolder.getMessages();
            System.out.printf("%d new emails%n", inbox.length);

            for (Message email : inbox) {
                String content = getTextFromMessage(email);
                emails.add(new Email(email.getFrom(), email.getSubject(), content));
            }
            emailFolder.close(false);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return emails;
    }

    private static String getTextFromMessage(Message message) throws MessagingException {
        try {
            if (message.isMimeType("text/plain")) {
                return message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                String result = "";
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                int count = mimeMultipart.getCount();
                for (int i = 0; i < count; i++) {
                    BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                    if (bodyPart.isMimeType("text/plain")) {
                        result = result + "\n" + bodyPart.getContent();
                        break; // without break, same text appears twice in my tests
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.ssl.protocols", "TLSv1.2");
        return properties;
    }
}
