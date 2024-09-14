package com.web.hackercode.mappers;

import com.web.hackercode.structures.FinishedTest;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FinishedTestMapper implements RowMapper<Object> {

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    FinishedTest ft = new FinishedTest();
    ft.setTestId(rs.getString("tt_test_id"));
    ft.setUserId(rs.getString("tt_user_id"));
    ft.setTimeRemainingSec(rs.getString("tt_time_remaining"));
    ft.setAnsData(rs.getString("tt_ans_object"));
    ft.setIsFinished(rs.getInt("isFinished"));
    ft.setMarks(rs.getInt("marks"));
    ft.setDate(rs.getString("tt_date"));
    ft.setMaxMarks(rs.getInt("tt_maxMarks"));
    return ft;
  }
}
