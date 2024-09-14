package com.web.hackercode.controller;

import com.google.gson.JsonObject;
import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.constants.MailTemplate;
import com.web.hackercode.dao.ArticleDAO;
import com.web.hackercode.dao.MailerDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.sql.Date;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

  @Autowired
  private JavaMailSender mailSenderObj;

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
  Utility utils = new Utility();

  @RequestMapping(
    value = "/user/api/reset-password",
    method = RequestMethod.POST
  )
  @ResponseBody
  public String resetPasswordMailer(
    HttpServletRequest req,
    @RequestParam final String email
  ) {
    final String subject = "HackerCode | Password Reset Link";
    final String resetLink = utils.getMd5(
      email + utils.getMd5(utils.randomAlphaNumeric(10))
    );
    final String message = "https://hackercode.in/password-reset/" + resetLink;

    UserDAO udao = ctx.getBean(UserDAO.class);
    MailerDAO mail = ctx.getBean(MailerDAO.class);
    JsonObject obj = new JsonObject();

    obj.addProperty("isUser", false);
    obj.addProperty("isMailSent", false);

    User user = udao.getUser(email);

    if (user != null) {
      obj.addProperty("isUser", true);

      // save md5 hash to database to compare it later with incoming request.
      if (udao.saveResetLink(user.getUsername(), resetLink)) {
        if (mail.sendMail(mailSenderObj, subject, email, message)) {
          obj.addProperty("isMailSent", true);
        }
      }
    }

    return obj.toString();
  }

  @RequestMapping(
    value = "/user/api/send-mail-article-action",
    method = RequestMethod.POST
  )
  @ResponseBody
  public String sendArticleApprovedMail(
    HttpServletRequest req,
    @RequestParam final String emailTo,
    @RequestParam String articleName,
    @RequestParam boolean approved
  ) {
    String subject = null;
    String message = null;

    JsonObject obj = new JsonObject();
    obj.addProperty("isLoggedIn", false);
    obj.addProperty("isMailSent", false);
    obj.addProperty("error_text", "");

    if (utils.isUserAuthenticated(req)) {
      obj.addProperty("isLoggedIn", true);
      User user = (User) req.getSession().getAttribute("user");
      MailerDAO mail = ctx.getBean(MailerDAO.class);

      if (approved) {
        subject = "HackerCode | Article " + articleName + " Approved";
        message = MailTemplate.ARTICLE_ACTION_APPROVE;
      } else {
        subject = "HackerCode | Article " + articleName + " Disapproved";
        message = MailTemplate.ARTICLE_ACTION_DISAPPROVE;
      }

      final String subject_final = subject;
      final String message_final = message;

      if (mail.sendMail(mailSenderObj, subject_final, emailTo, message_final)) {
        obj.addProperty("isMailSent", true);
      }
    }

    return obj.toString();
  }
}
