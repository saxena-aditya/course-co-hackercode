package com.web.hackercode.structures;

public class TestUser {

  private String userId;
  private String testName;
  private String testCode;
  private String testId;
  private String data;
  private String timeLeft;
  private int marks;
  private boolean isTestFinished;
  private int maxMarks;
  private String date;
  private String username;
  private String firstName;
  private String lastName;
  private String institue;
  private String pic;
  private String endDate;

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the pic
   */
  public String getPic() {
    return pic;
  }

  /**
   * @param pic the pic to set
   */
  public void setPic(String pic) {
    this.pic = pic;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the testCode
   */
  public String getTestCode() {
    return testCode;
  }

  /**
   * @param testCode the testCode to set
   */
  public void setTestCode(String testCode) {
    this.testCode = testCode;
  }

  /**
   * @return the testName
   */
  public String getTestName() {
    return testName;
  }

  /**
   * @param testName the testName to set
   */
  public void setTestName(String testName) {
    this.testName = testName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getInstitue() {
    return institue;
  }

  public void setInstitue(String institueName) {
    this.institue = institueName;
  }

  public int getMaxMarks() {
    return maxMarks;
  }

  public void setMaxMarks(int maxMarks) {
    this.maxMarks = maxMarks;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getMarks() {
    return marks;
  }

  public void setMarks(int marks) {
    this.marks = marks;
  }

  public boolean isTestFinished() {
    return isTestFinished;
  }

  public void setTestFinished(boolean isTestFinished) {
    this.isTestFinished = isTestFinished;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTestId() {
    return testId;
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getTimeLeft() {
    return timeLeft;
  }

  public void setTimeLeft(String timeLeft) {
    this.timeLeft = timeLeft;
  }

  public String toString() {
    return "id : " + userId + " " + testId + " " + data;
  }
}
