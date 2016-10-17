package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;

import org.wit.android.helpers.DbHelper;
import org.wit.mytweet.model.Tweet;
import org.wit.mytweet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @file MyTweetApp.java
 * @brief Class to provide back-end application functionality
 * @version 2016.10.03
 * @author michaelfoy
 */
public class MyTweetApp extends Application {

  private static List<User> users = new ArrayList<User>();
  private static List<Tweet> tweets = new ArrayList<Tweet>();
  private static User currentUser;
  public static DbHelper dbHelper = null;

  /**
   * Activates the layout and instantiates it's widgets
   */
  @Override
  public void onCreate() {
      super.onCreate();
      dbHelper = new DbHelper(getApplicationContext());
      Log.v("MyTweet", "MyTweet App Started");
  }

  /**
   * Saves a new user
   * @param user The new user
   */
  public void newUser(User user) {
      dbHelper.addUser(user);
  }

  /**
   * Checks login data against registered users
   * @param email Input email
   * @param password Input password
   * @return True if the data matches a registered user
   */
  public boolean registeredUser(String email, String password)
  {
    users = dbHelper.selectAllUsers();
    for (User user : users) {
      if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
        Log.v("MyTweet", "Logging in as: " + user.getFirstName() + " " + user.getLastName());
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

  /**
   * If found, returns a User with the corresponding UUID
   *
   * @param id UUID to be searched in db
   * @return The corresponding User object
   */
  public static User getUserById(UUID id) {
    users = dbHelper.selectAllUsers();
    for (User user : users) {
      if(user.id.equals(id)) {
        Log.v("MyTweet", "Retrieving: " + user.getFirstName() + " " + user.getLastName());
        return user;
      }
    }
    Log.v("MyTweet", "No user found for id: " + id);
    return null;
  }

  /**
   * If found, returns a User with the corresponding UUID
   *
   * @param id UUID to be searched in db
   * @return The corresponding User object
   */
  public static Tweet getTweetById(UUID id) {
    tweets = dbHelper.selectAllTweets();
    for (Tweet tweet : tweets) {
      if(tweet.id.equals(id)) {
        Log.v("MyTweet", "Retrieving tweet: " + tweet.getContent());
        return tweet;
      }
    }
    Log.v("MyTweet", "No tweet found for id: " + id);
    return null;
  }


  /**
   * Returns a list of all Tweets in the db
   *
   * @return List of all persisted tweet
   */
  public static List<Tweet> getTweets() {
    return dbHelper.selectAllTweets();
  }
}