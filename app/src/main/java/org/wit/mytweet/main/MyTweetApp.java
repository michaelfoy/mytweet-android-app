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
}