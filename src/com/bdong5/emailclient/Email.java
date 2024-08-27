package com.bdong5.emailclient;

import javax.mail.Address;
import java.util.Arrays;

public class Email {
    String from;
    String subject;
    String date;

    public Email(Address[] from, String subject, String text) {
        this.from = Arrays.toString(from);
        this.subject = subject;
        this.date = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }
}
