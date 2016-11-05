package org.wit.mytweet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.wit.mytweet.R;

public class Settings extends AppCompatActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      SettingsFragment fragment = new SettingsFragment();
      getFragmentManager().beginTransaction()
          .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
          .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.mytweet_menu, menu);
    return true;
  }

}