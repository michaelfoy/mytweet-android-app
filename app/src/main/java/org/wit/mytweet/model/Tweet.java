package org.wit.mytweet.model;

import org.wit.mytweet.main.MyTweetApp;

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

  /**
   * Constructor for a new tweet
   * @param content Content text of the tweet
   * @param date Date the tweet was posted
   */
  public Tweet(String content, String date) {
    this.content = content;
    this.date = date;
    this.tweeter = MyTweetApp.getCurrentUser();
  }

  /**
   * Getter for the tweet's content text
   * @return Content of the tweet
   */
  public String getContent() {
    return content;
  }

}