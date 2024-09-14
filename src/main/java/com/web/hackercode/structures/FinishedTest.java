package com.web.hackercode.structures;

public class FinishedTest {

  private String testId;
  private String userId;
  private String ansData;
  private int marks;
  private int maxMarks;
  private String date;
  private String timeRemainingSec;
  private int isFinished;

  /**
   * @return the timeRemainingSec
   */
  public String getTimeRemainingSec() {
    return timeRemainingSec;
  }

  /**
   * @return the maxMarks
   */
  public int getMaxMarks() {
    return maxMarks;
  }

  /**
   * @param maxMarks the maxMarks to set
   */
  public void setMaxMarks(int maxMarks) {
    this.maxMarks = maxMarks;
  }

  /**
   * @param timeRemainingSec the timeRemainingSec to set
   */
  public void setTimeRemainingSec(String timeRemainingSec) {
    this.timeRemainingSec = timeRemainingSec;
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
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @return the ansData
   */
  public String getAnsData() {
    return ansData;
  }

  /**
   * @param ansData the ansData to set
   */
  public void setAnsData(String ansData) {
    this.ansData = ansData;
  }

  /**
   * @return the marks
   */
  public int getMarks() {
    return marks;
  }

  /**
   * @param marks the marks to set
   */
  public void setMarks(int marks) {
    this.marks = marks;
  }

  /**
   * @return the date
   */
  public String getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * @return the isFinished
   */
  public int getIsFinished() {
    return isFinished;
  }

  /**
   * @param isFinished the isFinished to set
   */
  public void setIsFinished(int isFinished) {
    this.isFinished = isFinished;
  }
}
