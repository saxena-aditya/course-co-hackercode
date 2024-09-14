package com.web.hackercode.dao;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailerDAO {
  public boolean sendMail(
    final JavaMailSender mailSenderObj,
    final String subject,
    final String mailTo,
    final String message
  );
}
