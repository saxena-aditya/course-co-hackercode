package com.web.hackercode.structures;

import java.util.Date;
import java.util.List;

public class StatBox {

  int id;
  int u_id;
  Date last_modified;
  int attribute_id;
  int totalRegistredStudents;

  public int getTotalRegistredStudents() {
    return totalRegistredStudents;
  }

  public void setTotalRegistredStudents(int totalRegistredStudents) {
    this.totalRegistredStudents = totalRegistredStudents;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getU_id() {
    return u_id;
  }

  public void setU_id(int u_id) {
    this.u_id = u_id;
  }

  public Date getLast_modified() {
    return last_modified;
  }

  public void setLast_modified(Date last_modified) {
    this.last_modified = last_modified;
  }

  public int getAttribute_id() {
    return attribute_id;
  }

  public void setAttribute_id(int attribute_id) {
    this.attribute_id = attribute_id;
  }

  public int countReports(List<StatBox> statBox) {
    return 1;
  }
}
