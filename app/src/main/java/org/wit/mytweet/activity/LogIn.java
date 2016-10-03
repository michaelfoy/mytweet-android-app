package org.wit.mytweet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;

/**
 * @file Login.java
 * @brief Class to provide functionality to activity_login.xml layout
 * @version 2016.09.25
 * @author michaelfoy
 */
public class LogIn extends AppCompatActivity implements View.OnClickListener{

  private Button loginButton;
  private EditText email;
  private EditText password;
  private MyTweetApp app;

  /**
   * Activates the layout and instantiates it's widgets
   *
   * @param savedInstanceState Saved data pertaining to the activity
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

    loginButton = (Button) findViewById(R.id.loginButton);
    loginButton.setOnClickListener(this);
    email = (EditText) findViewById(R.id.email);
    password = (EditText) findViewById(R.id.password);
    app = (MyTweetApp) getApplication();
  }

  /**
   * If user data is correct, logs in the user.
   */
  @Override
  public void onClick (View view) {
    String emailStr = email.getText().toString();
    String passwordStr = password.getText().toString();

    if (app.registeredUser(emailStr, passwordStr)) {
      startActivity(new Intent(this, NewTweet.class));
      Log.v("MyTweet", "Log in successful");
    } else {
      Toast toast = Toast.makeText(this, "Log in unsuccessful", Toast.LENGTH_SHORT);
      toast.show();
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
