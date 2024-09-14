package com.web.hackercode.mappers;

import com.web.hackercode.structures.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TestMapper implements RowMapper<Object> {

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    Test test = new Test();
    test.setTestId(rs.getString("t_id"));
    test.setName(rs.getString("t_name"));
    test.setAdmin(rs.getString("t_user_id"));
    test.setCode(rs.getString("t_test_code"));
    test.setCourseCode(rs.getString("t_associated_program"));
    test.setStartTime(rs.getString("t_start_time"));
    test.setEndTime(rs.getString("t_end_time"));
    test.setTotalTime(rs.getInt("t_total_time"));
    test.setIsTimeStrict(rs.getString("t_is_time_strict"));
    test.setIsAnsShuffle(rs.getString("t_is_ans_shuffle"));

    return test;
  }
}
