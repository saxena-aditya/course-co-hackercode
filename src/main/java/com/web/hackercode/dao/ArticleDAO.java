package com.web.hackercode.dao;

import com.web.hackercode.structures.Article;
import com.web.hackercode.structures.ArticleCategory;
import com.web.hackercode.structures.ArticleSubCategory;
import com.web.hackercode.structures.User;
import java.util.List;

public interface ArticleDAO {
  public boolean saveArticle(Article article, User user);

  public boolean isArticlePresent(String id);

  public boolean isArticlePresent(Article article);

  public Article getArticle(String id);

  public Article getArticle(Article a);

  public List<Article> getAllApprovedArticles();

  public List<Article> getAllNotApprovedArticles();

  public boolean approveArticle(String id);

  public boolean disapproveArticle(String id);

  public List<Article> getSimilarArticles(String id, String tagStr);

  public Article getCategoryArticle(String category, String subCategory);

  public List<Article> getSameCatArticles(String category, String subCategory);

  public List<Article> getUserArticles(User user);

  public List<Article> getArticlesByTags(String tags);

  public void incrementViewCount(String articleIDHash);

  public boolean updateArticle(Article article);

  public boolean deleteArticle(String articleHash);
}
