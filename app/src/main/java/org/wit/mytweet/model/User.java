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
  public String firstName;
  public String lastName;
  public String email;
  public String password;

  public User() {
  }
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

  public UUID getId() { return id; }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }

  public void setPassword( String password) { this.password = password; }

}
