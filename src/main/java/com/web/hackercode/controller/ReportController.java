package com.web.hackercode.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.hackercode.dao.CourseDAO;
import com.web.hackercode.dao.ReportsDAO;
import com.web.hackercode.dao.TestDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.Course;
import com.web.hackercode.structures.FinishedTest;
import com.web.hackercode.structures.Question;
import com.web.hackercode.structures.Test;
import com.web.hackercode.structures.User;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");

  @RequestMapping(value = "/reports/{testCode}", method = RequestMethod.GET)
  public ModelAndView getReport(
    @PathVariable String testCode,
    @RequestParam String username
  ) {
    System.out.println(testCode + " | " + username);
    TestDAO tdao = ctx.getBean(TestDAO.class);
    CourseDAO cdao = ctx.getBean(CourseDAO.class);
    UserDAO userdao = ctx.getBean(UserDAO.class);
    ReportsDAO rdao = ctx.getBean(ReportsDAO.class);

    User user = userdao.getUser(username);
    Test test = tdao.getTest(testCode);

    if ((test != null) & (user != null)) {
      FinishedTest ft = rdao.getFinishedTestData(
        test.getTestId(),
        Integer.toString(user.getU_id())
      );

      if (ft != null) {
        List<Question> testQuestions = tdao.getQuestionsForTest(
          test.getTestId()
        );
        List<Question> reportQuestions = rdao.getReportCompatibleQuestions(
          testQuestions,
          ft.getAnsData()
        );
        Course course = cdao.getCourse(test.getCourseCode());
        int answeredQuestions = 0;

        JsonObject obj = new JsonObject();

        for (Question tq : reportQuestions) {
          System.out.println(
            tq.getUserAns() +
            " | " +
            tq.getQuestionAns() +
            " | " +
            tq.getQuestionOptions() +
            " | " +
            tq.isAnswered()
          );
          if (tq.isAnswered()) answeredQuestions++;

          if (obj.has(tq.getQuestionSet())) {
            System.out.println("obj: " + obj.toString());
            JsonObject jd = (JsonObject) obj.get(tq.getQuestionSet());
            System.out.println(tq.getQuestionSet());
            System.out.println(jd.toString());
            jd.addProperty(
              "max",
              jd.get("max").getAsFloat() + tq.getQuestionMaxMarks()
            );
            jd.addProperty(
              "question_num",
              jd.get("question_num").getAsInt() + 1
            );

            if (tq.isAnswered()) {
              jd.addProperty(
                "question_answered",
                jd.get("question_answered").getAsInt() + 1
              );
              if (tq.getQuestionAns().equalsIgnoreCase(tq.getUserAns())) {
                jd.addProperty(
                  "user",
                  jd.get("user").getAsFloat() + tq.getQuestionMaxMarks()
                );
              } else {
                jd.addProperty(
                  "user",
                  jd.get("user").getAsFloat() - tq.getQuestionNegMarks()
                );
              }
            }

            obj.add(tq.getQuestionSet(), jd);
          } else {
            JsonObject jr = new JsonObject();
            jr.addProperty("max", tq.getQuestionMaxMarks());
            jr.addProperty("question_num", 1);
            jr.addProperty("user", 0);
            if (tq.isAnswered()) {
              jr.addProperty("question_answered", 1);
              if (
                tq.getQuestionAns().equalsIgnoreCase(tq.getUserAns())
              ) jr.addProperty("user", tq.getQuestionMaxMarks());
              else jr.addProperty("user", -tq.getQuestionNegMarks());
            } else {
              jr.addProperty("question_answered", 0);
            }

            obj.add(tq.getQuestionSet(), jr);
          }
        }

        System.out.println(obj.toString());

        return new ModelAndView("report")
          .addObject("user", user)
          .addObject("test", test)
          .addObject("course", course)
          .addObject("ft", ft)
          .addObject("aq", answeredQuestions)
          .addObject("str", obj.toString())
          .addObject("questions", reportQuestions);
      } else {
        return null;
      }
    }
    return null;
  }
}
