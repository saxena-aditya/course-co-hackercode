package com.web.hackercode.controller;

import com.amazonaws.services.support.model.Category;
import com.google.gson.JsonObject;
import com.web.hackercode.dao.ArticleDAO;
import com.web.hackercode.dao.CategoryDAO;
import com.web.hackercode.dao.CourseDAO;
import com.web.hackercode.dao.InternshipDAO;
import com.web.hackercode.dao.ReportsDAO;
import com.web.hackercode.dao.TestDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.InternshipForm;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HomeController {

  @Autowired
  ServletContext context;

  String message = "Welcome to Spring MVC!";
  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
  Utility utils = new Utility();

  @RequestMapping("/hello")
  public ModelAndView showMessage(
    @RequestParam(
      value = "name",
      required = false,
      defaultValue = "World"
    ) String name
  ) {
    System.out.println("in controller");

    ModelAndView mv = new ModelAndView("helloworld");
    mv.addObject("message", message);
    mv.addObject("name", name);
    return mv;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView showIndex(HttpServletRequest req) {
    System.out.println("<text>google<text/>".replaceAll("<", "&gt;"));
    String url = "";
    ReportsDAO rdao = ctx.getBean(ReportsDAO.class);
    CourseDAO cdao = ctx.getBean(CourseDAO.class);
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);

    // increment the visitor counter
    int count = rdao.incrementCounter();
    int doLogin = 1;

    if (req.getSession() == null) {
      doLogin = 1;
      System.out.println("Session Null");
    } else {
      System.out.println(req.getSession());
      Enumeration<String> x = req.getSession().getAttributeNames();
      while (x.hasMoreElements()) {
        System.out.println(x.nextElement());
      }

      System.out.println("Session Not Null");
      if (utils.isUserAuthenticated(req)) {
        doLogin = 0;
        User user = (User) req.getSession().getAttribute("user");
        System.out.println(user.toString());
        if (user.isAdmin()) {
          url = "dashboard";
        } else if (user.isDrafter()) {
          url = "user/drafter";
        } else {
          url = "profile";
        }
      }
    }

    return new ModelAndView("index-home")
      .addObject("courses", cdao.getEntityCourses(req))
      .addObject("doLogin", doLogin)
      .addObject("visitorCount", count)
      .addObject("url", url)
      .addObject("nav", catdao.getNavJSON());
  }

  @RequestMapping(value = "/new-admin-login", method = RequestMethod.GET)
  public ModelAndView showAdminLogin(HttpServletRequest req) {
    return new ModelAndView("index");
  }

  @RequestMapping(value = "/resources", method = RequestMethod.GET)
  public ModelAndView showResources(HttpServletRequest req) {
    String url = "";

    CourseDAO cdao = ctx.getBean(CourseDAO.class);
    ReportsDAO rdao = ctx.getBean(ReportsDAO.class);

    rdao.incrementCounter();

    int doLogin = 1;

    if (req.getSession() == null) {
      doLogin = 1;
      System.out.println("Session Null");
    } else {
      System.out.println(req.getSession());
      Enumeration<String> x = req.getSession().getAttributeNames();
      while (x.hasMoreElements()) {
        System.out.println(x.nextElement());
      }

      System.out.println("Session Not Null");

      if (utils.isUserAuthenticated(req)) {
        doLogin = 0;

        User user = (User) req.getSession().getAttribute("user");

        if (user.isAdmin()) {
          url = "dashboard";
        } else if (user.isDrafter()) {
          url = "user/drafter";
        } else {
          url = "profile";
        }
      }
    }

    return new ModelAndView("resources")
      .addObject("res", cdao.getResources())
      .addObject("doLogin", doLogin)
      .addObject("url", url);
  }

  @RequestMapping(
    value = "/user/apply-internship/api/save",
    method = RequestMethod.POST
  )
  @ResponseBody
  public String applyForInternship(
    @ModelAttribute("IntenshipForm") InternshipForm itn
  ) {
    JsonObject obj = new JsonObject();

    obj.addProperty("error", true);
    InternshipDAO idao = ctx.getBean(InternshipDAO.class);

    if (idao.applyForInternship(itn)) {
      obj.addProperty("error", false);
    }

    return obj.toString();
  }
}
