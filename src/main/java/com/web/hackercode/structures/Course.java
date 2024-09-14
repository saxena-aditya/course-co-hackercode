package com.web.hackercode.structures;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class Course {

  private String name;
  private String code;
  private int price;
  private String desc;
  private int totalDays;
  private String creator;
  private String lesson;

  private String isFree;
  private String lessonTags;
  private String isCourseFree;

  private String chapter;
  private String tags;
  private int mrp;
  private String subDesc;
  private MultipartFile img;
  private int totalLessons;
  private int completedLessons;
  private List<String> files;
  private List<Float> duration;
  private String cover;
  private String intro;
  private boolean isTestSeries;
  private int lessonCount;

  /**
   * @return the isCourseFree
   */
  public String getIsCourseFree() {
    return isCourseFree;
  }

  /**
   * @param isCourseFree the isCourseFree to set
   */
  public void setIsCourseFree(String isCourseFree) {
    this.isCourseFree = isCourseFree;
  }

  /**
   * @return the lessonCount
   */
  public int getLessonCount() {
    return lessonCount;
  }

  /**
   * @param lessonCount the lessonCount to set
   */
  public void setLessonCount(int lessonCount) {
    this.lessonCount = lessonCount;
  }

  /**
   * @return the isTestSeries
   */
  public boolean isTestSeries() {
    return isTestSeries;
  }

  /**
   * @param isTestSeries the isTestSeries to set
   */
  public void setTestSeries(boolean isTestSeries) {
    this.isTestSeries = isTestSeries;
  }

  /**
   * @return the cover
   */
  public String getCover() {
    return cover;
  }

  /**
   * @param cover the cover to set
   */
  public void setCover(String cover) {
    this.cover = cover;
  }

  /**
   * @return the intro
   */
  public String getIntro() {
    return intro;
  }

  /**
   * @param intro the intro to set
   */
  public void setIntro(String intro) {
    this.intro = intro;
  }

  /**
   * @return the duration
   */
  public List<Float> getDuration() {
    return duration;
  }

  /**
   * @param duration the duration to set
   */
  public void setDuration(List<Float> duration) {
    this.duration = duration;
  }

  /**
   * @return the totalLessons
   */
  public int getTotalLessons() {
    return totalLessons;
  }

  /**
   * @param totalLessons the totalLessons to set
   */
  public void setTotalLessons(int totalLessons) {
    this.totalLessons = totalLessons;
  }

  /**
   * @return the completedLessons
   */
  public int getCompletedLessons() {
    return completedLessons;
  }

  /**
   * @param completedLessons the completedLessons to set
   */
  public void setCompletedLessons(int completedLessons) {
    this.completedLessons = completedLessons;
  }

  /**
   * @param files the files to set
   */
  public void setFiles(List<String> files) {
    this.files = files;
  }

  /**
   * @return the img
   */
  public MultipartFile getImg() {
    return img;
  }

  /**
   * @param img the img to set
   */
  public void setImg(MultipartFile img) {
    this.img = img;
  }

  /**
   * @return the subDesc
   */
  public String getSubDesc() {
    return subDesc;
  }

  /**
   * @param subDesc the subDesc to set
   */
  public void setSubDesc(String subDesc) {
    this.subDesc = subDesc;
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
   * @return the totalDays
   */
  public int getTotalDays() {
    return totalDays;
  }

  /**
   * @param totalDays the totalDays to set
   */
  public void setTotalDays(int totalDays) {
    this.totalDays = totalDays;
  }

  /**
   * @return the chapter
   */
  public String getChapter() {
    return chapter;
  }

  /**
   * @param chapter the chapter to set
   */
  public void setChapter(String chapter) {
    this.chapter = chapter;
  }

  /**
   * @return the lesson
   */
  public String getLesson() {
    return lesson;
  }

  /**
   * @param lesson the lesson to set
   */
  public void setLesson(String lesson) {
    this.lesson = lesson;
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
   * @return the creator
   */
  public String getCreator() {
    return creator;
  }

  /**
   * @param creator the creator to set
   */
  public void setCreator(String creator) {
    this.creator = creator;
  }

  /**
   * @return the files
   */
  public List<String> getFiles() {
    return files;
  }

  /**
   * @return the isFree
   */
  public String getIsFree() {
    return isFree;
  }

  /**
   * @param isFree the isFree to set
   */
  public void setIsFree(String isFree) {
    this.isFree = isFree;
  }

  public String toString() {
    return (
      "course name: " +
      getName() +
      "\nchapter name: " +
      getChapter() +
      "\nlesson name: " +
      getLesson() +
      "\nisLessonFree: " +
      getIsFree() +
      "\nfile size: " +
      getFiles().size()
    );
  }

  /**
   * @return the lessonTags
   */
  public String getLessonTags() {
    return lessonTags;
  }

  /**
   * @param lessonTags the lessonTags to set
   */
  public void setLessonTags(String lessonTags) {
    this.lessonTags = lessonTags;
  }
}
