package com.web.hackercode.structures;

import java.util.Arrays;

public class Register {

  private String fName;
  private String lName;
  private String username;
  private String email;
  private String password;
  private String course;
  private String[] programs;
  private String phone;
  private String institute;
  private boolean isAdmin;
  private boolean isDrafter;
  private String[] courses;

  /**
   * @return the courses
   */
  public String[] getCourses() {
    return courses;
  }

  /**
   * @param courses the courses to set
   */
  public void setCourses(String[] courses) {
    this.courses = courses;
  }

  /**
   * @return the isAdmin
   */
  public boolean isAdmin() {
    return isAdmin;
  }

  /**
   * @param isAdmin the isAdmin to set
   */
  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  /**
   * @return the isDrafter
   */
  public boolean isDrafter() {
    return isDrafter;
  }

  /**
   * @param isDrafter the isDrafter to set
   */
  public void setDrafter(boolean isDrafter) {
    this.isDrafter = isDrafter;
  }

  /**
   * @return the institute
   */
  public String getInstitute() {
    return institute;
  }

  /**
   * @param institute the institute to set
   */
  public void setInstitute(String institute) {
    this.institute = institute;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String[] getPrograms() {
    return programs;
  }

  public void setPrograms(String[] programs) {
    this.programs = programs;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getlName() {
    return lName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }

  @Override
  public String toString() {
    return (
      "Register [fName=" +
      fName +
      ", lName=" +
      lName +
      ", username=" +
      username +
      ", email=" +
      email +
      ", password=" +
      password +
      ", course=" +
      course +
      ", programs=" +
      Arrays.toString(programs) +
      ", phone=" +
      phone +
      ", institute=" +
      institute +
      ", isAdmin=" +
      isAdmin +
      ", isDrafter=" +
      isDrafter +
      ", courses=" +
      Arrays.toString(courses) +
      "]"
    );
  }
}
