package org.wit.mytweet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import org.wit.mytweet.R;

/**
 * @file UserNewTweet.java
 * @brief UserNewTweet activity class, enables UserNewTweet fragment
 * @version 2016.11.01
 * @author michaelfoy
 */
public class UserNewTweet extends AppCompatActivity
{
  static ActionBar actionBar;

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_container);

    actionBar = getSupportActionBar();

    FragmentManager manager = getSupportFragmentManager();
    Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
    if (fragment == null)
    {
      fragment = new UserNewTweetFragment();
      manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
    }
  }
}
