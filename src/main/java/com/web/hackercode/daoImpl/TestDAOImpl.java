package com.web.hackercode.daoImpl;

import com.google.gson.*;
import com.web.hackercode.dao.TestDAO;
import com.web.hackercode.mappers.TestMapper;
import com.web.hackercode.mappers.UserMapper;
import com.web.hackercode.structures.Admin;
import com.web.hackercode.structures.Program;
import com.web.hackercode.structures.ProgramSpecificTests;
import com.web.hackercode.structures.Question;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.Test;
import com.web.hackercode.structures.TestData;
import com.web.hackercode.structures.TestInfoFromClient;
import com.web.hackercode.structures.TestUser;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TestDAOImpl implements TestDAO {

  /*
   * @Autowired ServletContext context;
   */

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate = new JdbcTemplate();
  Utility utils = new Utility();

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

  public boolean saveFile(int testId, MultipartFile file, String path)
    throws IOException {
    String pathServer =
      "/opt/bitnami/apache-tomcat/webapps/WebHackerCode/resources/file-data";

    String unEscapedString =
      "<java>public static void main(String[] args) { ... }</java>";

    String escapedHTML = StringEscapeUtils.escapeHtml4(unEscapedString);

    String LOCATION = "";
    System.out.println(file.getName());
    InputStream in = file.getInputStream();

    /*
     * String relativeWebPath = "/resources/file-data"; String absoluteFilePath =
     * context.getRealPath(relativeWebPath);
     */

    File currdir = new File(
      "A:\\HackerCode\\src\\main\\webapp\\resources\\file-data"
    );

    //      String path = currdir.getAbsolutePath();
    LOCATION = path.substring(0, path.length()) +
    "\\" +
    file.getOriginalFilename();
    FileOutputStream f = new FileOutputStream(LOCATION);
    int ch = 0;
    // add try,catch and finally blocks to copy the file
    while ((ch = in.read()) != -1) {
      f.write(ch);
    }

    System.out.println(LOCATION);
    f.flush();
    f.close();

    Workbook workbook = null;
    try {
      workbook = WorkbookFactory.create(new File(LOCATION));
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
            if (
              dm.formatCellValue(row.getCell(4)).toString().length() > 0
            ) q.setQuestionMaxMarks(
              Integer.parseInt(dm.formatCellValue(row.getCell(4)))
            );
            if (
              dm.formatCellValue(row.getCell(5)).toString().length() > 0
            ) q.setQuestionNegMarks(
              Integer.parseInt(dm.formatCellValue(row.getCell(5)))
            );
            q.setQuestionOptions(dm.formatCellValue(row.getCell(6)));
            q.setQuestionAns(dm.formatCellValue(row.getCell(7)));
            if (q.getQuestionContent().length() > 0) saveQuestions(testId, q);
          }
        }
      }

      workbook.close();
      System.out.println("About to return True");
      return true;
    } catch (InvalidFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }

  public boolean saveTest(Test test, String path) throws IOException {
    String startTime, endTime;
    Timestamp start, end;
    jdbcTemplate.setDataSource(getDataSource());

    //System.out.println(test.toString());

    String sql =
      "INSERT INTO hc_tests (t_name, t_associated_program, t_user_id, t_test_code,t_test_password," +
      "t_start_time, t_end_time, t_total_time,t_is_time_strict, t_is_ans_shuffle) VALUES(?,?,?,?,?,?,?,?,?,?)";

    startTime = test.getStartTime();
    endTime = test.getEndTime();

    // converting hour and minutes into milliseconds because the test works on millisecond data
    int hours = test.getTotalTimeHours(), minutes = test.getTotalTimeMinutes();
    long milliseconds = TimeUnit.SECONDS.toMillis(
      TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes)
    );

    if (StringUtils.countMatches(startTime, ":") == 1) startTime += ":00";
    if (StringUtils.countMatches(endTime, ":") == 1) endTime += ":00";
    //System.out.println(test.toString());
    start = Timestamp.valueOf(startTime.replace("T", " "));
    end = Timestamp.valueOf(endTime.replace("T", " "));
    // saving test data.
    try {
      jdbcTemplate.update(
        sql,
        new Object[] {
          test.getName(),
          test.getCourseCode(),
          test.getAdmin(),
          test.getCode(),
          test.getPassword(),
          start,
          end,
          milliseconds,
          test.getIsTimeStrict(),
          test.getIsAnsShuffle(),
        }
      );

      String GET_TEST_CODE =
        "SELECT t_id FROM hc_tests WHERE t_test_code = ? AND t_is_active = 1";
      Number testId = jdbcTemplate.queryForObject(
        GET_TEST_CODE,
        new Object[] { test.getCode() },
        Integer.class
      );

      if (saveFile(testId.intValue(), test.getFile(), path)) return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean saveQuestions(int testId, Question q) {
    jdbcTemplate.setDataSource(getDataSource());

    String INSERT_QUESTION =
      "INSERT INTO hc_questions (q_set_id, q_tag, q_type," +
      "q_content,q_max_marks, q_negative_marks, q_options, q_ans, q_scope ) VALUES (?,?,?,?,?,?,?,?,?)";
    String GET_LAST_INSERTED_QUESTION =
      "SELECT q_question_id FROM hc_questions ORDER by q_question_id DESC LIMIT 1";
    String ADD_QUESTION_TO_TEST =
      "INSERT INTO hc_test_questions (tq_test_id, tq_question_id) VALUES (?,?)";

    try {
      jdbcTemplate.update(
        INSERT_QUESTION,
        new Object[] {
          q.getQuestionSet().replaceAll(" ", "-"),
          q.getQuestionTag(),
          q.getQuestionType(),
          q.getQuestionContent(),
          q.getQuestionMaxMarks(),
          q.getQuestionNegMarks(),
          q.getQuestionOptions(),
          q.getQuestionAns(),
          "PUBLIC",
        }
      );
      Number questionId = jdbcTemplate.queryForObject(
        GET_LAST_INSERTED_QUESTION,
        Integer.class
      );
      jdbcTemplate.update(
        ADD_QUESTION_TO_TEST,
        new Object[] { testId, questionId }
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }

  public String getTestData(
    int testIdentifier,
    String test_id,
    String user_id
  ) {
    jdbcTemplate.setDataSource(getDataSource());

    String sqlQuestions =
      "SELECT * FROM hc_test_questions JOIN hc_questions WHERE hc_test_questions.tq_question_id = hc_questions.q_question_id AND hc_test_questions.tq_test_id = ?";
    String sqlTest = "SELECT * FROM hc_tests WHERE t_id = ?";
    Gson gson = new GsonBuilder().create();

    //for checking if there is any data for test already present
    List<TestUser> users_test = jdbcTemplate.query(
      "SELECT * from hc_temp_test WHERE tt_user_id = ? AND tt_test_id = ? ",
      new Object[] { user_id, test_id },
      new ResultSetExtractor<List<TestUser>>() {
        public List<TestUser> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<TestUser> list = new ArrayList<TestUser>();
          while (rs.next()) {
            TestUser u = new TestUser();
            u.setUserId(rs.getString("tt_user_id"));
            u.setTestId(rs.getString("tt_test_id"));
            u.setData(rs.getString("tt_ans_object"));
            u.setTimeLeft(rs.getString("tt_time_remaining"));
            list.add(u);
          }
          return list;
        }
      }
    );

    System.out.println("LENTH OF ARRAY KLIST IN USERS" + users_test.size());

    //for creating the response json object for test
    try {
      Test test = (Test) jdbcTemplate.queryForObject(
        sqlTest,
        new Object[] { testIdentifier },
        new TestMapper()
      );
      List<Question> questions = jdbcTemplate.query(
        sqlQuestions,
        new Object[] { testIdentifier },
        new ResultSetExtractor<List<Question>>() {
          public List<Question> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Question> list = new ArrayList<Question>();
            while (rs.next()) {
              Question q = new Question();
              q.setQuestionId(rs.getInt("tq_question_id"));
              q.setTestId(rs.getInt("tq_test_id"));
              q.setQuestionSet(rs.getString("q_set_id"));
              q.setQuestionTag(rs.getString("q_tag"));
              q.setQuestionType(rs.getString("q_type"));
              q.setQuestionContent(rs.getString("q_content"));
              q.setQuestionMaxMarks(rs.getInt("q_max_marks"));
              q.setQuestionNegMarks(rs.getInt("q_negative_marks"));
              q.setQuestionOptions(rs.getString("q_options"));
              q.setQuestionAns(rs.getString("q_ans"));
              list.add(q);
            }
            return list;
          }
        }
      );

      System.out.println(test.getName());
      if (questions.size() != 0) {
        // questions found
        JsonObject t = new JsonObject();
        t.addProperty("test_id", test.getTestId());
        t.addProperty("test_name", test.getName());
        t.addProperty("test_duration", test.getTotalTime());
        t.addProperty("start_time", test.getStartTime().toString());
        t.addProperty("end_time", test.getEndTime().toString());
        t.addProperty("test_admin", test.getAdmin());
        t.addProperty("is_time_strict", test.getIsTimeStrict());
        t.addProperty("is_ans_shuffle", test.getIsAnsShuffle());

        ArrayList<String> sets = new ArrayList<String>();

        // getting all the different sets;
        for (Question quest : questions) {
          if (!sets.contains(quest.getQuestionSet())) {
            System.out.println("Adding: " + quest.getQuestionSet());
            sets.add(quest.getQuestionSet());
          }
        }
        System.out.println();
        //JsonObject s = null;
        JsonObject xx = new JsonObject();
        JsonObject ds = new JsonObject();
        for (int i = 0; i < sets.size(); i++) {
          //t = new JsonObject();
          JsonArray sx = null;
          sx = new JsonArray();

          for (Question q : questions) {
            if (q.getQuestionSet().equalsIgnoreCase(sets.get(i))) {
              JsonObject ss = new JsonObject();

              ss.addProperty("id", q.getQuestionId());
              ss.addProperty("tag", q.getQuestionTag());
              ss.addProperty("max_marks", q.getQuestionMaxMarks());
              ss.addProperty("neg_marks", q.getQuestionNegMarks());
              ss.addProperty("question", q.getQuestionContent());

              // ss.addProperty("answer", q.getQuestionAns());

              ss.addProperty("status", "normal");

              // @ is the delimiter for separating question options
              String[] array = q.getQuestionOptions().split("@");
              JsonArray xz = new JsonArray();

              for (int i1 = 0; i1 < array.length; i1++) xz.add(array[i1]);

              ss.add("options", xz);
              JsonArray j = new JsonArray();
              j.add(q.getQuestionAns());
              //ss.add("answer", j);
              //sx is array of questions specific to sets
              sx.add(ss);
            }
          }
          t.addProperty("total_sets", sets.size());
          JsonObject setQuestions = new JsonObject();
          setQuestions.add("questions", sx);
          xx.add(sets.get(i).toLowerCase(), setQuestions);
        }
        t.add("question_set", xx);
        //if previous data was present
        if (users_test.size() > 0) {
          for (TestUser user : users_test) {
            String s = gson.toJson(user);
            JsonObject p = new JsonObject();
            p.addProperty("timeLeft", user.getTimeLeft());
            p.add(
              "data",
              (new JsonParser()).parse(user.getData()).getAsJsonObject()
            );
            t.add("previous_data", p);
          }
        }
        return t.toString();
      } else {
        // send no questions found error
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public String getStoredTestData(String data) {
    System.out.println("GET STORED TEST DATA INVOKED");
    jdbcTemplate.setDataSource(getDataSource());
    Gson gson = new GsonBuilder().create();
    JsonObject job = gson.fromJson(data, JsonObject.class);
    JsonObject ovl = job.getAsJsonObject("user");

    TestUser user = new TestUser();
    System.out.println(ovl.toString());
    user.setTestId(ovl.get("testId").getAsString());
    user.setUserId(ovl.get("userId").getAsString());
    user.setTimeLeft(ovl.get("timeLeft").getAsString());
    user.setData(ovl.get("data").getAsString());

    System.out.println("USER DATA FROM SERVER" + user);
    String user_id = user.getUserId();
    String test_id = user.getTestId();
    List<TestUser> users_test = jdbcTemplate.query(
      "SELECT * from hc_temp_test WHERE tt_user_id = ? AND tt_test_id = ? ",
      new Object[] { user_id, test_id },
      new ResultSetExtractor<List<TestUser>>() {
        public List<TestUser> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<TestUser> list = new ArrayList<TestUser>();
          while (rs.next()) {
            TestUser u = new TestUser();
            u.setUserId(rs.getString("tt_user_id"));
            u.setTestId(rs.getString("tt_test_id"));
            u.setData(rs.getString("tt_ans_object"));
            u.setTimeLeft(rs.getString("tt_time_remaining"));
            list.add(u);
          }
          return list;
        }
      }
    );

    System.out.println("LENGTH OF ARRAY KLIST IN USERS" + users_test.size());
    //first time
    if (users_test.size() == 0) {
      String sql =
        "INSERT INTO hc_temp_test(tt_user_id, tt_test_id, tt_time_remaining ,tt_ans_object) VALUES (?,?,?,?)";
      try {
        jdbcTemplate.update(
          sql,
          new Object[] {
            user.getUserId().toString(),
            user.getTestId().toString(),
            user.getTimeLeft().toString(),
            user.getData().toString(),
          }
        );
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    } else {
      for (TestUser u : users_test) {
        System.out.println(
          "DATA BEFORE ADDING TO DB" + user.getData().length()
        );
        System.out.println("FIRST TIMER USER IS AVAILABLE");
        String sql =
          "UPDATE hc_temp_test SET tt_ans_object = ? , tt_time_remaining = ? WHERE tt_user_id = ? AND tt_test_id = ?";
        try {
          jdbcTemplate.update(
            sql,
            new Object[] {
              user.getData().toString(),
              user.getTimeLeft().toString(),
              user.getUserId().toString(),
              user.getTestId().toString(),
            }
          );
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        System.out.println("DATA AFter ADDING TO DB" + user.getData());
      }
    }
    return " ";
  }

  public JsonObject makeAnswerSheet(String data, HttpServletRequest req) {
    jdbcTemplate.setDataSource(getDataSource());
    JsonObject res = new JsonObject(); // result object
    int result = 0;

    //getting the json data from the client
    Gson gson = new GsonBuilder().create();
    JsonObject job = gson.fromJson(data, JsonObject.class);
    JsonObject ovl = job.getAsJsonObject("test-data");

    Set<Map.Entry<String, JsonElement>> entries = ovl.entrySet(); //will return members of your object
    ArrayList<TestData> testStore = new ArrayList<TestData>();

    for (Map.Entry<String, JsonElement> entry : entries) {
      System.out.println("KEY >>>>" + entry.getKey());
      JsonObject temp = ovl.getAsJsonObject(entry.getKey());
      TestData tmp = gson.fromJson(temp, TestData.class);
      testStore.add(tmp);
    }

    //test-info
    JsonObject testInfo = job.getAsJsonObject("test-info");
    TestInfoFromClient testData = gson.fromJson(
      testInfo,
      TestInfoFromClient.class
    );
    String testIdentifier = testData.getId();
    List<Question> questions = getQuestionsForTest(testIdentifier);

    int maxMarks = 0;

    for (Question q : questions) {
      maxMarks = maxMarks + q.getQuestionMaxMarks();
      for (Map.Entry<String, JsonElement> entry : entries) {
        JsonObject temp = ovl.getAsJsonObject(entry.getKey());
        String k = entry.getKey().toString();
        if (k.equals(Integer.toString(q.getQuestionId()))) {
          TestData tmp = gson.fromJson(temp, TestData.class);

          if (tmp.getAnswer() != null) {
            if (tmp.getStatus().equals("skipped")) {
              break; //not attempted
            }
            if (tmp.getAnswer().equals(q.getQuestionAns())) {
              result = result + q.getQuestionMaxMarks();
            } else {
              System.out.println("WRONG ASNSER");
              result = result + -q.getQuestionNegMarks();
            }
          }
          break; //found the question no need to look ahead
        }
      }
    }
    res.addProperty("marks", result);
    res.addProperty("maxMarks", maxMarks);
    try {
      Date date = Calendar.getInstance().getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
      String strDate = dateFormat.format(date);
      //adding marks to the table
      String Update =
        "UPDATE hc_temp_test SET marks = ?, isFinished = 1, tt_date = ?, tt_maxMarks= ? WHERE tt_user_id = ? AND tt_test_id = ?";
      User user = (User) req.getSession().getAttribute("user");
      jdbcTemplate.update(
        Update,
        new Object[] {
          result,
          strDate,
          maxMarks,
          user.getU_id(),
          testData.getId(),
        }
      );
      System.out.println(
        "========================================================"
      );
      System.out.println(testData.getId().toString() + " - " + user.getU_id());
    } catch (Exception e) {
      e.printStackTrace();
    }
    res.addProperty("test_id", testData.getId());
    res.addProperty("error", 0);

    return res;
  }

  public List<Question> getQuestionsForTest(String testIdentifier) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_TEST_QUESTIONS =
      "SELECT * FROM hc_questions JOIN hc_test_questions WHERE hc_questions.q_question_id = hc_test_questions.tq_question_id AND hc_test_questions.tq_test_id = ? AND tq_is_active = 1";
    List<Question> questions = jdbcTemplate.query(
      GET_TEST_QUESTIONS,
      new Object[] { testIdentifier },
      new ResultSetExtractor<List<Question>>() {
        public List<Question> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<Question> list = new ArrayList<Question>();
          while (rs.next()) {
            Question q = new Question();
            q.setTestId(rs.getInt("tq_test_id"));
            q.setQuestionId(rs.getInt("q_question_id"));
            q.setQuestionSet(rs.getString("q_set_id"));
            q.setQuestionTag(rs.getString("q_tag"));
            q.setQuestionType(rs.getString("q_type"));
            q.setQuestionContent(rs.getString("q_content"));
            q.setQuestionMaxMarks(rs.getInt("q_max_marks"));
            q.setQuestionNegMarks(rs.getInt("q_negative_marks"));
            q.setQuestionOptions(rs.getString("q_options"));
            q.setQuestionAns(rs.getString("q_ans"));
            list.add(q);
          }
          return list;
        }
      }
    );
    return questions;
  }

  // utility function for getting test object.

  public Test getTest(int testId) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_TEST_DETAILS = "SELECT * FROM hc_tests WHERE t_id = ?";
    Test test = (Test) jdbcTemplate.queryForObject(
      GET_TEST_DETAILS,
      new Object[] { testId },
      new TestMapper()
    );
    if (test == null) return null;

    return test;
  }

  // utility function for getting test object.

  public Test getTest(String testCode) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_TEST_DETAILS = "SELECT * FROM hc_tests WHERE t_test_code = ?";
    Test test = (Test) jdbcTemplate.queryForObject(
      GET_TEST_DETAILS,
      new Object[] { testCode },
      new TestMapper()
    );
    if (test != null) return test;

    return null;
  }

  // utility function for get user details.

  public List<Program> getAllCourses() {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_PROGRAMS = "SELECT * FROM  hc_courses WHERE c_is_active = 1";
    try {
      List<Program> programs = jdbcTemplate.query(
        GET_PROGRAMS,
        new ResultSetExtractor<List<Program>>() {
          public List<Program> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Program> list = new ArrayList<Program>();
            while (rs.next()) {
              Program program = new Program();
              program.setProgramCode(rs.getString("c_code"));
              program.setProgramName(rs.getString("c_name"));
              list.add(program);
            }
            return list;
          }
        }
      );
      return programs;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // return all tests that were created by the Admin

  public List<ProgramSpecificTests> getAllTestsByAdmin(User u) {
    jdbcTemplate.setDataSource(getDataSource());
    //String username = u.getEmail();
    String username = u.getUsername();
    List<ProgramSpecificTests> tests = null;
    String GET_TESTS_BY_ADMIN =
      "SELECT * FROM hc_tests WHERE t_user_id = ? AND t_is_active = 1";
    System.out.println(u.getUsername());
    tests = jdbcTemplate.query(
      GET_TESTS_BY_ADMIN,
      new Object[] { username },
      new ResultSetExtractor<List<ProgramSpecificTests>>() {
        public List<ProgramSpecificTests> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<ProgramSpecificTests> list = new ArrayList<
            ProgramSpecificTests
          >();
          while (rs.next()) {
            ProgramSpecificTests t = new ProgramSpecificTests();
            t.setTestId(rs.getString("t_id"));
            t.setName(rs.getString("t_name"));
            t.setCode(rs.getString("t_test_code"));
            t.setCourseCode(rs.getString("t_associated_program"));
            // t.setCourseName(rs.getString("p_name"));
            t.setStartTime(rs.getString("t_start_time"));
            t.setEndTime(rs.getString("t_end_time"));
            t.setTotalTime(rs.getInt("t_total_time"));
            t.setIsTimeStrict(rs.getString("t_is_time_strict"));

            list.add(t);
          }
          return list;
        }
      }
    );
    // TODO Auto-generated method stub
    return tests;
  }

  public boolean saveQuestion(Question question, int mode) {
    String SAVE_QUESTION = "INSERT INTO hc_questions () VALUES ()";
    String ADD_QUESTION_TO_TEST =
      "INSERT INTO hc_test_questions (tq_test_id, tq_question_id) VALUES (?,?)";
    String UPDATE_QUESTION =
      "UPDATE hc_questions SET q_set_id = ?, q_tag = ?, q_type = ?, q_content = ?,q_max_marks = ?,q_negative_marks = ?, q_options = ?, q_ans = ?, q_scope = ? WHERE q_question_id = ?";
    jdbcTemplate.setDataSource(getDataSource());
    // mode: 0 => Update Question
    // mode: 1 => Insert New Question

    if (mode == 0) {
      jdbcTemplate.update(
        UPDATE_QUESTION,
        new Object[] {
          question.getQuestionSet(),
          question.getQuestionTag(),
          question.getQuestionType(),
          utils.getHTMLCompatContent(question.getQuestionContent()),
          question.getQuestionMaxMarks(),
          question.getQuestionNegMarks(),
          question.getQuestionOptions(),
          question.getQuestionAns(),
          "PUBLIC",
          question.getQuestionId(),
        }
      );
      System.out.println("question :" + question.toString());
      return true;
    }
    return true;
  }

  public List<TestUser> getTestTakers(Test test) {
    String GET_TEST_USER =
      "SELECT * FROM hc_temp_test ttest JOIN hc_user_details user JOIN hc_tests test WHERE ttest.tt_user_id = user.ud_id AND ttest.tt_test_id = test.t_id AND ttest.isFinished = 1";

    List<TestUser> tusers = jdbcTemplate.query(
      GET_TEST_USER,
      new ResultSetExtractor<List<TestUser>>() {
        public List<TestUser> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<TestUser> list = new ArrayList<TestUser>();
          while (rs.next()) {
            TestUser t = new TestUser();
            t.setTestName(rs.getString("t_name"));
            t.setTestCode(rs.getString("t_test_code"));
            t.setData(rs.getString("tt_ans_object"));
            t.setMarks(rs.getInt("marks"));
            t.setMaxMarks(rs.getInt("tt_maxMarks"));
            t.setEndDate(rs.getString("tt_date"));
            t.setUsername(rs.getString("ud_username"));
            t.setTestCode(rs.getString("t_test_code"));
            t.setFirstName(rs.getString("ud_firstname"));
            t.setLastName(rs.getString("ud_lastname"));
            t.setPic(rs.getString("ud_img_path"));

            list.add(t);
          }
          return list;
        }
      }
    );

    return tusers;
  }
}
