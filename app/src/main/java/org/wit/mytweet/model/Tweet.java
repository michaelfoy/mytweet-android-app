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
   * Constructor for a new tweet
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
   * @return Content of the tweet
   */
  public String getContent() {
    return content;
  }

  /**
   * Getter for the tweet's date
   * @return Date of the tweet
   */
  public String getDate() {
    return date;
  }

  /**
   * Getter for the tweet's sender
   * @return Id of sender of the tweet
   */
  /*public long getTweeter() {
    return tweeter.getId();
  }*/
}