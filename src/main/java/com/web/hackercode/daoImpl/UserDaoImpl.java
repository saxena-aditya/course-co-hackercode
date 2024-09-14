package com.web.hackercode.daoImpl;

import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.dao.UserDAO;
import com.web.hackercode.mappers.StudentMapper;
import com.web.hackercode.mappers.UserMapper;
import com.web.hackercode.structures.EntityLesson;
import com.web.hackercode.structures.InternshipForm;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.Student;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UserDaoImpl implements UserDAO {

  Utility utils = new Utility();

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

  public boolean savePicture(User user, String imagePath) {
    jdbcTemplate.setDataSource(getDataSource());
    String UPDATE_PROFILE_PIC =
      "UPDATE hc_user_details SET ud_img_path = ? WHERE ud_username = ?";

    try {
      System.out.println(user.getEmail());
      System.out.println(imagePath);
      jdbcTemplate.update(
        UPDATE_PROFILE_PIC,
        new Object[] { HCConstants.S3_CDN_URL + imagePath, user.getEmail() }
      );
      System.out.println("returning after query");
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public String uploadFile(
    HttpServletRequest req,
    MultipartFile file,
    String relPath
  ) {
    if (!file.isEmpty()) {
      String LOCATION = null;
      InputStream in;
      FileOutputStream f = null;
      try {
        in = file.getInputStream();
        File currdir = new File(relPath);
        String path = currdir.getAbsolutePath();
        LOCATION = path.substring(0, path.length()) +
        "/" +
        file.getOriginalFilename();
        f = new FileOutputStream(LOCATION);
        int ch = 0;
        // add try,catch and finally block to copy the file
        while ((ch = in.read()) != -1) {
          f.write(ch);
        }

        System.out.println(LOCATION);
        return "img/" + file.getOriginalFilename();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          f.flush();
          f.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } else {
      System.out.println(
        "You failed to upload " + " because the file was empty."
      );
    }
    return "";
  }

  public boolean isUser(
    String username,
    String password,
    HttpServletRequest request
  ) {
    //if (!(boolean) request.getSession().getAttribute("isLoggedIn")) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_USER =
      "SELECT COUNT(*) FROM hc_user WHERE BINARY u_username = ? AND BINARY u_password = ?";
    System.out.println(IS_USER);
    System.out.println(username + "  " + utils.getMd5(password));

    try {
      Number count = jdbcTemplate.queryForObject(
        IS_USER,
        Integer.class,
        username,
        utils.getMd5(password)
      );
      System.out.println("data: " + count.intValue());
      if (count.intValue() != 1) return false;
      else {
        request.getSession().setAttribute("isLoggedIn", true);
        return true;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    System.out.println("done!");
    //}
    //else {
    // login with saved user.
    //}
    return false;
  }

  public User getUser(String username, HttpServletRequest req) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USER =
      "SELECT * FROM hc_user_details WHERE BINARY ud_username = ?";
    try {
      User user = (User) jdbcTemplate.queryForObject(
        GET_USER,
        new Object[] { username },
        new UserMapper()
      );
      req.getSession().setAttribute("user", user);
      return user;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public int getUserCountWithEmail(String email) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USER =
      "SELECT COUNT(*) FROM hc_user_details WHERE BINARY ud_email = ?";
    Number count = 0;
    try {
      count = jdbcTemplate.queryForObject(GET_USER, Integer.class, email);
      return new Integer(count.intValue());
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public String returnImagePath(MultipartFile file) throws IOException {
    System.out.println("\n \n RETURN IMAGE PATH FILE INVOKED \n \n" + file);
    String LOCATION = "";
    System.out.println(file.getName());
    InputStream in = file.getInputStream();
    System.out.println("\n \n BEFORE CURR DIR \n \n");
    File currdir = new File("D:\\hackercode\\src\\main\\images");
    String path = currdir.getAbsolutePath();
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
    System.out.println("\n LOCATION FOR SAVED FILE PATH : " + LOCATION + "\n");
    return LOCATION;
  }

  public User saveUserViaRequest(HttpServletRequest req, Register user) {
    if (this.saveUser(user)) return getUser(user.getEmail(), req);
    return null;
  }

  public boolean saveUser(Register ruser) {
    jdbcTemplate.setDataSource(getDataSource());
    ruser.setPassword(utils.getMd5(ruser.getPassword()));
    String SAVE_USER =
      "INSERT INTO hc_user_details (ud_username, ud_firstname, ud_lastname, ud_email, ud_role, ud_phone, ud_institute) VALUES (?,?,?,?,0,?,?)";
    String SAVE_USER_LOGIN_CREDENTIALS =
      "INSERT INTO hc_user (u_username, u_password) VALUES (?,?)";
    // String ADD_USER_WITH_PROGRAM = "INSERT INTO hc_user_program (up_username, up_code) VALUES (?,?)";
    try {
      jdbcTemplate.update(
        SAVE_USER,
        new Object[] {
          ruser.getEmail(),
          ruser.getfName(),
          ruser.getlName(),
          ruser.getEmail(),
          ruser.getPhone(),
          ruser.getInstitute(),
        }
      );

      jdbcTemplate.update(
        SAVE_USER_LOGIN_CREDENTIALS,
        new Object[] { ruser.getEmail(), ruser.getPassword() }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public User getUser(String username) {
    jdbcTemplate.setDataSource(getDataSource());
    String COUNT_USER =
      "SELECT COUNT(*) FROM hc_user_details WHERE ud_username = ?";
    String GET_USER = "SELECT * FROM hc_user_details WHERE ud_username = ?";

    int count = jdbcTemplate.queryForObject(
      COUNT_USER,
      new Object[] { username },
      Integer.class
    );

    if (count != 0) {
      User user = (User) jdbcTemplate.queryForObject(
        GET_USER,
        new Object[] { username },
        new UserMapper()
      );

      if (user != null) return user;
    }

    return null;
  }

  public void updateUserInfo(User user, User currentUser) {
    jdbcTemplate.setDataSource(getDataSource());
    System.out.println("CURRENT USER" + currentUser);

    String UPDATE_USER_INFO =
      "UPDATE `hc_user_details` SET ud_username=?,ud_firstname=?,ud_lastname=?,ud_institute=?,ud_email=?, ud_img_path = ? WHERE ud_id = ?";
    jdbcTemplate.update(
      UPDATE_USER_INFO,
      new Object[] {
        user.getUsername(),
        user.getFirstName(),
        user.getLastName(),
        user.getInstitute(),
        user.getEmail(),
        user.getFilePath(),
        currentUser.getU_id(),
      }
    );
  }

  public boolean saveResetLink(String username, String resetLink) {
    jdbcTemplate.setDataSource(getDataSource());
    String SAVE_KEY =
      "INSERT INTO hc_password_reset (username, reset_key) VALUES (?,?)";

    try {
      jdbcTemplate.update(SAVE_KEY, username, resetLink);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean isGoodKey(String resetLink) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_GOOD_KEY =
      "SELECT COUNT(*) FROM hc_password_reset WHERE reset_key = ? AND is_active > 0";
    try {
      int count = jdbcTemplate.queryForObject(
        IS_GOOD_KEY,
        new Object[] { resetLink },
        Integer.class
      );

      if (count == 1) return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean dropKey(String resetLink) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_COUNTER =
      "SELECT is_active FROM hc_password_reset WHERE reset_key = ?";
    String DROP_KEY =
      "UPDATE hc_password_reset SET is_active = ? WHERE reset_key = ?";
    try {
      int count = jdbcTemplate.queryForObject(
        GET_COUNTER,
        new Object[] { resetLink },
        Integer.class
      );
      System.out.println("Count is " + count);
      count = count - 1;
      jdbcTemplate.update(DROP_KEY, count, resetLink);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean updatePassword(String password, String resetKey) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USERNAME =
      "SELECT username FROM hc_password_reset WHERE reset_key = ?";
    String UPDATE_PASSWORD =
      "UPDATE hc_user SET u_password = md5(?) WHERE u_username = ?";

    String username = jdbcTemplate.queryForObject(
      GET_USERNAME,
      new Object[] { resetKey },
      String.class
    );

    if (!username.isEmpty()) {
      try {
        jdbcTemplate.update(UPDATE_PASSWORD, password, username);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
