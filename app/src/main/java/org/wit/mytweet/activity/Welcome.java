package org.wit.mytweet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;

/**
 * @author michaelfoy
 * @version 2016.10.03
 * @file Welcome.java
 * @brief Class to provide functionality to welcome.xml layout
 */
public class Welcome extends AppCompatActivity implements View.OnClickListener {

  private Button loginButton;
  private Button signupButton;

  /**
   * Activates the layout and instantiates it's widgets
   *
   * @param savedInstanceState Saved data pertaining to the activity
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.welcome);

    loginButton = (Button) findViewById(R.id.logIn);
    signupButton = (Button) findViewById(R.id.signUp);
    signupButton.setOnClickListener(this);
    loginButton.setOnClickListener(this);
  }

  /**
   * Starts the login or signup activity
   */
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.logIn:
        startActivity(new Intent(this, LogIn.class));
        break;
      case R.id.signUp:
        startActivity(new Intent(this, SignUp.class));
        break;
      default:
        break;
    }
  }

  /**
   * Ensures that back button disabled when user logged out
   */
  @Override
  public void onBackPressed() {
    if(MyTweetApp.getCurrentUser() == null) {
      Toast toast = Toast.makeText(this, "Nobody's logged in! :(", Toast.LENGTH_SHORT);
      toast.show();
      startActivity(new Intent(this, Welcome.class));
    }
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }
}
