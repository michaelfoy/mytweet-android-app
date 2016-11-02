package org.wit.mytweet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.wit.mytweet.R;

/**
 * Created by Workstation on 01/11/2016.
 */
public class UserTweetList extends AppCompatActivity
{
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_container);

    FragmentManager manager = getSupportFragmentManager();
    Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
    if (fragment == null)
    {
      fragment = new UserTweetListFragment();
      manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
    }
  }
}
