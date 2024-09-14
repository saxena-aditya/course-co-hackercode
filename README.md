# HackerCode docs

### Moving the application to the server

The application presently runs on apache-tomcat hosted on a GCE Compute Engine. A Jenkins installation has been already set up for CI/CD purposes.

Jenkins (http://34.67.112.194/jenkins-install) is set up to automatically fetch and build all the code and deploy it on tomcat. Two separate freestyle jobs have been configured to fetch and build the code.

##### To setup your own Jenkins instance

1. Use Git plugin of Jenkins and give the repo URL and branch name.
2. Go to repo settings and generate a webhook URL.
3. Use this URL to use in the Jenkins plugin.
4. From the repo settings generate a key pair.
5. Use this key pair in Jenkins to authenticate with the Git.

Additional improvements may include adding a new piece to the pipeline for restarting the server whenever a build is successful.

The setup at present listens to each commit and gets initiated automatically. Whenever you commit your code to the `dev` branch of the repo, your code will be built and deployed hence the CD.

### The Code

#### (\*)Controllers.java

The application is based on an MVC architecture so it has a lot of controller files. One controller for one feature.

`AuthController.java`

Controls login and logout features.

`HomeController.java`

Controls basic homepage links like - opening resources page and apply-for-internship API for saving internship letter.

`InternshipController.java`

Controls Internship activities like - showing internship letters in the admin dashboard, marking internship letters (to determine if the letter is attended or not)

`UserController.java`

Controls user features like saving profile user photo and recovering password.

`ArticleController.java`

All the CRUD operations relating to articles are handled in this file.
Admin side features like approving, disapproving and viewing articles before approving are also served by this file.

`CourseController.java`

All the operations regarding courses are handled here. Note that some routes start with '/api'. This denotes that this function/method is used to just return/save data from/to DB and no views are being rendered.
The admin side functionality like adding courses, deleting courses, modifying courses are also present in this file.

`MailController.java`

This class is used for sending various mails, right now password-reset and article-process mails are being handled here. Schedule this class for auto mailers.

`ReportControllers.java`

All the functionality regarding test reporting is coded here. This class requires a Users object to perform its actions. Extend this class if you need to implement any new reporting features.

`TestController.java`

This class is for all the Test/Exam operations. Be it a user side functionality or an admin feature you'll find all the Test/Exam CRUD operations here.
Example operations include - Creating a Test, Deleting a Test, Modifying a Test, Adding new Questions to a pre-existing test, Deleting Questions from a pre-existing test etc.

#### The DAO and DAOImpl

All the controller classes are supported by DAO classes. For example, for ArticleController.java there is ArticleDAO.java, for TestController.java there is TestDAO.java.
DAO class files have all the prototyping for the methods that are used to perform various operations.
DAO classes _only_ hold the prototypes for the methods whereas DAOImpl classes contain their definitions. So if you need to change anything, chances are you'll have to look for a DAOImpl class file. Lookup is fairly logical tho, like DAO class files, DAOImpl class files also have names according to there Controller counterparts eg. ArticleController.java will have ArticleDAOImpl.java and so on

#### CRUD operations

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

###### Getting a user from database:

```java
public User getUser(String username) {
  jdbcTemplate.setDataSource(getDataSource());
  String COUNT_USER =
    "SELECT COUNT(*) FROM hc_user_details WHERE ud_username = ?";
  String GET_USER = "SELECT * FROM hc_user_details WHERE ud_username = ?";

  int count = jdbcTemplate.queryForObject(
    COUNT_USER,
    new Object[] { username },
    Integer.class
  );

  if (count != 0) {
    User user = (User) jdbcTemplate.queryForObject(
      GET_USER,
      new Object[] { username },
      new UserMapper()
    );

    if (user != null) return user;
  }

  return null;
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

  return null;
}

```

###### Approving and Disapproving any article:

```java
public boolean approveArticle(String id) {
  jdbcTemplate.setDataSource(getDataSource());

  String APPROVE_ARTICLE =
    "UPDATE hc_articles set a_is_approved = 1 WHERE id = ? AND a_is_active = 1";
  try {
    jdbcTemplate.update(APPROVE_ARTICLE, id);
    return true;
  } catch (Exception e) {
    e.printStackTrace();
  }
  return false;
}

public boolean disapproveArticle(String id) {
  jdbcTemplate.setDataSource(getDataSource());

  String DISAPPROVE_ARTICLE =
    "UPDATE hc_articles set a_is_approved = 0 WHERE id = ? AND a_is_active = 1";
  try {
    jdbcTemplate.update(DISAPPROVE_ARTICLE, id);
    return true;
  } catch (Exception e) {
    e.printStackTrace();
  }
  return false;
}

```

#### AWS Code Example to save data:

```javascript
const albumBucketName = "<bucket_name>";
const bucketRegion = "<region>";
const IdentityPoolId = "<poll_id>";

AWS.config.update({
    credentials: new AWS.CognitoIdentityCredentials({
      IdentityPoolId: IdentityPoolId
   })
});

AWS.config.region = bucketRegion;

const s3 = new AWS.S3({
   apiVersion: "<api_version>",
   params: {
     Bucket: albumBucketName
   }
});
s3.upload({
        Key: rs.key,
        Body: rs.file,
        ContentType: rs.file.type,
        ACL: "<access_level>"
      }, options)
      .on("httpUploadProgress", function(evt) {
       // show the loading bar here.
      )}
```
