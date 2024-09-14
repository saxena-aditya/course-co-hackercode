package com.web.hackercode.structures;

import org.springframework.web.multipart.MultipartFile;

public class Test {

  private String testId;
  private String name;
  private String admin;
  private String code;
  private String password;
  private String startTime;
  private String endTime;
  private int totalTimeHours;
  private int totalTimeMinutes;
  private int totalTime;
  private String isTimeStrict;
  private String isAnsShuffle;
  private String courseCode;
  private MultipartFile file;
  private String filePath;

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
   * @return the filePath
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * @param filePath the filePath to set
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the file
   */
  public MultipartFile getFile() {
    return file;
  }

  /**
   * @param file the file to set
   */
  public void setFile(MultipartFile file) {
    this.file = file;
  }

  /**
   * @return the testId
   */
  public String getTestId() {
    return testId;
  }

  /**
   * @param testId the testId to set
   */
  public void setTestId(String testId) {
    this.testId = testId;
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
   * @return the admin
   */
  public String getAdmin() {
    return admin;
  }

  /**
   * @param admin the admin to set
   */
  public void setAdmin(String admin) {
    this.admin = admin;
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
   * @return the startTime
   */
  public String getStartTime() {
    return startTime;
  }

  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  /**
   * @return the endTime
   */
  public String getEndTime() {
    return endTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  /**
   * @return the isTimeStrict
   */
  public String getIsTimeStrict() {
    return isTimeStrict;
  }

  /**
   * @param isTimeStrict the isTimeStrict to set
   */
  public void setIsTimeStrict(String isTimeStrict) {
    this.isTimeStrict = isTimeStrict;
  }

  /**
   * @return the isAnsShuffle
   */
  public String getIsAnsShuffle() {
    return isAnsShuffle;
  }

  /**
   * @param isAnsShuffle the isAnsShuffle to set
   */
  public void setIsAnsShuffle(String isAnsShuffle) {
    this.isAnsShuffle = isAnsShuffle;
  }

  public int getTotalTimeHours() {
    return totalTimeHours;
  }

  public void setTotalTimeHours(int totalTimeHours) {
    this.totalTimeHours = totalTimeHours;
  }

  public int getTotalTimeMinutes() {
    return totalTimeMinutes;
  }

  public void setTotalTimeMinutes(int totalTimeMinutes) {
    this.totalTimeMinutes = totalTimeMinutes;
  }

  public int getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(int totalTime) {
    this.totalTime = totalTime;
  }

  public String toString() {
    return (
      "Admin: " +
      this.getAdmin() +
      "\n Name: " +
      this.getName() +
      "\n Code: " +
      this.getCode() +
      "\n Start Time: " +
      this.getStartTime() +
      "\n End Time: " +
      this.getEndTime() +
      "\n isAnsShuffle: " +
      this.getIsAnsShuffle() +
      "\n isTimeStrict: " +
      this.getIsTimeStrict() +
      "\n Associated to :" +
      this.getCourseCode() +
      "\n Time(h): " +
      this.getTotalTimeHours() +
      "\n Time(min): " +
      this.getTotalTimeMinutes()
    );
  }
}
