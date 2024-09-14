package com.web.hackercode.dao;

import com.web.hackercode.structures.ProgramSpecificTests;
import com.web.hackercode.structures.TestUser;
import com.web.hackercode.structures.User;
import java.util.List;

public interface TestUtilitiesDAO {
  public List<TestUser> getAllFinishedTest(User u);

  public List<ProgramSpecificTests> getAllTest(User u);

  public List<ProgramSpecificTests> getResumableTests(User user);
}
