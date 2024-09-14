package com.web.hackercode.dao;

import com.web.hackercode.structures.InternshipForm;
import com.web.hackercode.structures.Register;
import com.web.hackercode.structures.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserDAO {
  public User getUser(String username, HttpServletRequest request);

  public int getUserCountWithEmail(String email);

  public User getUser(String username);

  public boolean saveUser(Register user);

  public void updateUserInfo(User u, User currentUser);

  public String returnImagePath(MultipartFile file) throws IOException;

  public String uploadFile(
    HttpServletRequest req,
    MultipartFile file,
    String relPath
  );

  public boolean isUser(
    String username,
    String password,
    HttpServletRequest request
  );

  public boolean savePicture(User user, String filePath);

  public boolean saveResetLink(String username, String resetLink);

  public boolean isGoodKey(String resetLink);

  public boolean dropKey(String resetLink);

  public boolean updatePassword(String password, String resetKey);

  public User saveUserViaRequest(HttpServletRequest req, Register user);
}
