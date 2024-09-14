package com.web.hackercode.dao;

import com.web.hackercode.structures.Course;
import com.web.hackercode.structures.EditChapter;
import com.web.hackercode.structures.EditCourse;
import com.web.hackercode.structures.EditLesson;
import com.web.hackercode.structures.EntityCourse;
import com.web.hackercode.structures.EntityLesson;
import com.web.hackercode.structures.Resource;
import com.web.hackercode.structures.User;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;

public interface CourseDAO {
  public String isCoursePresent(String name);

  public String saveCourse(Course course);

  public boolean saveChapterAndLessons(Course course, String courseCode);

  public String getCourses();

  public List<EntityCourse> getEntityCourses(HttpServletRequest req);

  public Course getCourse(String code);

  public String paymentInit(User user, String courseCode);

  public boolean completePayment(TreeMap<String, String> param);

  public boolean addCourseToUser(String string, String courseCode);

  public List<Course> getUserCourses(String username);

  public List<EntityCourse> getCoursesByTags(String tags);

  public List<EntityLesson> getRelatedVideos(String tags);

  public String getCourseJson(String courseCode);

  public boolean addResource(String name, String source);

  public List<Resource> getResources();

  public boolean markLessonComplete(
    String username,
    String chapterCode,
    String lessoncode
  );

  public int getlLessonCount(String courceCode);

  public int getCompletedLessonCount(String courceCode);

  public boolean updateChapter(EditChapter chapter);

  public boolean makeChapter(EditChapter chapter);

  public boolean updateLesson(EditLesson lesson);

  public boolean makeLesson(EditLesson lesson);

  public boolean deleteEntity(
    String code,
    String tableName,
    String columnName,
    String codeColumn
  );

  public boolean updateCourse(EditCourse course);

  public List<EntityCourse> getSimilarCourse(String articleCode);

  public boolean isCourseSubscribedByUser(String username, String string);
}
