package com.web.hackercode.structures;

public class EntityLesson {

  private String name;
  private String code;
  private boolean isFree;
  private String resourse;
  private float duration;

  /**
   * @return the duration
   */
  public float getDuration() {
    return duration;
  }

  /**
   * @return the isFree
   */
  public boolean isFree() {
    return isFree;
  }

  /**
   * @param isFree the isFree to set
   */
  public void setFree(boolean isFree) {
    this.isFree = isFree;
  }

  /**
   * @param duration the duration to set
   */
  public void setDuration(float duration) {
    this.duration = duration;
  }

  /**
   * @return the resourse
   */
  public String getResourse() {
    return resourse;
  }

  /**
   * @param resourse the resourse to set
   */
  public void setResourse(String resourse) {
    this.resourse = resourse;
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
}
