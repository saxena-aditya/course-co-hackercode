package com.web.hackercode.controller;

import com.google.gson.*;
import com.web.hackercode.dao.CourseDAO;
import com.web.hackercode.dao.TestDAO;
import com.web.hackercode.dao.TestUtilitiesDAO;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.structures.Course;
import com.web.hackercode.structures.Program;
import com.web.hackercode.structures.ProgramSpecificTests;
import com.web.hackercode.structures.Question;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.Test;
import com.web.hackercode.structures.TestUser;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TestController extends AbstractController {

  @Autowired
  ServletContext context;

  ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
  Utility utils = new Utility();
  User loggedInUser = null;

  @RequestMapping(value = "/file-upload", method = RequestMethod.GET)
  public ModelAndView serverFileUpload(
    HttpServletRequest req,
    HttpServletResponse res
  ) {
    return new ModelAndView("test-admin-panel").addObject(
      "fileUploadURL",
      "/file-process"
    );
  }

  @RequestMapping(value = "/save-test", method = RequestMethod.POST)
  @ResponseBody
  public int saveTest(@ModelAttribute("test") Test test, BindingResult result) {
    TestDAO testDAO = ctx.getBean(TestDAO.class);

    String relativeWebPath = "/resources/file-data";
    String absoluteFilePath = context.getRealPath(relativeWebPath);

    System.out.println(result.getModel().toString());
    if (result.hasErrors()) return 0;
    try {
      boolean testSaved = testDAO.saveTest(test, absoluteFilePath);
      if (testSaved) {
        return 1;
      } else {
        return 0;
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 0;
  }

  @RequestMapping(value = "/admin/save-question", method = RequestMethod.POST)
  @ResponseBody
  public int saveQuestion(
    @ModelAttribute("question") Question q,
    BindingResult result
  ) {
    boolean success = false;
    TestDAO testDao = ctx.getBean(TestDAO.class);

    if (result.hasErrors()) return 0;
    // mode: 0 => Update Question
    // mode: 1 => Insert New Question
    try {
      if (q.isQuestionNew()) {
        // insert mode
        success = testDao.saveQuestion(q, 1);
      } else {
        success = testDao.saveQuestion(q, 0);
      }
      if (success) return 1;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  @RequestMapping(value = "/file-process", method = RequestMethod.POST)
  public ModelAndView fileProcess(ModelAndView model, MultipartFile file)
    throws IOException, InvalidFormatException {
    TestDAO testDAO = ctx.getBean(TestDAO.class);

    String LOCATION = null;
    InputStream in = file.getInputStream();
    File currdir = new File("D:/HackerCode/HackerCode/file-data/");
    String path = currdir.getAbsolutePath();
    LOCATION = path.substring(0, path.length()) +
    "\\" +
    file.getOriginalFilename();
    FileOutputStream f = new FileOutputStream(LOCATION);
    int ch = 0;
    // add try,catch and finally bolcks to copy the file
    while ((ch = in.read()) != -1) {
      f.write(ch);
    }

    System.out.println(LOCATION);
    f.flush();
    f.close();

    Workbook workbook = WorkbookFactory.create(new File(LOCATION));
    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
      Sheet sheet = workbook.getSheetAt(i);
      DataFormatter dm = new DataFormatter();
      Iterator<Row> rowIterator = sheet.rowIterator();
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (row.getRowNum() == 0) continue;
        else {
          Question q = new Question();
          q.setQuestionSet(dm.formatCellValue(row.getCell(0)));
          q.setQuestionTag(dm.formatCellValue(row.getCell(1)));
          q.setQuestionType(dm.formatCellValue(row.getCell(2)));

          q.setQuestionContent(dm.formatCellValue(row.getCell(3)));
          q.setQuestionMaxMarks(
            Integer.parseInt(dm.formatCellValue(row.getCell(4)))
          );
          q.setQuestionNegMarks(
            Integer.parseInt(dm.formatCellValue(row.getCell(5)))
          );
          q.setQuestionOptions(dm.formatCellValue(row.getCell(6)));
          q.setQuestionAns(dm.formatCellValue(row.getCell(7)));
          testDAO.saveQuestions(1, q);
        }
      }
    }
    workbook.close();

    //model.addObject("val", "got the file!");
    return model;
  }

  @RequestMapping(
    value = "/get-test-data/{testID}",
    method = RequestMethod.POST,
    produces = "application/json"
  )
  @ResponseBody
  public String testData(
    @PathVariable String testID,
    @RequestBody String json
  ) {
    TestDAO testDAO = ctx.getBean(TestDAO.class);
    //creating a json object for easiness will have test_id and user_id
    Gson gson = new GsonBuilder().create();
    JsonObject jsonData = gson.fromJson(json, JsonObject.class);
    String test_id = jsonData.get("test_id").getAsString();
    String user_id = jsonData.get("user_id").getAsString();

    //making a testidentifier
    int testIdentifier = 0;
    try {
      testIdentifier = Integer.parseInt(testID);
    } catch (Exception e) {
      System.out.println("Could not convert String to Integer");
      // terminate code and show error msg.
      return null;
    }

    String testData = testDAO.getTestData(testIdentifier, test_id, user_id);
    return testData;
    //return new ModelAndView("test-data").addObject("data", testData);
  }

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public ModelAndView showAdminDashBoard(HttpServletRequest req) {
    if (!utils.isUserAuthenticated(req)) {
      return new ModelAndView(new RedirectView("/", true));
    }

    return new ModelAndView("test-admin-dashboard");
  }

  @RequestMapping(
    value = "/profile",
    method = { RequestMethod.GET, RequestMethod.POST }
  )
  public ModelAndView showStudentDashBoard(HttpServletRequest req) {
    TestUtilitiesDAO tutils = ctx.getBean(TestUtilitiesDAO.class);

    if (!utils.isUserAuthenticated(req)) {
      return new ModelAndView(new RedirectView("/", true));
    }

    System.out.println(
      "isLoggedIn: " + req.getSession().getAttribute("isLoggedIn").toString()
    );
    System.out.println(
      "LOGGED IN USER CURRENT USER" +
      (User) req.getSession().getAttribute("user")
    );
    this.loggedInUser = (User) req.getSession().getAttribute("user");

    String courseCode = req.getParameter("course");

    System.out.println("GOT INQUIRY FOR " + courseCode);
    if (courseCode != null) {
      RedirectView v = new RedirectView(
        req.getContextPath() + "/courses/" + courseCode + "/payment/redirect"
      );
      v.setExposeModelAttributes(false);

      return new ModelAndView(v);
    }

    User u = (User) req.getSession().getAttribute("user");

    List<ProgramSpecificTests> tests = tutils.getAllTest(u);
    List<TestUser> finishedTest = tutils.getAllFinishedTest(u);
    return new ModelAndView("admin-dashboard")
      .addObject("tests", tests)
      .addObject("finishedTest", finishedTest)
      .addObject("user", u);
  }

  /*
   * @RequestMapping(value = "/admin-login", method = RequestMethod.GET) public
   * ModelAndView showAdminLogin(HttpServletRequest req) {
   * req.getSession().setAttribute("isLoggedIn", false);
   *
   * return new ModelAndView("test-admin-login"); }
   */

  @RequestMapping(value = "/admin-dashboard", method = RequestMethod.GET)
  public ModelAndView showAdminPanel() {
    return new ModelAndView("test-admin-dashboard");
  }

  @RequestMapping(value = "/add-test", method = RequestMethod.GET)
  public ModelAndView showAddTestPanel(HttpServletRequest req) {
    User user = (User) req.getSession().getAttribute("user");
    TestDAO testDao = ctx.getBean(TestDAO.class);
    return new ModelAndView("test-admin-upload-test")
      .addObject(
        "testId",
        RandomStringUtils.randomAlphanumeric(17).toUpperCase()
      )
      .addObject("userId", user.getEmail())
      .addObject("programs", testDao.getAllCourses());
  }

  @RequestMapping(value = "/get-result", method = RequestMethod.GET)
  @ResponseBody
  public String setTestResult(
    @RequestParam String json,
    HttpServletRequest req
  ) {
    TestDAO testDAO = ctx.getBean(TestDAO.class);

    JsonObject result = testDAO.makeAnswerSheet(json, req);
    //now we can show this to him
    System.out.println("RESULT ON MODEL AND VIEW" + result);
    return result.toString();
  }

  @RequestMapping(value = "/give-test/{testId}", method = RequestMethod.GET)
  public ModelAndView giveTest(
    @PathVariable String testId,
    HttpServletRequest req
  ) {
    User u = (User) req.getSession().getAttribute("user");
    return new ModelAndView("give-test")
      .addObject("testName", new String("TAKE TEST PART"))
      .addObject("test_id", testId)
      .addObject("user_id", u.getU_id());
  }

  @RequestMapping(value = "/update-test-data", method = RequestMethod.POST)
  public void updateOrInsertTestData(@RequestBody String json) {
    TestDAO testDAO = ctx.getBean(TestDAO.class);
    testDAO.getStoredTestData(json);
  }

  @RequestMapping(value = "/admin/tests", method = RequestMethod.GET)
  public ModelAndView showTests(HttpServletRequest req) {
    TestDAO testDao = ctx.getBean(TestDAO.class);
    if (utils.isUserAuthenticated(req)) {
      User user = (User) req.getSession().getAttribute("user");
      //User user = new User();

      List<ProgramSpecificTests> tests = testDao.getAllTestsByAdmin(user);

      return new ModelAndView("test-admin-dashboard-tests").addObject(
        "tests",
        tests
      );
    }

    // show login page
    return null;
  }

  @RequestMapping(
    value = "/admin/get-questions-for-test/{testId}",
    method = RequestMethod.GET,
    produces = "application/json"
  )
  @ResponseBody
  public String getQuestions(
    @PathVariable String testId,
    HttpServletRequest req
  ) {
    TestDAO testDao = ctx.getBean(TestDAO.class);
    List<Question> questions = null;
    String jsonStr = null;
    if (testId.length() > 0) {
      questions = testDao.getQuestionsForTest(testId);
      Gson gson = new Gson();
      jsonStr = gson.toJson(questions);
    }
    return jsonStr;
  }

  @RequestMapping(value = "/update-user-info", method = RequestMethod.POST)
  public ModelAndView updateUserInfo(
    HttpServletRequest req,
    @ModelAttribute("user") User user
  ) {
    System.out.println("\n \n REQUEST FOR SERVER " + req);

    System.out.println("USER FRoM 322" + user);
    TestDAO testDao = ctx.getBean(TestDAO.class);
    UserDAO userdao = ctx.getBean(UserDAO.class);
    TestUtilitiesDAO tutils = ctx.getBean(TestUtilitiesDAO.class);

    System.out.println(
      "\n \n USER UPDATED now " + user + "\n \n" + loggedInUser
    );
    User currentUser = (User) req.getSession().getAttribute("user");
    try {
      currentUser.setFilePath(userdao.returnImagePath(user.getFile()));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("\n \n \n ERROR UPLOADING FILE \n \n \n");
      e.printStackTrace();
    }
    userdao.updateUserInfo(user, currentUser);
    user.setU_id(currentUser.getU_id());
    User u = user;

    List<ProgramSpecificTests> tests = tutils.getAllTest(u);
    List<TestUser> finishedTest = tutils.getAllFinishedTest(u);
    return new ModelAndView("admin-dashboard")
      .addObject("tests", tests)
      .addObject("finishedTest", finishedTest)
      .addObject("user", u);
  }

  @RequestMapping(value = "/get-completed-tests", method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView getCompletedTests(HttpServletRequest req) {
    if (utils.isUserAuthenticated(req)) {
      TestUtilitiesDAO tutils = ctx.getBean(TestUtilitiesDAO.class);
      User user = (User) req.getSession().getAttribute("user");
      //return "something";
      return new ModelAndView("profile-test-history")
        .addObject("tests", tutils.getAllFinishedTest(user))
        .addObject("user", user);
    }
    System.out.println("User not Authenticated");
    return null;
  }

  @RequestMapping(value = "/get-live-tests", method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView getLiveTests(HttpServletRequest req)
    throws InterruptedException {
    if (utils.isUserAuthenticated(req)) {
      TestUtilitiesDAO tutils = ctx.getBean(TestUtilitiesDAO.class);
      User user = (User) req.getSession().getAttribute("user");
      //return "something";
      Thread.sleep(2000);
      return new ModelAndView("profile-live-tests")
        .addObject("tests", tutils.getAllTest(user))
        .addObject("rTest", tutils.getResumableTests(user));
    }
    System.out.println("User not Authenticated");
    return null;
  }

  @RequestMapping(
    value = "/admin/test-details/{testCode}",
    method = RequestMethod.GET
  )
  public ModelAndView showTestDetails(
    HttpServletRequest req,
    @PathVariable String testCode
  ) {
    TestDAO tdao = ctx.getBean(TestDAO.class);
    System.out.println("in the authetication session");

    if (utils.isUserAuthenticated(req)) {
      User user = (User) req.getSession().getAttribute("user");
      Test test = tdao.getTest(testCode);
      System.out.println(
        "in the authetication session " +
        test.getAdmin() +
        " - " +
        user.getUsername()
      );
      if (test.getAdmin().equalsIgnoreCase(user.getUsername())) {
        List<TestUser> tusers = tdao.getTestTakers(test);
        JsonArray marks = new JsonArray();
        for (TestUser t : tusers) {
          JsonObject m = new JsonObject();
          m.addProperty("username", t.getFirstName());
          m.addProperty("marks", t.getMarks());
          m.addProperty("max_marks", t.getMaxMarks());
          marks.add(m);
        }
        System.out.println("size" + tusers.size());

        return new ModelAndView("test-admin-dashboard-test-details")
          .addObject("tusers", tusers)
          .addObject("data", marks);
      } else {
        // this test is not you test.
      }
    } else {
      // return login page.
    }
    return null;
  }

  @Override
  public ModelAndView handleRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws Exception {
    return null;
  }

  @Override
  protected ModelAndView handleRequestInternal(
    HttpServletRequest arg0,
    HttpServletResponse arg1
  ) throws Exception {
    return null;
  }
}
