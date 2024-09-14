package com.web.hackercode.dao;

import com.google.gson.JsonObject;
import com.web.hackercode.structures.Program;
import com.web.hackercode.structures.ProgramSpecificTests;
import com.web.hackercode.structures.Question;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.Test;
import com.web.hackercode.structures.TestUser;
import com.web.hackercode.structures.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface TestDAO {
  public boolean saveTest(Test test, String path) throws IOException;

  public boolean saveQuestions(int testId, Question q);

  public String getTestData(int testIdentifier, String test_id, String user_id);

  public JsonObject makeAnswerSheet(String data, HttpServletRequest request);

  public boolean saveFile(int testId, MultipartFile file, String path)
    throws IOException;

  public String getStoredTestData(String json);

  public Test getTest(int testId);

  public Test getTest(String testCode);

  public List<Program> getAllCourses();

  public List<ProgramSpecificTests> getAllTestsByAdmin(User u);

  public List<Question> getQuestionsForTest(String testIdentifier);

  public boolean saveQuestion(Question q, int mode);

  public List<TestUser> getTestTakers(Test test);
}
