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

  /**
   * Getter for user's first name
   *
   * @return User's first name
   */
  public String getFirstName() { return firstName; }

  /**
   * Setter for user's first name
   *
   * @param firstName user's first name
   */
  public void setFirstName(String firstName) { this.firstName = firstName; }

  /**
   * Getter for user's last name
   *
   * @return User's last name
   */
  public String getLastName() { return lastName; }

  /**
   * Setter for user's last name
   *
   * @param lastName user's last name
   */
  public void setLastName(String lastName) { this.lastName = lastName; }

  /**
   * Getter for user's email
   *
   * @return User's email
   */
  public String getEmail() { return email; }

  /**
   * Setter for user's email
   *
   * @param email User's email
   */
  public void setEmail(String email) { this.email = email; }

  /**
   * Getter for user's password
   *
   * @return User's password
   */
  public String getPassword() { return password; }

  /**
   * Setter for user's password
   *
   * @param password User's password
   */
  public void setPassword( String password) { this.password = password; }

}
