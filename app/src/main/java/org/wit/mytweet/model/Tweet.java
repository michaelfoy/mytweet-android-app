package org.wit.mytweet.model;

import android.support.annotation.NonNull;

import org.wit.mytweet.main.MyTweetApp;
import java.util.Random;
import java.util.UUID;

/**
 * @file Tweet.java
 * @brief A class to describe a tweet object and relevant methods
 * @author michaelfoy
 * @version 2016.10.03
 **/

public class Tweet {

  private String content;
  private String date;
  private User tweeter;
  public UUID id;

  /**
   * Constructor of empty Tweet. Used to retrieve data from db
   */
  public Tweet() {

  }

  /**
   * Constructor for a new tweet
   *
   * @param content Content text of the tweet
   * @param date Date the tweet was posted
   */
  public Tweet(String content, String date) {
    this.content = content;
    this.date = date;
    this.tweeter = MyTweetApp.getCurrentUser();
    this.id = UUID.randomUUID();
  }

  /**
   * Getter for the tweet's content text
   *
   * @return Content of the tweet
   */
  public String getContent() {
    return content;
  }

  public UUID getId() { return this.id; }
  /**
   * Setter for the tweet's content text
   *
   * @param content Content of the tweet
   */
  public void setContent(String content) {
    this.content =  content;
  }

  /**
   * Getter for the tweet's date
   * @return Date of the tweet
   */
  public String getDate() {
    return date;
  }

  /**
   * Setter for the tweet's date
   *
   * @param date Date of the tweet
   */
  public void setDate(String date) {
    this.date =  date;
  }

  /**
   * Getter for the tweet's sender
   * @return Id of sender of the tweet
   */
  public UUID getTweeter() {
    return tweeter.id;
  }

  /**
   * Getter for the name of the tweet's sender
   * @return Id of sender of the tweet
   */
  public String getTweeterName() {

    User user = MyTweetApp.getUserById(tweeter.id);
    return user.getFirstName() + " " + user.getLastName();
  }

  /**
   * Sets User tweeter for the Tweet
   * @param tweeter The specified tweeter
   */
  public void setTweeter(String tweeter) {
    UUID TweeterId;
    TweeterId = UUID.fromString(tweeter);
    this.tweeter =  MyTweetApp.getUserById(TweeterId);
  }
}