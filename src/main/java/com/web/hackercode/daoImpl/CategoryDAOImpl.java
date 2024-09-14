package com.web.hackercode.daoImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.hackercode.dao.CategoryDAO;
import com.web.hackercode.structures.ArticleCategory;
import com.web.hackercode.structures.ArticleSubCategory;
import com.web.hackercode.utility.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class CategoryDAOImpl implements CategoryDAO {

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

  public List<ArticleCategory> getCategories() {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_CATEGORIES =
      "SELECT id, name, name_ FROM hc_categories WHERE is_active = 1";
    try {
      List<ArticleCategory> categories = jdbcTemplate.query(
        GET_CATEGORIES,
        new ResultSetExtractor<List<ArticleCategory>>() {
          public List<ArticleCategory> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<ArticleCategory> list = new ArrayList<ArticleCategory>();
            while (rs.next()) {
              ArticleCategory cat = new ArticleCategory();
              cat.setId(rs.getInt("id"));
              cat.setName(rs.getString("name"));
              cat.setName_(rs.getString("name_"));
              list.add(cat);
            }
            return list;
          }
        }
      );
      return categories;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public List<ArticleSubCategory> getSubCategories(String categoryCode) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_SUB_CATEGORIES =
      "SELECT id, name, name_ FROM hc_sub_categories WHERE cat_id = ? AND is_active = 1";
    final int catID = Integer.parseInt(categoryCode);

    try {
      List<ArticleSubCategory> categories = jdbcTemplate.query(
        GET_SUB_CATEGORIES,
        new Object[] { catID },
        new ResultSetExtractor<List<ArticleSubCategory>>() {
          public List<ArticleSubCategory> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<ArticleSubCategory> list = new ArrayList<ArticleSubCategory>();
            while (rs.next()) {
              ArticleSubCategory cat = new ArticleSubCategory();
              cat.setCatId(catID);
              cat.setId(rs.getInt("id"));
              cat.setName_(rs.getString("name_"));
              cat.setName(rs.getString("name"));
              list.add(cat);
            }
            return list;
          }
        }
      );
      return categories;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public String getNavJSON() {
    List<ArticleCategory> cat = getCategories();
    JsonArray arr = new JsonArray();

    for (int i = 0; i < cat.size(); i++) {
      JsonObject obj1 = new JsonObject();
      obj1.addProperty("id", cat.get(i).getId());
      obj1.addProperty("name", cat.get(i).getName());
      obj1.addProperty("value", cat.get(i).getName_());
      obj1.addProperty("isNew", false);

      List<ArticleSubCategory> subCat = getSubCategories(
        Integer.toString(cat.get(i).getId())
      );
      JsonArray ar = new JsonArray();
      for (int j = 0; j < subCat.size(); j++) {
        JsonObject oo = new JsonObject();
        oo.addProperty("id", subCat.get(j).getId());
        oo.addProperty("name", subCat.get(j).getName());
        oo.addProperty("value", subCat.get(j).getName_());
        ar.add(oo);
      }
      obj1.add("sub_category", ar);
      arr.add(obj1);
    }

    return arr.toString();
  }

  @Override
  public boolean deleteSubCategory(int catId, int subCatId) {
    jdbcTemplate.setDataSource(getDataSource());

    String DELETE_SUB_CAT =
      "UPDATE hc_sub_categories SET is_active = 0 WHERE cat_id = ? AND id = ?";
    try {
      jdbcTemplate.update(DELETE_SUB_CAT, new Object[] { catId, subCatId });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public int addSubCategory(final int catId, final String subCatName) {
    jdbcTemplate.setDataSource(getDataSource());

    final String CREATE_SUB_CAT =
      "INSERT INTO hc_sub_categories (cat_id, name, name_) VALUES (?,?,?)";
    final String name_ = subCatName.toLowerCase().replace(" ", "-");

    GeneratedKeyHolder holder = new GeneratedKeyHolder();

    try {
      jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con)
            throws SQLException {
            PreparedStatement statement = con.prepareStatement(
              CREATE_SUB_CAT,
              Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, catId);
            statement.setString(2, subCatName);
            statement.setString(3, name_);
            return statement;
          }
        },
        holder
      );

      return holder.getKey().intValue();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
  }

  @Override
  public boolean deleteCategory(int catId) {
    jdbcTemplate.setDataSource(getDataSource());
    String DELETE_SUB_CAT =
      "UPDATE hc_categories SET is_active = 0 WHERE id = ?";
    try {
      jdbcTemplate.update(DELETE_SUB_CAT, new Object[] { catId });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public int addCategory(final String catName) {
    jdbcTemplate.setDataSource(getDataSource());
    final String CREATE_CATEGORY =
      "INSERT INTO hc_categories (name, name_) VALUES (?,?)";
    final String name_ = catName.toLowerCase().replace(" ", "-");

    System.out.println(catName);

    System.out.println(name_);

    GeneratedKeyHolder holder = new GeneratedKeyHolder();

    try {
      jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con)
            throws SQLException {
            PreparedStatement statement = con.prepareStatement(
              CREATE_CATEGORY,
              Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, catName);
            statement.setString(2, name_);
            return statement;
          }
        },
        holder
      );

      return holder.getKey().intValue();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
  }
}
