package org.wit.mytweet.model;

import android.support.annotation.NonNull;

import java.util.Random;
import java.util.UUID;

/**
 * @author michaelfoy
 * @version 2016.10.03
 * @file User.java
 * @brief A class to describe a user object and relevant methods
 **/
public class User {

  public UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  /**
   * Creates a new User
   *
   * @param firstName User's first name
   * @param lastName  User's last name
   * @param email     User's email address
   * @param password  User's password
   */
  public User(String firstName, String lastName, String email, String password) {
    this.id = UUID.randomUUID();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public String getName() {
    return firstName + " " + lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
