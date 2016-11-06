package org.wit.mytweet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import static org.wit.android.helpers.IntentHelper.navigateUp;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;

/**
 * @file SettingsFragment.java
 * @brief Provides view and methods for Settings adjustment
 * @author michaelfoy
 * @version 2016.11.06
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  private SharedPreferences prefs;
  private MyTweetApp app;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    app = MyTweetApp.getApp();
    addPreferencesFromResource(R.xml.settings);
  }

  @Override
  public void onStart()
  {
    super.onStart();
    prefs = PreferenceManager
        .getDefaultSharedPreferences(getActivity());
    prefs.registerOnSharedPreferenceChangeListener(this);
  }

  /**
   * Directs app to action when menu item selected
   *
   * @param item The selected item
   * @return True if action successful
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
        navigateUp(getActivity());
        return true;

      case R.id.action_mytweets:
        startActivity(new Intent(getActivity(), UserTweetList.class));
        return true;

      case R.id.action_settings:
        startActivity(new Intent(getActivity(), Settings.class));
        return true;

      case R.id.action_logout:
        MyTweetApp.logout();
        startActivity(new Intent(getActivity(), Welcome.class));
        Toast logoutToast = Toast.makeText(getActivity(), "Successfully logged out :)", Toast.LENGTH_LONG);
        logoutToast.show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  /**
   * Directs the app upon shred preference update
   *
   * @param sharedPreferences Object containing modified data
   * @param key Key identifying which setting has been modified
   */
  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    String value = sharedPreferences.getString(key, "null");
    if (value.equals("null")) {
      Log.v("MyTweet", "No value entered for setting");

      // Update email address
    } else if (key.equals("email")) {
      app.updateEmail(value);

      // Update password
    } else if (key.equals("password")) {
      app.updatePassword(value);
    } else {
      Log.v("MyTweet", "Error handling shared preference, key not identified");
    }

  }

  @Override
  public void onStop()
  {
    super.onStop();
    prefs.unregisterOnSharedPreferenceChangeListener(this);
  }
}