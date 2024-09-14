package com.web.hackercode.daoImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.hackercode.dao.ArticleDAO;
import com.web.hackercode.mappers.ArticleMapper;
import com.web.hackercode.structures.Article;
import com.web.hackercode.structures.ArticleCategory;
import com.web.hackercode.structures.ArticleSubCategory;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class ArticleDAOImpl implements ArticleDAO {

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate = new JdbcTemplate();

  Utility utils = new Utility();

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

  public boolean saveArticle(Article article, User user) {
    String SAVE_ARTICLE =
      "INSERT INTO hc_articles (id_hash, name, tags, category, sub_category, content) VALUES (?,?,?,?,?,?)";
    String SAVE_USER_ARTICLE =
      "INSERT INTO hc_user_articles (user_name, article_id) VALUES (?,?)";
    if (isArticlePresent(article)) {
      return false;
    } else {
      try {
        // save article;
        Date x = new Date();

        jdbcTemplate.update(
          SAVE_ARTICLE,
          new Object[] {
            utils.getMd5(
              article.getTitle() +
              article.getUserName() +
              Long.toString(x.getTime())
            ),
            article.getTitle().trim(),
            article.getTags(),
            article.getCategoryCode(),
            article.getSubCategoryCode(),
            utils.getHTMLCompatContent(article.getContent()),
          }
        );

        // for getting inserted article ID
        Article a = getArticle(article);
        jdbcTemplate.update(
          SAVE_USER_ARTICLE,
          new Object[] { user.getUsername(), a.getId() }
        );

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public boolean isArticlePresent(Article article) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_ARTICLE_PRESENT =
      "SELECT COUNT(*) FROM hc_articles WHERE name = ?";
    int count = jdbcTemplate.queryForObject(
      IS_ARTICLE_PRESENT,
      new Object[] { article.getTitle() },
      Integer.class
    );

    if (count != 0) {
      return true;
    }

    return false;
  }

  public boolean isArticlePresent(String IDHash) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_ARTICLE_PRESENT =
      "SELECT COUNT(*) FROM hc_articles WHERE id_hash = ?";
    int count = jdbcTemplate.queryForObject(
      IS_ARTICLE_PRESENT,
      new Object[] { IDHash },
      Integer.class
    );

    if (count != 0) {
      return true;
    }

    return false;
  }

  public Article getArticle(String IDHash) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_ARTICLE =
      "SELECT a.id, a.id_hash, a.name, a.tags, a.content, c.name as cat, c.id AS cat_id, s.name as sub_cat, s.id AS sub_cat_id FROM hc_articles as a JOIN hc_categories as" +
      " c JOIN hc_sub_categories as s WHERE a.id_hash = ? AND a.category = c.id AND a.sub_category = s.id";

    if (isArticlePresent(IDHash)) {
      try {
        Article a = jdbcTemplate.queryForObject(
          GET_ARTICLE,
          new ArticleMapper(),
          IDHash
        );
        System.out.print(a.toString());
        return a;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    System.out.println("No such article present");

    return null;
  }

  public Article getArticle(Article a) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_ARTICLE =
      "SELECT a.id, a.id_hash, a.name, a.tags, a.content, c.name as cat, s.name as sub_cat, c.id as cat_id, s.id as sub_cat_id FROM hc_articles as a JOIN hc_categories as" +
      " c JOIN hc_sub_categories as s WHERE a.name = ? AND a.category = c.id AND a.sub_category = s.id";

    if (isArticlePresent(a)) {
      try {
        return jdbcTemplate.queryForObject(
          GET_ARTICLE,
          new ArticleMapper(),
          a.getTitle()
        );
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  public List<Article> getAllApprovedArticles() {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_ALL_NOT_APPROVED_ARTICLES =
      "SELECT a.id_hash, a.name as title , a.tags, a.id, ud_email, concat(ud_firstname, ud_lastname) as name ," +
      " c.name AS cat, s.name AS sub_cat, a_is_approved FROM hc_articles a JOIN hc_user_details ud JOIN hc_user_articles ua JOIN hc_categories c JOIN " +
      "hc_sub_categories s WHERE c.id = a.category AND s.id = a.sub_category AND ua.user_name = ud.ud_username AND a.id = ua.article_id and a_is_approved = 1";

    try {
      List<Article> articles = jdbcTemplate.query(
        GET_ALL_NOT_APPROVED_ARTICLES,
        new ResultSetExtractor<List<Article>>() {
          public List<Article> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
              Article a = new Article();
              a.setId(rs.getInt("id"));
              a.setName(rs.getString("name"));
              a.setUserName(rs.getString("ud_email"));
              a.setTitle(rs.getString("title"));
              a.setTags(rs.getString("tags"));
              a.setCategory(rs.getString("cat"));
              a.setSubCategory(rs.getString("sub_cat"));
              a.setId_hash(rs.getString("id_hash"));

              list.add(a);
            }
            return list;
          }
        }
      );
      return articles;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public List<Article> getAllNotApprovedArticles() {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_ALL_APPROVED_ARTICLES =
      "SELECT a.id_hash, a.name as title , a.tags, a.id, ud_email, concat(ud_firstname,' ', ud_lastname) as name ," +
      " c.name AS cat, s.name AS sub_cat, a_is_approved FROM hc_articles a JOIN hc_user_details ud JOIN hc_user_articles ua JOIN hc_categories c JOIN " +
      "hc_sub_categories s WHERE c.id = a.category AND s.id = a.sub_category AND ua.user_name = ud.ud_username AND a.id = ua.article_id and a_is_approved = 0 and a_is_active = 1";

    try {
      List<Article> articles = jdbcTemplate.query(
        GET_ALL_APPROVED_ARTICLES,
        new ResultSetExtractor<List<Article>>() {
          public List<Article> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
              Article a = new Article();
              a.setId(rs.getInt("id"));

              a.setName(rs.getString("name"));
              a.setUserName(rs.getString("ud_email"));
              a.setTitle(rs.getString("title"));
              a.setTags(rs.getString("tags"));
              a.setCategory(rs.getString("cat"));
              a.setSubCategory(rs.getString("sub_cat"));
              a.setId_hash(rs.getString("id_hash"));

              list.add(a);
            }
            return list;
          }
        }
      );
      return articles;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public boolean approveArticle(String id) {
    jdbcTemplate.setDataSource(getDataSource());

    String APPROVE_ARTICLE =
      "UPDATE hc_articles set a_is_approved = 1 WHERE id = ? AND a_is_active = 1";
    try {
      jdbcTemplate.update(APPROVE_ARTICLE, id);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean disapproveArticle(String id) {
    jdbcTemplate.setDataSource(getDataSource());

    String DISAPPROVE_ARTICLE =
      "UPDATE hc_articles set a_is_approved = 0 WHERE id = ? AND a_is_active = 1";
    try {
      jdbcTemplate.update(DISAPPROVE_ARTICLE, id);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Article> getSimilarArticles(String IDHash, String tagStr) {
    jdbcTemplate.setDataSource(getDataSource());
    tagStr = tagStr.replace("+", "p");
    String GET_SIMILAR_ARTICLES =
      "SELECT * FROM hc_articles WHERE tags REGEXP ? AND id_hash != ? AND a_is_approved = 1 AND  a_is_active = 1";
    try {
      List<Article> articles = jdbcTemplate.query(
        GET_SIMILAR_ARTICLES,
        new Object[] { tagStr, IDHash },
        new ResultSetExtractor<List<Article>>() {
          public List<Article> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
              Article a = new Article();
              a.setId(rs.getInt("id"));
              a.setId_hash(rs.getString("id_hash"));
              a.setTitle(rs.getString("name"));
              a.setCreated_at(rs.getTimestamp("updated_at"));
              a.setViews(rs.getInt("views"));
              list.add(a);
            }
            return list;
          }
        }
      );
      return articles;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Article getCategoryArticle(String category, String subCategory) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_SPECIAL_ARTICLE =
      "SELECT a.id_hash, a.id, c.name as cat, s.name as sub_cat, a.name, a.content, a.tags, a.updated_at, c.id as cat_id, s.id as sub_cat_id FROM hc_articles a JOIN hc_categories c JOIN hc_sub_categories s where a.category = c.id AND a.sub_category = s.id AND c.name_ = ? AND s.name_ = ? and a.a_is_active = 1 AND a.is_special = 1 AND a.a_is_approved = 1";
    try {
      Article a = jdbcTemplate.queryForObject(
        GET_SPECIAL_ARTICLE,
        new ArticleMapper(),
        category,
        subCategory
      );
      System.out.print(a.toString());
      return a;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Article> getSameCatArticles(String category, String subCategory) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_SAME_CAT_ARTICLES =
      "SELECT a.id_hash, a.id, a.name, a.updated_at FROM hc_articles a JOIN hc_categories c JOIN hc_sub_categories s where a.category = c.id AND a.sub_category = s.id AND c.name_ = ? AND s.name_ = ? and a.a_is_active = 1 AND a.is_special = 0 AND a.a_is_approved = 1";
    try {
      List<Article> articles = jdbcTemplate.query(
        GET_SAME_CAT_ARTICLES,
        new Object[] { category, subCategory },
        new ResultSetExtractor<List<Article>>() {
          public List<Article> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
              Article a = new Article();
              a.setId(rs.getInt("id"));
              a.setId_hash(rs.getString("id_hash"));
              a.setTitle(rs.getString("name"));
              a.setCreated_at(rs.getTimestamp("updated_at"));
              list.add(a);
            }
            return list;
          }
        }
      );
      return articles;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Article> getUserArticles(User user) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USER_ARTICLES =
      "SELECT * FROM hc_user_articles JOIN hc_articles WHERE hc_user_articles.article_id = hc_articles.id AND user_name = ? AND a_is_active = 1";

    if (user.isAdmin()) {
      GET_USER_ARTICLES =
        "SELECT * FROM hc_user_articles JOIN hc_articles WHERE hc_user_articles.article_id = hc_articles.id AND user_name != ? AND a_is_active = 1";
    }

    try {
      List<Article> articles = jdbcTemplate.query(
        GET_USER_ARTICLES,
        new Object[] { user.getUsername() },
        new ResultSetExtractor<List<Article>>() {
          public List<Article> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
              Article a = new Article();
              a.setId(rs.getInt("id"));
              a.setId_hash(rs.getString("id_hash"));
              a.setTitle(rs.getString("name"));
              a.setTags(rs.getString("tags"));
              a.setViews(rs.getInt("views"));
              a.setCreated_at(rs.getTimestamp("updated_at"));

              if (rs.getInt("a_is_approved") == 1) a.setApproved(true);
              else a.setApproved(false);
              list.add(a);
            }
            return list;
          }
        }
      );
      return articles;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Article> getArticlesByTags(String tags) {
    jdbcTemplate.setDataSource(getDataSource());
    String SEARCH_ARTICLES_BY_TAGS =
      "SELECT * FROM hc_articles WHERE tags REGEXP ?";

    tags = tags.replace(" ", "|").replace("+", "p");
    try {
      List<Article> articles = jdbcTemplate.query(
        SEARCH_ARTICLES_BY_TAGS,
        new Object[] { tags },
        new ResultSetExtractor<List<Article>>() {
          public List<Article> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
              Article a = new Article();
              a.setId(rs.getInt("id"));
              a.setId_hash(rs.getString("id_hash"));
              a.setTitle(rs.getString("name"));
              a.setTags(rs.getString("tags"));
              a.setViews(rs.getInt("views"));
              a.setCreated_at(rs.getTimestamp("updated_at"));

              if (rs.getInt("a_is_approved") == 1) a.setApproved(true);
              else a.setApproved(false);
              list.add(a);
            }
            return list;
          }
        }
      );
      return articles;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public void incrementViewCount(String articleIDHash) {
    jdbcTemplate.setDataSource(getDataSource());

    String UPDATE_VIEWS =
      "UPDATE hc_articles SET views = views + 1 WHERE id_hash = ?";

    try {
      jdbcTemplate.update(UPDATE_VIEWS, articleIDHash);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean updateArticle(Article article) {
    jdbcTemplate.setDataSource(getDataSource());
    String UPDATE_ARTICLE =
      "UPDATE hc_articles SET tags = ?, content = ?, category = ?, sub_category = ?, a_is_approved = 0 WHERE name = ?";
    System.out.println(article.toString());
    try {
      jdbcTemplate.update(
        UPDATE_ARTICLE,
        new Object[] {
          article.getTags(),
          article.getContent(),
          article.getCategoryCode(),
          article.getSubCategoryCode(),
          article.getTitle(),
        }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean deleteArticle(String articleHash) {
    jdbcTemplate.setDataSource(getDataSource());
    String DELETE_ARTICLE =
      "UPDATE hc_articles SET a_is_active = 0 WHERE id_hash = ?";

    try {
      jdbcTemplate.update(DELETE_ARTICLE, new Object[] { articleHash });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }
}
