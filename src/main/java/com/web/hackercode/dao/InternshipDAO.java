package com.web.hackercode.dao;

import com.web.hackercode.structures.InternshipForm;
import java.util.List;

public interface InternshipDAO {
  public boolean applyForInternship(InternshipForm itn);

  public List<InternshipForm> getInternshipApplications();

  public InternshipForm getInternshipApplication(String id);

  public boolean markInternshipLead(String id);

  public void addDrafterAccount(InternshipForm iform, String internUsername);
}
