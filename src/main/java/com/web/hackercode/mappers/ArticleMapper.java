package com.web.hackercode.mappers;

import com.web.hackercode.structures.Article;
import com.web.hackercode.structures.Course;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class ArticleMapper implements RowMapper<Article> {

  public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
    Article a = new Article();
    a.setId(rs.getInt("id"));
    a.setTitle(rs.getString("name"));
    a.setTags(rs.getString("tags"));
    a.setContent(rs.getString("content"));
    a.setCategory(rs.getString("cat"));
    a.setSubCategory(rs.getString("sub_cat"));
    a.setId_hash(rs.getString("id_hash"));
    a.setCategoryCode(rs.getString("cat_id"));
    a.setSubCategoryCode(rs.getString("sub_cat_id"));

    return a;
  }
}
