package com.web.hackercode.structures;

public class Admin {

  private int u_id;
  private String username;
  private String password;
  private String adminType;

  /**
   * @return the u_id
   */
  public int getU_id() {
    return u_id;
  }

  /**
   * @param u_id the u_id to set
   */
  public void setU_id(int u_id) {
    this.u_id = u_id;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the adminType
   */
  public String getAdminType() {
    return adminType;
  }

  /**
   * @param adminType the adminType to set
   */
  public void setAdminType(String adminType) {
    this.adminType = adminType;
  }
}
