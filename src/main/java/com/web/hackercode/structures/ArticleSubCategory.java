package com.web.hackercode.structures;

public class ArticleSubCategory {

  private int catId;
  private int id;
  private String name;
  private String name_;

  /**
   * @return the name_
   */
  public String getName_() {
    return name_;
  }

  /**
   * @param name_ the name_ to set
   */
  public void setName_(String name_) {
    this.name_ = name_;
  }

  /**
   * @return the catId
   */
  public int getCatId() {
    return catId;
  }

  /**
   * @param catId the catId to set
   */
  public void setCatId(int catId) {
    this.catId = catId;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
}
