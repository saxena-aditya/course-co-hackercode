package com.web.hackercode.controller;

import com.google.gson.JsonObject;
import com.web.hackercode.dao.TestDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

  @Autowired
  ServletContext context;

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
  Utility utils = new Utility();

  @RequestMapping(value = "/save-profile-pic", method = RequestMethod.POST)
  @ResponseBody
  public boolean saveProfilePic(
    HttpServletRequest req,
    @RequestParam String fileName
  ) {
    String relativeWebPath = "/resources/ff-ideas/img";
    String absoluteFilePath = context.getRealPath(relativeWebPath);

    UserDAO userDao = ctx.getBean(UserDAO.class);
    User user = (User) req.getSession().getAttribute("user");

    if (fileName.length() != 0) {
      boolean x = userDao.savePicture(user, fileName);
      System.out.println("after request");
      if (x) {
        req
          .getSession()
          .setAttribute("user", userDao.getUser(user.getUsername(), req));
        return true;
      }
    }
    return false;
  }

  @RequestMapping(
    value = "/password-reset/{resetKey}",
    method = RequestMethod.GET
  )
  public ModelAndView resetPasswordModel(@PathVariable String resetKey) {
    UserDAO udao = ctx.getBean(UserDAO.class);
    boolean isGoodKey = false;
    if (udao.isGoodKey(resetKey)) {
      isGoodKey = true;
      udao.dropKey(resetKey);
    }
    return new ModelAndView("reset-password")
      .addObject("isGoodKey", isGoodKey)
      .addObject("resetKey", resetKey);
  }

  @RequestMapping(
    value = "/user/api/update-password",
    method = RequestMethod.POST
  )
  @ResponseBody
  public String resetPassword(
    @RequestParam String password,
    @RequestParam String resetKey
  ) {
    UserDAO udao = ctx.getBean(UserDAO.class);
    JsonObject obj = new JsonObject();
    obj.addProperty("isDone", false);
    if (udao.updatePassword(password, resetKey)) {
      obj.addProperty("isDone", true);
    }

    return obj.toString();
  }
}
