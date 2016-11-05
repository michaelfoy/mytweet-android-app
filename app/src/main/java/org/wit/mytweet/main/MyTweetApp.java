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
  protected static MyTweetApp app;

  // Temporary tweet object, used when creating a new tweet
  private static Tweet tempTweet;

  /**
   * Activates the layout and instantiates it's widgets
   */
  @Override
  public void onCreate() {
    app = this;
    super.onCreate();
    dbHelper = new DbHelper(getApplicationContext());
    Log.v("MyTweet", "MyTweet App Started");
  }

  public static MyTweetApp getApp(){
    return app;
  }

  /**
   * Saves a new user if their email does not exist in db
   *
   * @param user The new user
   * @return True if user email not already persisted
   */
  public boolean newUser(User user) {

    users = dbHelper.selectAllUsers();
    String checkEmail = user.getEmail();
    for (int i = 0; i < users.size(); i++){
      String regEmail = users.get(i).getEmail();
      if (checkEmail.equals(regEmail)) {
        return false;
      }
    }
    dbHelper.addUser(user);
    users = null;
    return true;
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
    users = null;
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
   * Logs out the current user
   */
  public static void logout() {
    currentUser = null;
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
    users = null;
    return null;
  }

  /**
   * If found, returns a Tweet with the corresponding UUID
   *
   * @param id UUID to be searched in db
   * @return The corresponding Tweet object
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
    tweets = null;
    return null;
  }

  /**
   * Return a list of all tweets composed by a specific user
   * @param id Id of specified user
   * @return List of user's tweets
   */
  public List<Tweet> getAllTweetsForUser(String id) {
    return dbHelper.getAllTweetsForUser(id);
  }

  /**
   * Returns a list of all Tweets in the db
   *
   * @return List of all persisted tweet
   */
  public static List<Tweet> getTweets() {
    return dbHelper.selectAllTweets();
  }

  /**
   * Deletes a tweet from the db
   *
   * @param id Id of the tweet to be deleted
   */
  public void deleteTweet(String id) { dbHelper.deleteTweet(id); }

  /**
   * Sets the temporary tweet object before commiting to db
   */
  public static void setTempTweet(Tweet tweet) {
    tempTweet = tweet;
  }

  /**
   * Gets the temporary tweet object before commiting to db
   */
  public static Tweet getTempTweet() {
    return tempTweet;
  }

  /**
   * Deletes the temporary tweet object
   */
  public static void deleteTempTweet() { tempTweet = null; }
}