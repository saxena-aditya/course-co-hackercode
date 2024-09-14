package com.web.hackercode.mappers;

import com.web.hackercode.structures.InternshipForm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class InternshipApplicationMapper implements RowMapper<InternshipForm> {

  public InternshipForm mapRow(ResultSet rs, int rowNum) throws SQLException {
    InternshipForm iform = new InternshipForm();
    iform.setFirstName(rs.getString("f_name"));
    iform.setLastName(rs.getString("l_name"));
    iform.setPhnNum(rs.getLong("ph_num"));
    iform.setEmail(rs.getString("email"));
    iform.setUniversity(rs.getString("university"));
    iform.setCity(rs.getString("city"));
    iform.setAppliedAt(rs.getTimestamp("applied_at"));
    if (rs.getInt("is_handled") == 0) iform.setHandled(false);
    else iform.setHandled(true);

    return iform;
  }
}
