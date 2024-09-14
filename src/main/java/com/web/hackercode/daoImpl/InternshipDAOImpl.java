package com.web.hackercode.daoImpl;

import com.web.hackercode.constants.HCConstants;
import com.web.hackercode.dao.InternshipDAO;
import com.web.hackercode.mappers.InternshipApplicationMapper;
import com.web.hackercode.structures.InternshipForm;
import com.web.hackercode.utility.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class InternshipDAOImpl implements InternshipDAO {

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

  public boolean applyForInternship(InternshipForm itn) {
    jdbcTemplate.setDataSource(getDataSource());

    String APPLY_FOR_INTERN =
      "INSERT INTO hc_internships (f_name, l_name, ph_num, email, university, city, course, semester) VALUES (?,?,?,?,?,?,?,?)";
    try {
      jdbcTemplate.update(
        APPLY_FOR_INTERN,
        itn.getFirstName(),
        itn.getLastName(),
        itn.getPhnNum(),
        itn.getEmail(),
        itn.getUniversity(),
        itn.getCity(),
        itn.getCourse(),
        itn.getSemester()
      );
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public List<InternshipForm> getInternshipApplications() {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_APPLICATIONS =
      "SELECT * FROM hc_internships ORDER BY is_handled ASC";
    try {
      List<InternshipForm> itn = jdbcTemplate.query(
        GET_APPLICATIONS,
        new ResultSetExtractor<List<InternshipForm>>() {
          public List<InternshipForm> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<InternshipForm> list = new ArrayList<InternshipForm>();
            while (rs.next()) {
              InternshipForm el = new InternshipForm();
              el.setId(rs.getInt("id"));
              el.setFirstName(rs.getString("f_name"));
              el.setLastName(rs.getString("l_name"));
              el.setUniversity(rs.getString("university"));
              el.setCourse(rs.getString("course"));
              el.setSemester(rs.getInt("semester"));
              el.setCity(rs.getString("city"));
              el.setEmail(rs.getString("email"));
              el.setPhnNum(rs.getLong("ph_num"));
              el.setAppliedAt(rs.getTimestamp("applied_at"));

              if (rs.getInt("is_handled") == 0) {
                el.setHandled(false);
              } else {
                el.setHandled(true);
              }

              list.add(el);
            }
            return list;
          }
        }
      );

      return itn;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public InternshipForm getInternshipApplication(String id) {
    String GET_LETTER = "SELECT * FROM hc_internships WHERE id = ?";
    try {
      InternshipForm iform = jdbcTemplate.queryForObject(
        GET_LETTER,
        new InternshipApplicationMapper(),
        id
      );
      return iform;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean markInternshipLead(String id) {
    jdbcTemplate.setDataSource(dataSource);
    String MARK_LEAD = "UPDATE hc_internships SET is_handled = 1 WHERE id = ?";

    try {
      jdbcTemplate.update(MARK_LEAD, new Object[] { id });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public void addDrafterAccount(InternshipForm iform, String internUsername) {
    jdbcTemplate.setDataSource(dataSource);
    String SAVE_USER =
      "INSERT INTO hc_user_details (ud_username, ud_firstname, ud_lastname, ud_email, ud_role, ud_phone, ud_institute) VALUES (?,?,?,?,?,?,?)";
    String SAVE_USER_LOGIN_CREDENTIALS =
      "INSERT INTO hc_user (u_username, u_password) VALUES (?,?)";

    try {
      jdbcTemplate.update(
        SAVE_USER,
        new Object[] {
          internUsername,
          iform.getFirstName(),
          iform.getLastName(),
          iform.getEmail(),
          HCConstants.DRAFTER_ACC,
          iform.getPhnNum(),
          iform.getUniversity(),
        }
      );

      jdbcTemplate.update(
        SAVE_USER_LOGIN_CREDENTIALS,
        new Object[] {
          internUsername,
          utils.getMd5(Long.toString(iform.getPhnNum())),
        }
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
