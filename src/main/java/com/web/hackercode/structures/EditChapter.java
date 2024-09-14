package com.web.hackercode.structures;

public class EditChapter {

  private String courseCode;
  private String code;
  private String name;
  private boolean update;

  /**
   * @return the courseCode
   */
  public String getCourseCode() {
    return courseCode;
  }

  /**
   * @param courseCode the courseCode to set
   */
  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
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
   * @return the update
   */
  public boolean getUpdate() {
    return update;
  }

  /**
   * @param update the update to set
   */
  public void setUpdate(boolean update) {
    this.update = update;
  }

  @Override
  public String toString() {
    return (
      "EditChapter [courseCode=" +
      courseCode +
      ", code=" +
      code +
      ", name=" +
      name +
      ", update=" +
      update +
      "]"
    );
  }
}
