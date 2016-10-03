package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;

import org.wit.mytweet.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @file MyTweetApp.java
 * @brief Class to provide back-end application functionality
 * @version 2016.10.03
 * @author michaelfoy
 */
public class MyTweetApp extends Application
{
    public List<User> users = new ArrayList<User>();

    /**
     * Activates the layout and instantiates it's widgets
     */
    @Override
    public void onCreate()
    {
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
        return true;
      }
    }
    return false;
  }
}