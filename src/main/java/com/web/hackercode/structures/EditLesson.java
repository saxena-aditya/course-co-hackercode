package com.web.hackercode.structures;

public class EditLesson {

  private String courseCode;
  private String chapterCode;
  private String code;
  private String name;
  private String fileUrl;
  private String tags;
  private boolean update;
  private double duration;

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
   * @return the chapterCode
   */
  public String getChapterCode() {
    return chapterCode;
  }

  /**
   * @param chapterCode the chapterCode to set
   */
  public void setChapterCode(String chapterCode) {
    this.chapterCode = chapterCode;
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
   * @return the fileUrl
   */
  public String getFileUrl() {
    return fileUrl;
  }

  /**
   * @param fileUrl the fileUrl to set
   */
  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
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

  /**
   * @return the duration
   */
  public double getDuration() {
    return duration;
  }

  /**
   * @param duration the duration to set
   */
  public void setDuration(double duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return (
      "EditLesson [courseCode=" +
      courseCode +
      ", chapterCode=" +
      chapterCode +
      ", code=" +
      code +
      ", name=" +
      name +
      ", fileUrl=" +
      fileUrl +
      ", update=" +
      update +
      ", duration=" +
      duration +
      "]"
    );
  }
}
