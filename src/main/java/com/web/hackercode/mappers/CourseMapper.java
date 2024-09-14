package com.web.hackercode.mappers;

import com.web.hackercode.structures.Course;
import com.web.hackercode.utility.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CourseMapper implements RowMapper<Course> {

  Utility utils = new Utility();

  public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
    Course course = new Course();
    course.setName(rs.getString("c_name"));
    course.setDesc(rs.getString("c_desc"));
    course.setCode(rs.getString("c_code"));
    //course.setCreator(rs.getString("c_creator"));
    course.setPrice(rs.getInt("c_price"));
    course.setTags(rs.getString("c_tags"));
    course.setMrp(rs.getInt("c_mrp"));
    course.setTotalDays(rs.getInt("c_total_days"));
    course.setIntro(rs.getString("c_intro"));
    course.setCover(rs.getString("c_cover"));
    course.setSubDesc(rs.getString("c_sub_desc"));

    if (utils.isTestSeries(rs.getString("c_name"))) {
      course.setTestSeries(true);
    } else {
      course.setTestSeries(false);
    }
    return course;
  }
}
