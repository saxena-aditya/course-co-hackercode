package com.web.hackercode.mappers;

import com.web.hackercode.structures.EntityCourse;
import com.web.hackercode.structures.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class EntityCourseMapper implements RowMapper<Object> {

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    EntityCourse ec = new EntityCourse();

    return ec;
  }
}
