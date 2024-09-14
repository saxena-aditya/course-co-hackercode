package com.web.hackercode.structures;

public class EditCourse {

  private String code;
  private String name;
  private int mrp;
  private int price;
  private int days;
  private String tags;
  private String desc;
  private String quickDesc;
  private boolean update;

  @Override
  public String toString() {
    return (
      "EditCourse [code=" +
      code +
      ", name=" +
      name +
      ", mrp=" +
      mrp +
      ", price=" +
      price +
      ", days=" +
      days +
      ", tags=" +
      tags +
      ", desc=" +
      desc +
      ", quickDesc=" +
      quickDesc +
      ", update=" +
      update +
      "]"
    );
  }

  /**
   * @return the update
   */
  public boolean isUpdate() {
    return update;
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
   * @param update the update to set
   */
  public void setUpdate(boolean update) {
    this.update = update;
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
   * @return the quickDesc
   */
  public String getQuickDesc() {
    return quickDesc;
  }

  /**
   * @param quickDesc the quickDesc to set
   */
  public void setQuickDesc(String quickDesc) {
    this.quickDesc = quickDesc;
  }
}
