package com.web.hackercode.controller;

import com.google.gson.JsonObject;
import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.dao.TestDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public ModelAndView logout(HttpServletRequest req) {
    req.getSession().setAttribute("isLoggedIn", false);
    req.getSession().setAttribute("user", null);
    // req.getSession().invalidate();
    RedirectView home = new RedirectView("/");
    return new ModelAndView(home);
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  @ResponseBody
  public String signup(
    @ModelAttribute("register") Register user,
    HttpServletRequest req
  ) {
    // for sign up
    JsonObject obj = new JsonObject();
    obj.addProperty("error", false);

    req.getSession().setAttribute("isLoggedIn", false);
    UserDAO userdao = ctx.getBean(UserDAO.class);

    // check if there is user with same name password
    int i = userdao.getUserCountWithEmail(user.getEmail());
    if (i > 0) {
      // List < Program > programs = testDao.getAllPrograms();
      obj.addProperty("error", true);
      obj.addProperty(
        "error_txt",
        "Oops! Seems like this E-Mail is already taken. Please Try Again."
      );
      return obj.toString();
    }

    // add user to DB
    User u = userdao.saveUserViaRequest(req, user);
    req.getSession().setAttribute("isLoggedIn", true);
    req.getSession().setAttribute("user", u);

    String courseCode = req.getParameter("course");
    if (courseCode != null) {
      obj.addProperty("view", "/profile?course=" + courseCode);
    } else {
      obj.addProperty("view", "/profile");
    }

    return obj.toString();
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(
    @RequestParam String username,
    @RequestParam String password,
    HttpServletRequest req
  ) {
    req.getSession().setAttribute("isLoggedIn", false);
    UserDAO userdao = ctx.getBean(UserDAO.class);
    JsonObject obj = new JsonObject();

    obj.addProperty("isUser", false);

    if (userdao.isUser(username, password, req)) {
      User user = userdao.getUser(username, req);
      req.getSession().setAttribute("user", user);
      System.out.println(user.toString());
      obj.addProperty("isUser", true);
      if (user.isAdmin()) {
        // get details for admin and pass the details to the model.
        obj.addProperty("view", "dashboard");
        // view = new RedirectView("dashboard", true);
      } else if (user.isDrafter()) {
        // article uploader.
        obj.addProperty("view", "user/drafter");
        // view = new RedirectView("");
      } else {
        String courseCode = req.getParameter("course");
        if (courseCode == null) {
          obj.addProperty("view", "profile");
          // view = new RedirectView("profile" , true);
        } else {
          obj.addProperty("view", "profile?course=" + courseCode);
          // view = new RedirectView("profile?course=" + courseCode, true);
        }
      }

      req.getSession().setAttribute("isLoggedIn", true);
      // redirect user to appropriate screen.

    }

    return obj.toString();
  }
}
