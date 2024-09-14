package com.web.hackercode.controller;

import com.google.gson.JsonObject;
import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.dao.InternshipDAO;
import com.web.hackercode.dao.InternshipDAO;
import com.web.hackercode.dao.MailerDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.InternshipForm;
import com.web.hackercode.utility.Utility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InternshipController {

  @Autowired
  ServletContext context;

  @Autowired
  private JavaMailSender mailSenderObj;

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
  Utility utils = new Utility();

  @RequestMapping(value = "/admin/intern-letters", method = RequestMethod.GET)
  public ModelAndView showInternshipLetters() {
    InternshipDAO idao = ctx.getBean(InternshipDAO.class);
    return new ModelAndView("internship/internship").addObject(
      "app",
      idao.getInternshipApplications()
    );
  }

  @RequestMapping(
    value = "/admin/api/mark-internship-handled",
    method = RequestMethod.POST
  )
  @ResponseBody
  public String markIntern(HttpServletRequest req, @RequestParam String id) {
    InternshipDAO idao = ctx.getBean(InternshipDAO.class);
    UserDAO udao = ctx.getBean(UserDAO.class);
    MailerDAO mdao = ctx.getBean(MailerDAO.class);
    JsonObject obj = new JsonObject();

    obj.addProperty("error", false);
    // mark success
    InternshipForm iform = idao.getInternshipApplication(id);
    if (iform != null) {
      String internUsername = utils.randomAlphaNumeric(6);

      String subject = "HackerCode | Content Developer Internship";
      String message =
        "<h4>Hi " +
        iform.getFirstName() +
        ",</h4><h4>Thank you for your interest in Internship at HackerCode. Below are the login credentials for your article writing account. Please read the instructions carefully after login at https://hackercode.in/</h4>" +
        "<p> Your Drafter Account Credentials are: <br> Username: " +
        internUsername +
        "<br>" +
        "Password: " +
        iform.getPhnNum() +
        "<br><br>After 10 of your articles are published, you are entitled to get an Internship certificate from HackerCode. For any exceptional article you will get PayTM cash as well.<br><br>For any questions or doubts you can always write to us on this same email id.<br><br> Best of Luck!<br>Team HackerCode";

      int users = udao.getUserCountWithEmail(internUsername);

      if (users == 0) {
        idao.addDrafterAccount(iform, internUsername);

        if (!idao.markInternshipLead(id)) {
          obj.addProperty("error", true);
          obj.addProperty(
            "error_text",
            "Could not mark internship letter with ID: " + id
          );
        } else {
          if (
            !mdao.sendMail(mailSenderObj, subject, iform.getEmail(), message)
          ) {
            obj.addProperty("error", true);
            obj.addProperty(
              "error_text",
              "Could not send Credential Mail to " + iform.getEmail()
            );
          }
        }
      } else {
        obj.addProperty("error", true);
        obj.addProperty(
          "error_text",
          "A Drafter Account on " + iform.getEmail() + " is already present"
        );
      }
    } else {
      obj.addProperty("error", true);
      obj.addProperty(
        "error_text",
        "Could not get internship letter with ID: " + id
      );
    }

    return obj.toString();
  }
}
