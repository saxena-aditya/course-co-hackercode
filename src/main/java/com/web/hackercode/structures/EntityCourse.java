package com.web.hackercode.structures;

public class EntityCourse {

  private String name;
  private String code;
  private int price;
  private int days;
  private String desc;
  private String tags;
  private int mrp;
  private String creator;
  private String subDesc;
  private String intro;
  private String cover;
  private int lessonCount;
  private boolean isTestSeries;

  /**
   * @return the isTestSeries
   */
  public boolean isTestSeries() {
    return isTestSeries;
  }

  /**
   * @param isTestSeries the isTestSeries to set
   */
  public void setTestSeries(boolean isTestSeries) {
    this.isTestSeries = isTestSeries;
  }

  /**
   * @return the lessonCount
   */
  public int getLessonCount() {
    return lessonCount;
  }

  /**
   * @param lessonCount the lessonCount to set
   */
  public void setLessonCount(int lessonCount) {
    this.lessonCount = lessonCount;
  }

  /**
   * @return the intro
   */
  public String getIntro() {
    return intro;
  }

  /**
   * @param intro the intro to set
   */
  public void setIntro(String intro) {
    this.intro = intro;
  }

  /**
   * @return the cover
   */
  public String getCover() {
    return cover;
  }

  /**
   * @param cover the cover to set
   */
  public void setCover(String cover) {
    this.cover = cover;
  }

  /**
   * @return the subDesc
   */
  public String getSubDesc() {
    return subDesc;
  }

  /**
   * @param subDesc the subDesc to set
   */
  public void setSubDesc(String subDesc) {
    this.subDesc = subDesc;
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
   * @return the mrp
   */
  public int getMrp() {
    return mrp;
  }

  /**
   * @param mrp the mrp to set
   */
  public void setMrp(int mrp) {
    this.mrp = mrp;
  }

  /**
   * @return the desc
   */
  public String getDesc() {
    return desc;
  }

  /**
   * @param desc the desc to set
   */
  public void setDesc(String desc) {
    this.desc = desc;
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
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the price
   */
  public int getPrice() {
    return price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * @return the days
   */
  public int getDays() {
    return days;
  }

  /**
   * @param days the days to set
   */
  public void setDays(int days) {
    this.days = days;
  }

  /**
   * @return the creator
   */
  public String getCreator() {
    return creator;
  }

  /**
   * @param creator the creator to set
   */
  public void setCreator(String creator) {
    this.creator = creator;
  }
}
