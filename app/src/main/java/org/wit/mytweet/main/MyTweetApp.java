package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;

import org.wit.mytweet.model.Tweet;
import org.wit.mytweet.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @file MyTweetApp.java
 * @brief Class to provide back-end application functionality
 * @version 2016.10.03
 * @author michaelfoy
 */
public class MyTweetApp extends Application {

  private List<User> users = new ArrayList<User>();
  private List<Tweet> tweets = new ArrayList<Tweet>();
  private static User currentUser;

  /**
   * Activates the layout and instantiates it's widgets
   */
  @Override
  public void onCreate() {
      super.onCreate();
      Log.v("MyTweet", "MyTweet App Started");
  }

  /**
   * Saves a new user
   * @param user The new user
   */
  public void newUser(User user) {
      users.add(user);
  }

  /**
   * Saves a new Tweet
   * @param tweet The new tweet
   */
  public void newTweet(Tweet tweet) { tweets.add(tweet); Log.v("MyTweet", "" + tweet.content ); }

  /**
   * Checks login data against registered users
   * @param email Input email
   * @param password Input password
   * @return True if the data matches a registered user
   */
  public boolean registeredUser(String email, String password)
  {
    for (User user : users) {
      if(user.email.equals(email) && user.password.equals(password)) {
        Log.v("DonationApp", "Logging in as: " + user.firstName + " " + user.lastName);
        currentUser = user;
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the currently logged in user
   * @return The currently logged in user
   */
  public static User getCurrentUser() {
    return currentUser;
  }

  public String getTweet() {

    int counter = -1;
    for (int i = 0; i <= tweets.size(); i++) {
      counter++;
    }
    String str =  tweets.get(counter).getContent();
    return str;
  }
}