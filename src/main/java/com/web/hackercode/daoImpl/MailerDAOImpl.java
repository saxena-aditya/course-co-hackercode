package com.web.hackercode.daoImpl;

import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.constants.MailTemplate;
import com.web.hackercode.dao.MailerDAO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class MailerDAOImpl implements MailerDAO {

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate = new JdbcTemplate();

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public boolean sendMail(
    final JavaMailSender mailSender,
    final String subject,
    final String mailTo,
    final String msg
  ) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

      String htmlMsg =
        "<body style='border:2px solid black'>" +
        "Your onetime password for registration is  " +
        "Please use this OTP to complete your new user registration." +
        "OTP is confidential, do not share this  with anyone.</body>";

      message.setContent(msg, "text/html");
      helper.setTo(mailTo);
      helper.setSubject(subject);
      mailSender.send(message);
      return true;
    } catch (MessagingException e) {
      e.printStackTrace();
      return false;
    }
  }
}
