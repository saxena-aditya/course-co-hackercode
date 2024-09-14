package com.web.hackercode.mappers;

import com.web.hackercode.structures.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<Object> {

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    User user = new User();

    user.setU_id(rs.getInt("ud_id"));
    user.setFirstName(rs.getString("ud_firstname"));
    user.setLastName(rs.getString("ud_lastname"));
    user.setUsername(rs.getString("ud_username"));
    user.setInstitute(rs.getString("ud_institute"));
    user.setEmail(rs.getString("ud_email"));
    user.setFilePath(rs.getString("ud_img_path"));
    user.setInstitute(rs.getString("ud_institute"));

    if (rs.getString("ud_role").equals("1")) {
      user.setAdmin(true);
      user.setDrafter(false);
    } else if (rs.getString("ud_role").equals("2")) {
      user.setAdmin(false);
      user.setDrafter(true);
    } else {
      user.setAdmin(false);
      user.setDrafter(false);
    }
    return user;
  }
}
