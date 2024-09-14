package com.web.hackercode.structures;

import java.sql.Timestamp;

public class InternshipForm {

  private int id;
  private String firstName;
  private String lastName;
  private long phnNum;
  private String email;
  private String university;
  private String city;
  private String course;
  private int semester;
  private Timestamp appliedAt;
  private boolean isHandled;

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
   * @return the isHandled
   */
  public boolean isHandled() {
    return isHandled;
  }

  /**
   * @param isHandled the isHandled to set
   */
  public void setHandled(boolean isHandled) {
    this.isHandled = isHandled;
  }

  /**
   * @return the appliedAt
   */
  public Timestamp getAppliedAt() {
    return appliedAt;
  }

  /**
   * @param appliedAt the appliedAt to set
   */
  public void setAppliedAt(Timestamp appliedAt) {
    this.appliedAt = appliedAt;
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
   * @return the phnNum
   */
  public long getPhnNum() {
    return phnNum;
  }

  /**
   * @param phnNum the phnNum to set
   */
  public void setPhnNum(long phnNum) {
    this.phnNum = phnNum;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the university
   */
  public String getUniversity() {
    return university;
  }

  /**
   * @param university the university to set
   */
  public void setUniversity(String university) {
    this.university = university;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the course
   */
  public String getCourse() {
    return course;
  }

  /**
   * @param course the course to set
   */
  public void setCourse(String course) {
    this.course = course;
  }

  /**
   * @return the semester
   */
  public int getSemester() {
    return semester;
  }

  /**
   * @param semester the semester to set
   */
  public void setSemester(int semester) {
    this.semester = semester;
  }

  @Override
  public String toString() {
    return (
      "InternshipForm [firstName=" +
      firstName +
      ", lastName=" +
      lastName +
      ", phnNum=" +
      phnNum +
      ", email=" +
      email +
      ", university=" +
      university +
      ", city=" +
      city +
      ", course=" +
      course +
      ", semester=" +
      semester +
      "]"
    );
  }
}
