package com.web.hackercode.dao;

import com.web.hackercode.structures.FinishedTest;
import com.web.hackercode.structures.Question;
import java.util.List;

public interface ReportsDAO {
  public FinishedTest getFinishedTestData(String testId, String userId);

  public List<Question> getReportCompatibleQuestions(
    List<Question> testQuestions,
    String ansData
  );

  public int incrementCounter();
}
