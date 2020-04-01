package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Mail {

    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> props;

    public Mail() {
    }

    public Mail(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}
