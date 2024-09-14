# Course Go HackerCode

CourseCo is an online education platform designed to provide students and professionals with easy access to a variety of courses. The platform allows users to browse, enroll, and track their progress through different educational programs. Whether it's learning a new skill or advancing in your career, CourseCo offers a seamless, user-friendly experience that caters to learners of all backgrounds and levels.

### Moving the application to the server

The application presently runs on apache-tomcat hosted on a GCE Compute Engine. A Jenkins installation has been already set up for CI/CD purposes.

##### To setup your own Jenkins instance

1. Use Git plugin of Jenkins and give the repo URL and branch name.
2. Go to repo settings and generate a webhook URL.
3. Use this URL to use in the Jenkins plugin.
4. From the repo settings generate a key pair.
5. Use this key pair in Jenkins to authenticate with the Git.

Additional improvements may include adding a new piece to the pipeline for restarting the server whenever a build is successful.

The setup at present listens to each commit and gets initiated automatically. Whenever you commit your code to the `dev` branch of the repo, your code will be built and deployed hence the CD.

### About the code

#### Controllers

The application is based on an MVC architecture so it has a lot of controller files. One controller for one feature.

#### DAO and DAOImpl

All the controller classes are supported by DAO classes. For example, for ArticleController.java there is ArticleDAO.java, for TestController.java there is TestDAO.java. DAO class files have all the prototyping for the methods that are used to perform various operations.
DAO classes _only_ hold the prototypes for the methods whereas DAOImpl classes contain their definitions. So if you need to change anything, chances are you'll have to look for a DAOImpl class file. Lookup is fairly logical tho, like DAO class files, DAOImpl class files also have names according to there Controller counterparts eg. ArticleController.java will have ArticleDAOImpl.java and so on

###### Saving a user:

```java
  public boolean saveUser(Register ruser) {
    jdbcTemplate.setDataSource(getDataSource());
    ruser.setPassword(utils.getMd5(ruser.getPassword()));
    String SAVE_USER =
      "INSERT INTO hc_user_details (ud_username, ud_firstname, ud_lastname, ud_email, ud_role, ud_phone, ud_institute) VALUES (?,?,?,?,0,?,?)";
    String SAVE_USER_LOGIN_CREDENTIALS =
      "INSERT INTO hc_user (u_username, u_password) VALUES (?,?)";
    // String ADD_USER_WITH_PROGRAM = "INSERT INTO hc_user_program (up_username, up_code) VALUES (?,?)";
    try {
      jdbcTemplate.update(
        SAVE_USER,
        new Object[] {
          ruser.getEmail(),
          ruser.getfName(),
          ruser.getlName(),
          ruser.getEmail(),
          ruser.getPhone(),
          ruser.getInstitute(),
        }
      );

      jdbcTemplate.update(
        SAVE_USER_LOGIN_CREDENTIALS,
        new Object[] { ruser.getEmail(), ruser.getPassword() }
      );

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

```

###### Check if a User is already registered:

```java
  public int getUserCountWithEmail(String email) {
    jdbcTemplate.setDataSource(getDataSource());
    String GET_USER =
      "SELECT COUNT(*) FROM hc_user_details WHERE BINARY ud_email = ?";
    Number count = 0;
    try {
      count = jdbcTemplate.queryForObject(GET_USER, Integer.class, email);
      return new Integer(count.intValue());
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

```

###### Saving a course

```java
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
```