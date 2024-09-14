package com.web.hackercode.controller;

import com.google.gson.JsonObject;
import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.dao.CategoryDAO;
import com.web.hackercode.dao.CourseDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.Register;
import com.web.hackercode.utility.Utility;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
  Utility utils = new Utility();

  @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
  public ModelAndView showEditCategory() {
    CategoryDAO catdao = ctx.getBean(CategoryDAO.class);

    return new ModelAndView("test-admin-dashboard-add-categories").addObject(
      "nav",
      catdao.getNavJSON()
    );
  }

  @RequestMapping(
    value = "/admin/bulk-upload/users",
    method = RequestMethod.POST
  )
  @ResponseBody
  public String bulkUploadUser(@RequestParam("file") MultipartFile file)
    throws IOException {
    UserDAO userdao = ctx.getBean(UserDAO.class);
    CourseDAO coursedao = ctx.getBean(CourseDAO.class);
    Workbook workbook = null;
    JsonObject obj = new JsonObject();
    obj.addProperty("error", false);
    try {
      workbook = WorkbookFactory.create(file.getInputStream());
      System.out.println(workbook.getNumberOfSheets());

      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        Sheet sheet = workbook.getSheetAt(i);
        DataFormatter dm = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
          Row row = rowIterator.next();
          if (row.getRowNum() == 0) continue;
          else {
            Register user = new Register();
            user.setUsername(dm.formatCellValue(row.getCell(0)));
            user.setPassword(utils.getMd5(dm.formatCellValue(row.getCell(1))));

            user.setfName(dm.formatCellValue(row.getCell(2))); // First Name
            user.setlName(dm.formatCellValue(row.getCell(3))); // Second Name
            user.setPhone(dm.formatCellValue(row.getCell(4)));
            user.setInstitute(dm.formatCellValue(row.getCell(5)));
            user.setEmail(dm.formatCellValue(row.getCell(6)));

            if (
              dm
                .formatCellValue(row.getCell(7))
                .toString()
                .equalsIgnoreCase(String.valueOf(HCConstants.ADMIN_ACC))
            ) {
              user.setAdmin(true);
              user.setDrafter(false);
            } else if (
              dm
                .formatCellValue(row.getCell(7))
                .toString()
                .equalsIgnoreCase(String.valueOf(HCConstants.DRAFTER_ACC))
            ) {
              user.setDrafter(true);
              user.setAdmin(false);
            }

            String courses_str = dm.formatCellValue(row.getCell(8));
            String courses[] = courses_str.split(",");
            user.setCourses(courses);

            try {
              int user_count = userdao.getUserCountWithEmail(user.getEmail());
              if (user_count == 0) {
                // now save user in the DB.
                if (userdao.saveUser(user)) {
                  // assign courses to users.
                  // course code verification is not important because course
                  // queries are designed to skip the wrong course code.

                  for (int k = 0; k < courses.length; k++) {
                    if (courses[k].length() > 0) {
                      if (
                        !coursedao.isCourseSubscribedByUser(
                          user.getUsername(),
                          courses[k]
                        )
                      ) {
                        if (
                          !coursedao.addCourseToUser(
                            user.getUsername(),
                            courses[k]
                          )
                        ) {
                          obj.addProperty("error", true);
                          obj.addProperty(
                            "message",
                            "Could not save " +
                            courses[k] +
                            " to username " +
                            user.getUsername()
                          );
                          return obj.toString();
                        }
                      }
                    }
                  }
                } else {
                  // roll back nothing
                }

                System.out.println(user.toString());
              } else {
                System.out.println(
                  "User: " +
                  user.getEmail() +
                  " is Already present, not saving this user."
                );
              }
            } catch (Exception e) {
              obj.addProperty("error", true);
              obj.addProperty(
                "message",
                "Could not save user data in database. Please try again." +
                e.getMessage()
              );
            }
          }
        }
      }

      workbook.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return obj.toString();
  }
}
