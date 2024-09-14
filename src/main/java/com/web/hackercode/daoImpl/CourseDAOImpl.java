package com.web.hackercode.daoImpl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.hackercode.dao.CourseDAO;
import com.web.hackercode.mappers.CourseMapper;
import com.web.hackercode.structures.Course;
import com.web.hackercode.structures.EditChapter;
import com.web.hackercode.structures.EditCourse;
import com.web.hackercode.structures.EditLesson;
import com.web.hackercode.structures.EntityChapter;
import com.web.hackercode.structures.EntityCourse;
import com.web.hackercode.structures.EntityLesson;
import com.web.hackercode.structures.Resource;
import com.web.hackercode.structures.TestUser;
import com.web.hackercode.structures.User;
import com.web.hackercode.utility.Utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CourseDAOImpl implements CourseDAO {

  private static final String ALPHA_NUMERIC_STRING =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate = new JdbcTemplate();

  Utility utils = new Utility();

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public static String randomAlphaNumeric(int count) {
    StringBuilder builder = new StringBuilder();

    while (count-- != 0) {
      int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }

    return builder.toString();
  }

  private File convert(MultipartFile file) throws IOException {
    File convFile = new File("A:\\" + file.getOriginalFilename());
    System.out.println(file.getOriginalFilename());
    convFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  private String uploadVideo(MultipartFile file) throws IOException {
    AWSCredentials credentials = new BasicAWSCredentials(
      "<short-client-id>",
      "<long-api-key>"
    );
    long contentLength = file.getSize();
    long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
    String bucketName = "hcvideo212";
    String keyName = randomAlphaNumeric(10) + file.getOriginalFilename();
    String domain = "https://do4k6lnx3y4m9.cloudfront.net/";
    try {
      AmazonS3 s3client = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.AP_SOUTH_1)
        .build();

      // Create a list of ETag objects. You retrieve ETags for each object part uploaded,
      // then, after each individual part has been uploaded, pass the list of ETags to
      // the request to complete the upload.
      List<PartETag> partETags = new ArrayList<PartETag>();
      // Initiate the multipart upload.
      InitiateMultipartUploadRequest initRequest =
        new InitiateMultipartUploadRequest(bucketName, keyName);
      InitiateMultipartUploadResult initResponse =
        s3client.initiateMultipartUpload(initRequest);
      long filePosition = 0;
      File fileType = convert(file);
      for (int i = 1; filePosition < contentLength; i++) {
        // Because the last part could be less than 5 MB, adjust the part size as needed.
        partSize = Math.min(partSize, (contentLength - filePosition));

        // Create the request to upload a part.
        UploadPartRequest uploadRequest = new UploadPartRequest()
          .withBucketName(bucketName)
          .withKey(keyName)
          .withUploadId(initResponse.getUploadId())
          .withPartNumber(i)
          .withFileOffset(filePosition)
          .withFile(fileType)
          .withPartSize(partSize);

        // Upload the part and add the response's ETag to our list.
        UploadPartResult uploadResult = s3client.uploadPart(uploadRequest);
        partETags.add(uploadResult.getPartETag());

        filePosition += partSize;
      }

      CompleteMultipartUploadRequest compRequest =
        new CompleteMultipartUploadRequest(
          bucketName,
          keyName,
          initResponse.getUploadId(),
          partETags
        );
      s3client.completeMultipartUpload(compRequest);
      // delete temp file
      fileType.delete();
      return domain + keyName;
    } catch (AmazonServiceException e) {
      // The call was transmitted successfully, but Amazon S3 couldn't process
      // it, so it returned an error response.
      e.printStackTrace();
    } catch (SdkClientException e) {
      // Amazon S3 couldn't be contacted for a response, or the client
      // couldn't parse the response from Amazon S3.
      e.printStackTrace();
    }

    return null;
  }

  public String isCoursePresent(String name) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_COURSE_PRESENT =
      "SELECT count(*) FROM hc_courses WHERE c_name = ? AND c_is_active = 1";
    String GET_COURSE_CODE =
      "SELECT c_code FROM hc_courses WHERE c_name = ? AND c_is_active = 1";
    System.out.println("getting course code..." + name);
    int count = jdbcTemplate.queryForObject(
      IS_COURSE_PRESENT,
      new Object[] { name },
      Integer.class
    );
    System.out.println("got course count..." + name + " - -" + count);

    if (count == 1) {
      String code = jdbcTemplate.queryForObject(
        GET_COURSE_CODE,
        new Object[] { name },
        String.class
      );
      return code;
    }

    System.out.println("returning null from isCoursePresent...");
    return null;
  }

  public String saveCourse(Course course) {
    jdbcTemplate.setDataSource(getDataSource());
    String SAVE_COURSE =
      "INSERT INTO hc_courses (c_name, c_code, c_desc, c_price, c_total_days, c_tags, c_mrp, c_sub_desc, c_cover, c_intro, c_is_free) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    try {
      System.out.println("Saving course...: " + course.getName());
      String code = randomAlphaNumeric(6);

      jdbcTemplate.update(
        SAVE_COURSE,
        course.getName(),
        code,
        course.getDesc(),
        course.getPrice(),
        course.getTotalDays(),
        course.getTags(),
        course.getMrp(),
        course.getSubDesc(),
        course.getCover(),
        course.getIntro(),
        course.getIsCourseFree()
      );

      System.out.println("getting course code...: ");
      return code;
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("returning NULL from savecourse...");
    return null;
  }

  public String isChapterPresent(String name, String courseCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_COURSE_PRESENT =
      "SELECT count(*) FROM hc_chapters WHERE ch_name = ? AND ch_c_code = ? AND ch_is_active = 1";
    String GET_COURSE_CODE =
      "SELECT ch_code FROM hc_chapters WHERE ch_name = ? AND ch_is_active = 1";

    int count = jdbcTemplate.queryForObject(
      IS_COURSE_PRESENT,
      new Object[] { name, courseCode },
      Integer.class
    );

    if (count == 1) {
      String code = jdbcTemplate.queryForObject(
        GET_COURSE_CODE,
        new Object[] { name },
        String.class
      );
      return code;
    }

    return null;
  }

  public boolean saveChapterAndLessons(Course course, String courseCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String SAVE_CHAPTER =
      "INSERT INTO hc_chapters (ch_c_code, ch_name, ch_code) VALUES (?,?,?)";
    String SAVE_LESSION =
      "INSERT INTO hc_lessons (l_code, l_ch_code, l_c_code, l_name, l_resource, l_duration, l_is_free, l_tags) VALUES (?,?,?,?,?,?,?,?)";
    String domain = "https://do4k6lnx3y4m9.cloudfront.net/";

    String chCode = isChapterPresent(course.getChapter(), courseCode);

    if (chCode == null) {
      // save chapter and lesson;
      chCode = randomAlphaNumeric(7);
      try {
        jdbcTemplate.update(
          SAVE_CHAPTER,
          courseCode,
          course.getChapter(),
          chCode
        );
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    try {
      System.out.println("Size of video : " + course.getFiles().size());

      // one lesson will have only on resource attached to it.
      for (int i = 0;;) {
        String location = domain + course.getFiles().get(i);
        /*
         * System.out.println(randomAlphaNumeric(7) + " | " + chCode + " | " +
         * courseCode + " | " + course.getLesson() + " | " + location + " | " +
         * course.getDuration().get(i) + "|" + course.getIsFree());
         */
        jdbcTemplate.update(
          SAVE_LESSION,
          randomAlphaNumeric(7),
          chCode,
          courseCode,
          course.getLesson(),
          location,
          course.getDuration().get(i),
          course.getIsFree(),
          course.getLessonTags()
        );

        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  private List<EntityLesson> getEntityLesson(String chCode) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_CHAPTER =
      "SELECT * FROM hc_lessons WHERE l_ch_code = ? AND l_is_active = 1";
    List<EntityLesson> lessons = jdbcTemplate.query(
      GET_CHAPTER,
      new Object[] { chCode },
      new ResultSetExtractor<List<EntityLesson>>() {
        public List<EntityLesson> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<EntityLesson> list = new ArrayList<EntityLesson>();
          while (rs.next()) {
            EntityLesson el = new EntityLesson();
            el.setName(rs.getString("l_name"));
            el.setCode(rs.getString("l_code"));
            el.setResourse(rs.getString("l_resource"));
            el.setDuration(rs.getFloat("l_duration"));

            if (rs.getString("l_is_free").equalsIgnoreCase("1")) el.setFree(
              true
            );
            else el.setFree(false);

            list.add(el);
          }
          return list;
        }
      }
    );

    return lessons;
  }

  public List<EntityCourse> getEntityCourses(HttpServletRequest req) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_COURSES = "SELECT * FROM hc_courses WHERE c_is_active = 1";

    if (req != null) {
      if (req.getSession() != null) {
        if (req.getSession().getAttribute("user") != null) {
          User user = (User) req.getSession().getAttribute("user");
          GET_COURSES =
            "SELECT * FROM hc_courses AS c WHERE NOT EXISTS ( SELECT * " +
            "FROM hc_user_program as pg WHERE pg.up_code = c.c_code AND pg.up_username = ? AND c.c_is_active = 1)";
          try {
            List<EntityCourse> courses = jdbcTemplate.query(
              GET_COURSES,
              new Object[] { user.getUsername() },
              new ResultSetExtractor<List<EntityCourse>>() {
                public List<EntityCourse> extractData(ResultSet rs)
                  throws SQLException, DataAccessException {
                  List<EntityCourse> list = new ArrayList<EntityCourse>();
                  while (rs.next()) {
                    EntityCourse ec = new EntityCourse();
                    ec.setName(rs.getString("c_name"));
                    ec.setCode(rs.getString("c_code"));
                    ec.setTags(rs.getString("c_tags"));
                    ec.setMrp(rs.getInt("c_mrp"));
                    ec.setSubDesc(rs.getString("c_sub_desc"));

                    ec.setPrice(rs.getInt("c_price"));
                    ec.setDesc(rs.getString("c_desc"));
                    ec.setDays(rs.getInt("c_total_days"));
                    ec.setIntro(rs.getString("c_intro"));
                    ec.setCover(rs.getString("c_cover"));
                    ec.setLessonCount(getlLessonCount(rs.getString("c_code")));

                    if (utils.isTestSeries(rs.getString("c_name"))) {
                      ec.setTestSeries(true);
                    } else {
                      ec.setTestSeries(false);
                    }

                    list.add(ec);
                  }
                  return list;
                }
              }
            );

            return courses;
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }

    List<EntityCourse> courses = jdbcTemplate.query(
      GET_COURSES,
      new ResultSetExtractor<List<EntityCourse>>() {
        public List<EntityCourse> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<EntityCourse> list = new ArrayList<EntityCourse>();
          while (rs.next()) {
            EntityCourse ec = new EntityCourse();
            ec.setName(rs.getString("c_name"));
            ec.setCode(rs.getString("c_code"));
            ec.setTags(rs.getString("c_tags"));
            ec.setMrp(rs.getInt("c_mrp"));
            ec.setSubDesc(rs.getString("c_sub_desc"));
            ec.setPrice(rs.getInt("c_price"));
            ec.setDesc(rs.getString("c_desc"));
            ec.setDays(rs.getInt("c_total_days"));
            ec.setIntro(rs.getString("c_intro"));
            ec.setCover(rs.getString("c_cover"));
            ec.setLessonCount(getlLessonCount(rs.getString("c_code")));

            if (utils.isTestSeries(rs.getString("c_name"))) {
              ec.setTestSeries(true);
            } else {
              ec.setTestSeries(false);
            }

            list.add(ec);
          }
          return list;
        }
      }
    );

    return courses;
  }

  private List<EntityChapter> getEntityChapters(String cCode) {
    String GET_CHAPTER =
      "SELECT * FROM hc_chapters WHERE ch_c_code = ? AND ch_is_active = 1";
    List<EntityChapter> chapters = null;
    try {
      chapters = jdbcTemplate.query(
        GET_CHAPTER,
        new Object[] { cCode },
        new ResultSetExtractor<List<EntityChapter>>() {
          public List<EntityChapter> extractData(ResultSet rs)
            throws SQLException, DataAccessException, EmptyResultDataAccessException {
            List<EntityChapter> list = new ArrayList<EntityChapter>();
            while (rs.next()) {
              EntityChapter ec = new EntityChapter();
              ec.setName(rs.getString("ch_name"));
              ec.setCode(rs.getString("ch_code"));
              ec.setCourseCode(rs.getString("ch_c_code"));

              list.add(ec);
            }
            return list;
          }
        }
      );
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    return chapters;
  }

  public String getCourseJson(String courseCode) {
    Course ecourse = getCourse(courseCode);
    JsonArray root = new JsonArray();

    JsonObject coursesJ = new JsonObject();
    JsonArray jc = new JsonArray(); // chapter array obj

    if (ecourse != null) {
      List<EntityChapter> chapters = getEntityChapters(ecourse.getCode());
      if (chapters != null) {
        for (int j = 0; j < chapters.size(); j++) {
          EntityChapter echapter = chapters.get(j);
          List<EntityLesson> lessons = getEntityLesson(echapter.getCode());
          JsonArray jLessons = new JsonArray();

          for (int k = 0; k < lessons.size(); k++) {
            JsonObject jj = new JsonObject();
            jj.addProperty("name", lessons.get(k).getName());
            jj.addProperty("code", lessons.get(k).getCode());
            jj.addProperty("isFree", lessons.get(k).isFree());
            jj.addProperty("resourse", lessons.get(k).getResourse());
            jj.addProperty("duration", lessons.get(k).getDuration());
            jLessons.add(jj);
          }
          JsonObject jcc = new JsonObject();

          jcc.addProperty("name", echapter.getName());
          jcc.addProperty("code", echapter.getCode());
          jcc.add("lessons", jLessons);
          jc.add(jcc);
        }
        coursesJ.addProperty("name", ecourse.getName());
        coursesJ.addProperty("code", ecourse.getCode());
        coursesJ.addProperty("creator", ecourse.getCreator());
        coursesJ.addProperty("total_days", ecourse.getTotalDays());
        coursesJ.addProperty("price", ecourse.getPrice());
        coursesJ.addProperty("desc", ecourse.getDesc());
        coursesJ.addProperty("quick_desc", ecourse.getSubDesc());
        coursesJ.addProperty("tags", ecourse.getTags());
        coursesJ.addProperty("mrp", ecourse.getMrp());
        coursesJ.addProperty("days", ecourse.getTotalDays());

        coursesJ.addProperty("intro", ecourse.getIntro());
        coursesJ.addProperty("cover", ecourse.getCover());
        coursesJ.add("chapters", jc);
        root.add(coursesJ);
      }
    }

    return root.toString();
  }

  public Course getCourse(String courseCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_COURSE =
      "SELECT * FROM hc_courses WHERE c_code = ? AND c_is_active = 1";
    try {
      Course course = jdbcTemplate.queryForObject(
        GET_COURSE,
        new CourseMapper(),
        courseCode
      );
      return course;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public String paymentInit(User user, String courseCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String PAYMENT_INIT =
      "INSERT INTO hc_transactions (t_username,course_code, order_id) VALUES (?,?,?)";
    try {
      String orderId = "ORDER_" + randomAlphaNumeric(10);
      jdbcTemplate.update(
        PAYMENT_INIT,
        user.getUsername(),
        courseCode,
        orderId
      );
      return orderId;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean completePayment(TreeMap<String, String> param) {
    jdbcTemplate.setDataSource(getDataSource());
    String orderId = param.get("ORDERID");
    String COMPLETE_PAYMENT =
      "UPDATE hc_transactions SET bank_name = ?, " +
      "bank_txn_id =?, currency = ?, gateway_name = ?, mid = ?, payment_mode = ?, " +
      "resp_code = ?, resp_msg = ?, status = ?, txn_amount = ?, txn_date = ?, " +
      "txn_id = ? WHERE order_id = ?";

    if (param.get("STATUS").equals("TXN_SUCCESS")) {
      try {
        jdbcTemplate.update(
          COMPLETE_PAYMENT,
          param.get("BANKNAME"),
          param.get("BANKTXNID"),
          param.get("CURRENCY"),
          param.get("GATEWAYNAME"),
          param.get("MID"),
          param.get("PAYMENTMODE"),
          param.get("RESPCODE"),
          param.get("RESPMSG"),
          param.get("STATUS"),
          param.get("TXNAMOUNT"),
          param.get("TXNDATE"),
          param.get("TXNID"),
          orderId
        );
        System.out.println(orderId);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public boolean addCourseToUser(String username, String courseCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String ADD_COURSE_TO_USER =
      "INSERT INTO hc_user_program (up_username, up_code, is_paid) VALUES (?,?,?)";

    try {
      jdbcTemplate.update(ADD_COURSE_TO_USER, username, courseCode, 1);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Course> getUserCourses(String username) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USER_COURSES =
      "SELECT * FROM hc_user_program pg JOIN hc_courses c WHERE pg.up_code = c.c_code AND pg.up_username = ? AND c.c_is_active = 1";
    List<Course> courses = null;
    try {
      courses = jdbcTemplate.query(
        GET_USER_COURSES,
        new Object[] { username },
        new ResultSetExtractor<List<Course>>() {
          public List<Course> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<Course> list = new ArrayList<Course>();
            while (rs.next()) {
              Course ec = new Course();
              ec.setCode(rs.getString("c_code"));
              ec.setName(rs.getString("c_name"));
              ec.setDesc(rs.getString("c_desc"));
              ec.setPrice(rs.getInt("c_price"));
              ec.setTotalDays(rs.getInt("c_total_days"));
              ec.setTotalLessons(getlLessonCount(rs.getString("c_code")));
              ec.setCompletedLessons(
                getCompletedLessonCount(rs.getString("c_code"))
              );
              ec.setCover(rs.getString("c_cover"));

              list.add(ec);
            }
            return list;
          }
        }
      );
    } catch (Exception e) {
      e.printStackTrace();
    }

    return courses;
  }

  public List<EntityCourse> getCoursesByTags(String tags) {
    jdbcTemplate.setDataSource(getDataSource());
    String SEARCH_COURSE_BY_TAG =
      "SELECT * FROM hc_courses WHERE c_tags REGEXP ?";

    tags = tags.replace(" ", "|");
    System.out.println(tags);

    try {
      List<EntityCourse> courses = jdbcTemplate.query(
        SEARCH_COURSE_BY_TAG,
        new Object[] { tags },
        new ResultSetExtractor<List<EntityCourse>>() {
          public List<EntityCourse> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<EntityCourse> list = new ArrayList<EntityCourse>();
            while (rs.next()) {
              EntityCourse ec = new EntityCourse();
              ec.setName(rs.getString("c_name"));
              ec.setCode(rs.getString("c_code"));
              ec.setTags(rs.getString("c_tags"));
              ec.setMrp(rs.getInt("c_mrp"));
              ec.setSubDesc(rs.getString("c_sub_desc"));

              ec.setPrice(rs.getInt("c_price"));
              ec.setDesc(rs.getString("c_desc"));
              ec.setDays(rs.getInt("c_total_days"));
              ec.setIntro(rs.getString("c_intro"));
              ec.setCover(rs.getString("c_cover"));
              ec.setLessonCount(getlLessonCount(rs.getString("c_code")));

              if (utils.isTestSeries(rs.getString("c_name"))) {
                ec.setTestSeries(true);
              } else {
                ec.setTestSeries(false);
              }

              list.add(ec);
            }
            return list;
          }
        }
      );

      return courses;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public String getCourses() {
    List<EntityCourse> courses = getEntityCourses(null);
    JsonArray root = new JsonArray();

    for (int i = 0; i < courses.size(); i++) {
      JsonObject coursesJ = new JsonObject();
      JsonArray jc = new JsonArray(); // chapter arrayo

      EntityCourse ecourse = courses.get(i);
      List<EntityChapter> chapters = getEntityChapters(ecourse.getCode());

      for (int j = 0; j < chapters.size(); j++) {
        EntityChapter echapter = chapters.get(j);
        List<EntityLesson> lessons = getEntityLesson(echapter.getCode());
        JsonArray jLessons = new JsonArray();

        for (int k = 0; k < lessons.size(); k++) {
          JsonObject jj = new JsonObject();
          jj.addProperty("name", lessons.get(k).getName());
          jj.addProperty("code", lessons.get(k).getCode());
          jj.addProperty("resourse", lessons.get(k).getResourse());
          jLessons.add(jj);
        }
        JsonObject jcc = new JsonObject();

        jcc.addProperty("name", echapter.getName());
        jcc.addProperty("code", echapter.getCode());
        jcc.add("lessons", jLessons);
        jc.add(jcc);
      }
      coursesJ.addProperty("name", ecourse.getName());
      coursesJ.addProperty("code", ecourse.getCode());
      coursesJ.addProperty("creator", ecourse.getCreator());
      coursesJ.addProperty("total_days", ecourse.getDays());
      coursesJ.addProperty("price", ecourse.getPrice());
      coursesJ.addProperty("desc", ecourse.getDesc());
      coursesJ.add("chapters", jc);
      root.add(coursesJ);
    }

    return root.toString();
  }

  public boolean addResource(String name, String source) {
    jdbcTemplate.setDataSource(getDataSource());
    String ADD_COURSE =
      "INSERT INTO hc_resources (r_name, r_source) VALUES (?,?)";
    try {
      jdbcTemplate.update(ADD_COURSE, name, source);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public List<Resource> getResources() {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_RESOURCE = "SELECT * from hc_resources";
    List<Resource> resources = jdbcTemplate.query(
      GET_RESOURCE,
      new ResultSetExtractor<List<Resource>>() {
        public List<Resource> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<Resource> list = new ArrayList<Resource>();
          while (rs.next()) {
            Resource ec = new Resource();
            ec.setName(rs.getString("r_name"));
            ec.setSource(rs.getString("r_source"));
            list.add(ec);
          }
          return list;
        }
      }
    );

    return resources;
  }

  public boolean markLessonComplete(
    String username,
    String chapterCode,
    String lessonCode
  ) {
    jdbcTemplate.setDataSource(getDataSource());
    String IS_COMPLETE_LESSON =
      "SELECT COUNT(*) FROM hc_lesson_track WHERE lt_username = ? AND lt_ch_code = ? AND lt_ln_code = ?";
    String COMPLETE_LESSON =
      "INSERT INTO hc_lesson_track (lt_username, lt_ch_code, lt_ln_code) VALUES (?,?,?)";
    try {
      int count = jdbcTemplate.queryForObject(
        IS_COMPLETE_LESSON,
        new Object[] { username, chapterCode, lessonCode },
        Integer.class
      );

      if (count == 0) {
        jdbcTemplate.update(COMPLETE_LESSON, username, chapterCode, lessonCode);
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public int getlLessonCount(String courceCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_LESSON_COUNT =
      "SELECT COUNT(*) FROM hc_lessons WHERE l_c_code = ? AND l_is_active = 1";
    try {
      int count = jdbcTemplate.queryForObject(
        GET_LESSON_COUNT,
        new Object[] { courceCode },
        Integer.class
      );
      return count;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public int getCompletedLessonCount(String courceCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_LESSON_COUNT =
      "SELECT COUNT(*) FROM hc_lesson_track WHERE lt_ch_code = ?";
    try {
      int count = jdbcTemplate.queryForObject(
        GET_LESSON_COUNT,
        new Object[] { courceCode },
        Integer.class
      );
      return count;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public boolean updateCourse(EditCourse course) {
    jdbcTemplate.setDataSource(getDataSource());
    String UPDATE_COURSE =
      "UPDATE hc_courses SET c_name = ?, c_desc = ?, c_sub_desc = ?, c_mrp = ?, c_price = ?, " +
      " c_total_days = ?, c_tags = ? WHERE c_code = ?";
    System.out.println(course.toString());

    try {
      jdbcTemplate.update(
        UPDATE_COURSE,
        new Object[] {
          course.getName(),
          course.getDesc(),
          course.getQuickDesc(),
          course.getMrp(),
          course.getPrice(),
          course.getDays(),
          course.getTags(),
          course.getCode(),
        }
      );
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean updateChapter(EditChapter chapter) {
    jdbcTemplate.setDataSource(getDataSource());
    String UPDATE_CHAPTER =
      "UPDATE hc_chapters SET ch_name = ? WHERE ch_c_code = ? AND ch_code = ?";

    try {
      jdbcTemplate.update(
        UPDATE_CHAPTER,
        new Object[] {
          chapter.getName(),
          chapter.getCourseCode(),
          chapter.getCode(),
        }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean makeChapter(EditChapter chapter) {
    jdbcTemplate.setDataSource(getDataSource());
    String MAKE_CHAPTER =
      "INSERT INTO hc_chapters (ch_c_code, ch_code, ch_name) VALUE (?,?,?)";

    try {
      jdbcTemplate.update(
        MAKE_CHAPTER,
        new Object[] {
          chapter.getCourseCode(),
          chapter.getCode(),
          chapter.getName(),
        }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean updateLesson(EditLesson lesson) {
    jdbcTemplate.setDataSource(getDataSource());
    String UPDATE_CHAPTER =
      "UPDATE hc_lessons SET l_name = ?, l_resource = ? WHERE l_c_code = ? AND l_ch_code = ? AND  l_code = ?";

    try {
      jdbcTemplate.update(
        UPDATE_CHAPTER,
        new Object[] {
          lesson.getName(),
          lesson.getFileUrl(),
          lesson.getCourseCode(),
          lesson.getChapterCode(),
          lesson.getCode(),
        }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean makeLesson(EditLesson lesson) {
    jdbcTemplate.setDataSource(getDataSource());
    String MAKE_LESSON =
      "INSERT INTO hc_lessons (l_c_code, l_ch_code, l_code, l_name, l_resource, l_duration) VALUES (?,?,?,?,?,?)";

    try {
      jdbcTemplate.update(
        MAKE_LESSON,
        new Object[] {
          lesson.getCourseCode(),
          lesson.getChapterCode(),
          lesson.getCode(),
          lesson.getName(),
          lesson.getFileUrl(),
          lesson.getDuration(),
        }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean deleteEntity(
    String code,
    String tableName,
    String columnName,
    String codeCol
  ) {
    jdbcTemplate.setDataSource(getDataSource());

    String DELETE_ENTITY =
      "UPDATE " +
      tableName +
      " SET " +
      columnName +
      "= 0 WHERE " +
      codeCol +
      " = ?";
    System.out.println(DELETE_ENTITY);

    try {
      jdbcTemplate.update(DELETE_ENTITY, new Object[] { code });
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<EntityCourse> getSimilarCourse(String tags) {
    jdbcTemplate.setDataSource(getDataSource());

    String GET_SIMILAR_COURSES =
      "SELECT * FROM hc_courses WHERE c_tags REGEXP ? LIMIT 2";
    try {
      List<EntityCourse> courses = jdbcTemplate.query(
        GET_SIMILAR_COURSES,
        new Object[] { tags },
        new ResultSetExtractor<List<EntityCourse>>() {
          public List<EntityCourse> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
            List<EntityCourse> list = new ArrayList<EntityCourse>();
            while (rs.next()) {
              EntityCourse ec = new EntityCourse();
              ec.setName(rs.getString("c_name"));
              ec.setCode(rs.getString("c_code"));
              ec.setTags(rs.getString("c_tags"));
              ec.setMrp(rs.getInt("c_mrp"));
              ec.setSubDesc(rs.getString("c_sub_desc"));

              ec.setPrice(rs.getInt("c_price"));
              ec.setDesc(rs.getString("c_desc"));
              ec.setDays(rs.getInt("c_total_days"));
              ec.setIntro(rs.getString("c_intro"));
              ec.setCover(rs.getString("c_cover"));
              ec.setLessonCount(getlLessonCount(rs.getString("c_code")));

              list.add(ec);
            }
            return list;
          }
        }
      );

      return courses;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<EntityLesson> getRelatedVideos(String tags) {
    jdbcTemplate.setDataSource(getDataSource());
    tags = tags.replace(" ", "|");

    String GET_RELATED_VIDEOS =
      "SELECT * FROM hc_lessons WHERE l_tags REGEXP ? AND l_is_free = 1 LIMIT 1";

    List<EntityLesson> lessons = jdbcTemplate.query(
      GET_RELATED_VIDEOS,
      new Object[] { tags },
      new ResultSetExtractor<List<EntityLesson>>() {
        public List<EntityLesson> extractData(ResultSet rs)
          throws SQLException, DataAccessException {
          List<EntityLesson> list = new ArrayList<EntityLesson>();
          while (rs.next()) {
            EntityLesson el = new EntityLesson();
            el.setName(rs.getString("l_name"));
            el.setCode(rs.getString("l_code"));
            el.setResourse(rs.getString("l_resource"));
            el.setDuration(rs.getFloat("l_duration"));

            if (rs.getString("l_is_free").equalsIgnoreCase("1")) el.setFree(
              true
            );
            else el.setFree(false);

            list.add(el);
          }
          return list;
        }
      }
    );

    return lessons;
  }

  @Override
  public boolean isCourseSubscribedByUser(String username, String courseCode) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USER =
      "SELECT COUNT(*) FROM hc_user_program WHERE BINARY up_username = ? AND up_code = ?";
    Number count = 0;
    try {
      count = jdbcTemplate.queryForObject(
        GET_USER,
        Integer.class,
        username,
        courseCode
      );

      if (count.intValue() == 0) return false;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return true;
  }
}
