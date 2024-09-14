package com.web.hackercode.structures;

import java.sql.Timestamp;

public class Article {

  private int id;
  private String title;
  private String tags;
  private String content;
  private String categoryCode;
  private String subCategoryCode;
  private String category;
  private String subCategory;
  private String userName;
  private String name;
  private Timestamp created_at;
  private String id_hash;
  private int views;
  private boolean isApproved;
  private boolean doUpdate;

  /**
   * @return the doUpdate
   */
  public boolean isDoUpdate() {
    return doUpdate;
  }

  /**
   * @param doUpdate the doUpdate to set
   */
  public void setDoUpdate(boolean doUpdate) {
    this.doUpdate = doUpdate;
  }

  /**
   * @return the isApproved
   */
  public boolean isApproved() {
    return isApproved;
  }

  /**
   * @param isApproved the isApproved to set
   */
  public void setApproved(boolean isApproved) {
    this.isApproved = isApproved;
  }

  /**
   * @return the views
   */
  public int getViews() {
    return views;
  }

  /**
   * @param views the views to set
   */
  public void setViews(int views) {
    this.views = views;
  }

  /**
   * @return the id_hash
   */
  public String getId_hash() {
    return id_hash;
  }

  /**
   * @param id_hash the id_hash to set
   */
  public void setId_hash(String id_hash) {
    this.id_hash = id_hash;
  }

  /**
   * @return the created_at
   */
  public Timestamp getCreated_at() {
    return created_at;
  }

  /**
   * @param created_at the created_at to set
   */
  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
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

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @return the subCategory
   */
  public String getSubCategory() {
    return subCategory;
  }

  /**
   * @param subCategory the subCategory to set
   */
  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
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
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the tags
   */
  public String getTags() {
    return tags;
  }

  /**
   * @param tags the tags to set
   */
  public void setTags(String tags) {
    this.tags = tags;
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the subCategoryCode
   */
  public String getSubCategoryCode() {
    return subCategoryCode;
  }

  /**
   * @param subCategoryCode the subCategoryCode to set
   */
  public void setSubCategoryCode(String subCategoryCode) {
    this.subCategoryCode = subCategoryCode;
  }

  @Override
  public String toString() {
    return (
      "Article [id=" +
      id +
      ", title=" +
      title +
      ", tags=" +
      tags +
      ", content=" +
      content +
      ", categoryCode=" +
      categoryCode +
      ", subCategoryCode=" +
      subCategoryCode +
      ", category=" +
      category +
      ", subCategory=" +
      subCategory +
      ", userName=" +
      userName +
      ", name=" +
      name +
      ", created_at=" +
      created_at +
      ", id_hash=" +
      id_hash +
      ", views=" +
      views +
      ", isApproved=" +
      isApproved +
      ", doUpdate=" +
      doUpdate +
      "]"
    );
  }
}
