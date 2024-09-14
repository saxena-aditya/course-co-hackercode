package com.web.hackercode.daoImpl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.hackercode.dao.ReportsDAO;
import com.web.hackercode.mappers.FinishedTestMapper;
import com.web.hackercode.structures.FinishedTest;
import com.web.hackercode.structures.Question;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportsDAOImpl implements ReportsDAO {

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate = new JdbcTemplate();

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

  public FinishedTest getFinishedTestData(String testId, String userId) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_FINISHED_TEST =
      "SELECT * FROM hc_temp_test WHERE tt_user_id = ?  AND tt_test_id = ? AND isFinished = 1";

    FinishedTest ft = null;
    System.out.println("test details: " + testId + " | " + userId);
    try {
      ft = (FinishedTest) jdbcTemplate.queryForObject(
        GET_FINISHED_TEST,
        new Object[] { userId, testId },
        new FinishedTestMapper()
      );
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (ft != null) return ft;
    return null;
  }

  public List<Question> getReportCompatibleQuestions(
    List<Question> testQuestions,
    String ansData
  ) {
    JsonObject ansObj = new JsonParser().parse(ansData).getAsJsonObject();
    for (Question tq : testQuestions) {
      String qId = Integer.toString(tq.getQuestionId());
      if (ansObj.has(qId)) {
        JsonObject fquestion = (JsonObject) ansObj.get(
          Integer.toString(tq.getQuestionId())
        );

        System.out.println(fquestion.toString());
        boolean isAnswered = fquestion.get("answered").getAsBoolean();
        tq.setAnswered(isAnswered);
        if (isAnswered) {
          tq.setUserAns(fquestion.get("answer").getAsString());
        } else {
          tq.setUserAns("");
        }
        tq.setStatus(fquestion.get("status").getAsString());
      } else {
        System.out.println(qId + " Not Found!");
      }
    }

    return testQuestions;
  }

  public int incrementCounter() {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_COUNTER = "SELECT counter FROM hc_counter";
    String UPDATE_COUNTER = "UPDATE hc_counter SET counter = ? WHERE id = 1";
    int counter = jdbcTemplate.queryForObject(GET_COUNTER, Integer.class);
    jdbcTemplate.update(UPDATE_COUNTER, counter + 1);

    return counter + 1;
  }
}
