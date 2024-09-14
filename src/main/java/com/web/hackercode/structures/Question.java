package com.web.hackercode.structures;

public class Question {

  private int testId;
  private int questionId;
  private String questionSet;
  private String questionTag;
  private String questionType;
  private String questionContent;
  private int questionMaxMarks;
  private int questionNegMarks;
  private String questionOptions;
  private String questionAns;
  private String questionScope;
  private boolean isQuestionNew;

  // for reports and stuff.
  private String status;
  private boolean isAnswered;
  private String userAns;

  /**
   * @return the userAns
   */
  public String getUserAns() {
    return userAns;
  }

  /**
   * @param userAns the userAns to set
   */
  public void setUserAns(String userAns) {
    this.userAns = userAns;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the isAnswered
   */
  public boolean isAnswered() {
    return isAnswered;
  }

  /**
   * @param isAnswered the isAnswered to set
   */
  public void setAnswered(boolean isAnswered) {
    this.isAnswered = isAnswered;
  }

  /**
   * @return the isQuestionNew
   */
  public boolean isQuestionNew() {
    return isQuestionNew;
  }

  /**
   * @param isQuestionNew the isQuestionNew to set
   */
  public void setQuestionNew(boolean isQuestionNew) {
    this.isQuestionNew = isQuestionNew;
  }

  /**
   * @return the questionId
   */
  public int getQuestionId() {
    return questionId;
  }

  /**
   * @param questionId the questionId to set
   */
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  /**
   * @return the testId
   */
  public int getTestId() {
    return testId;
  }

  /**
   * @param testId the testId to set
   */
  public void setTestId(int testId) {
    this.testId = testId;
  }

  /**
   * @return the questionSet
   */
  public String getQuestionSet() {
    return questionSet;
  }

  /**
   * @param questionSet the questionSet to set
   */
  public void setQuestionSet(String questionSet) {
    this.questionSet = questionSet;
  }

  /**
   * @return the questionTag
   */
  public String getQuestionTag() {
    return questionTag;
  }

  /**
   * @param questionTag the questionTag to set
   */
  public void setQuestionTag(String questionTag) {
    this.questionTag = questionTag;
  }

  /**
   * @return the questionType
   */
  public String getQuestionType() {
    return questionType;
  }

  /**
   * @param questionType the questionType to set
   */
  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }

  /**
   * @return the questionContent
   */
  public String getQuestionContent() {
    return questionContent;
  }

  /**
   * @param questionContent the questionContent to set
   */
  public void setQuestionContent(String questionContent) {
    this.questionContent = questionContent;
  }

  /**
   * @return the questionMaxMarks
   */
  public int getQuestionMaxMarks() {
    return questionMaxMarks;
  }

  /**
   * @param questionMaxMarks the questionMaxMarks to set
   */
  public void setQuestionMaxMarks(int questionMaxMarks) {
    this.questionMaxMarks = questionMaxMarks;
  }

  /**
   * @return the questionNegMarks
   */
  public int getQuestionNegMarks() {
    return questionNegMarks;
  }

  /**
   * @param questionNegMarks the questionNegMarks to set
   */
  public void setQuestionNegMarks(int questionNegMarks) {
    this.questionNegMarks = questionNegMarks;
  }

  /**
   * @return the questionOptions
   */
  public String getQuestionOptions() {
    return questionOptions;
  }

  /**
   * @param questionOptions the questionOptions to set
   */
  public void setQuestionOptions(String questionOptions) {
    this.questionOptions = questionOptions;
  }

  /**
   * @return the questionAns
   */
  public String getQuestionAns() {
    return questionAns;
  }

  /**
   * @param questionAns the questionAns to set
   */
  public void setQuestionAns(String questionAns) {
    this.questionAns = questionAns;
  }

  /**
   * @return the questionScope
   */
  public String getQuestionScope() {
    return questionScope;
  }

  /**
   * @param questionScope the questionScope to set
   */
  public void setQuestionScope(String questionScope) {
    this.questionScope = questionScope;
  }

  @Override
  public String toString() {
    return (
      "Question [testId=" +
      testId +
      ", questionId=" +
      questionId +
      ", questionSet=" +
      questionSet +
      ", questionTag=" +
      questionTag +
      ", questionType=" +
      questionType +
      ", questionContent=" +
      questionContent +
      ", questionMaxMarks=" +
      questionMaxMarks +
      ", questionNegMarks=" +
      questionNegMarks +
      ", questionOptions=" +
      questionOptions +
      ", questionAns=" +
      questionAns +
      ", questionScope=" +
      questionScope +
      ", isQuestionNew=" +
      isQuestionNew +
      ", status=" +
      status +
      ", isAnswered=" +
      isAnswered +
      ", userAns=" +
      userAns +
      "]"
    );
  }
}
