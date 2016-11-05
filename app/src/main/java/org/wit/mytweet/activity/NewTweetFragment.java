package org.wit.mytweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.android.helpers.ContactHelper;
import org.wit.mytweet.model.Tweet;

import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.R;

import static org.wit.android.helpers.IntentHelper.navigateUp;
import static org.wit.android.helpers.IntentHelper.selectContact;
import static org.wit.android.helpers.ContactHelper.sendEmail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @file NewTweetFragment.java
 * @brief Controller for NewTweet fragment
 * @version 2016.11.01
 * @author michaelfoy
 */
public class NewTweetFragment extends Fragment implements TextWatcher, View.OnClickListener {

  private Button emailButton;
  private Button contactButton;
  private Button tweetButton;
  private EditText tweetText;
  private Tweet tweet;
  private TextView date;
  private TextView chars;
  private int counter;
  private int tweetLength;
  private static final int REQUEST_CONTACT = 1;
  private String emailAddress;

  /**
   * Activates the layout and instantiates it's widgets
   *
   * @param savedInstanceState Saved data pertaining to the activity
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    // Retrieves the currently loaded tweet
    tweet = MyTweetApp.getTempTweet();
    Log.v("MyTweet", "Tweet: " + tweet.id + " , From: " + tweet.getTweeterName());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    super.onCreateView(inflater, parent, savedInstanceState);
    View v = inflater.inflate(R.layout.newtweet, parent, false);
    //NewTweet newTweet = (NewTweet)getActivity();
    NewTweet.actionBar.setDisplayHomeAsUpEnabled(true);
    addListeners(v);
    resetCounter();
    date.setText(editDate());
    // Checks if the tweet has already been persisted
    checkForTweet(tweet);
    return v;
  }

  private void addListeners(View v) {
    emailButton = (Button) v.findViewById(R.id.emailButton);
    contactButton = (Button) v.findViewById(R.id.contactButton);
    tweetButton = (Button) v.findViewById(R.id.tweetButton);
    tweetText = (EditText) v.findViewById(R.id.tweet);
    chars = (TextView) v.findViewById(R.id.chars);
    date = (TextView) v.findViewById(R.id.date);

    tweetText.addTextChangedListener(this);
    tweetButton.setOnClickListener(this);
    contactButton.setOnClickListener(this);
    emailButton.setOnClickListener(this);
  }

  /**
   * Checks if the tweet has been persisted,
   * if so, this is a logged-in user viewing his/her own tweet.
   * TweetText  tweetButton disabled, tweeter can only email tweet
   *
   * @param checkTweet The currently displayed tweet
   */
  private void checkForTweet(Tweet checkTweet) {
    List<Tweet> allTweets = MyTweetApp.dbHelper.selectAllTweets();
    for (Tweet tweet : allTweets) {
      if (checkTweet.getContent().equals(tweet.getContent())) {
        date.setText(tweet.getDate());
        tweetText.setText(tweet.getContent());
        chars.setText("");
        tweetText.setSingleLine(false);
        tweetText.setEnabled(false);
        tweetButton.setEnabled(false);
      }
    }
  }

  /**
   * Displays actionbar menu on page
   *
   * @param menu Menu object to be displayed
   * @param inflater The MenuInflater to create the menu
   */
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
  {
    inflater.inflate(R.menu.mytweet_menu, menu);
    super.onCreateOptionsMenu(menu,inflater);
  }

  /**
   * Returns true if selected menu item is implemented
   *
   * @param item The selected menu item
   * @return True if item implemented
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        navigateUp(getActivity());
        return true;

      case R.id.menuItemNewTweet:
        Tweet tweet = new Tweet();
        tweet.setId();
        tweet.setTweeter(MyTweetApp.getCurrentUser().id.toString());
        MyTweetApp.setTempTweet(tweet);
        startActivity(new Intent(getActivity(), NewTweet.class));
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
   * Formats the date input for output
   */
  public String editDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yyyy, H:mm");
    Date today = new Date();
    return formatter.format(today);
  }

  /**
   * Retrieves the current length of the tweet message
   *
   * @param charSequence The tweet message
   * @param i            The first character of the sequence
   * @param i1           The length of the message
   * @param i2           Length of new message
   */
  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    tweetLength = charSequence.length();
  }

  /**
   * Modifies the counter according to the number of allowable characters remaining
   *
   * @param editable The changed tweet message
   */
  @Override
  public void afterTextChanged(Editable editable) {
    if (editable.length() <= 0) {
      resetCounter();
      chars.setText("" + counter);
    } else if (editable.length() > tweetLength) {
      chars.setText("" + (counter -= 1));
    } else if (editable.length() < tweetLength) {
      chars.setText("" + (counter += 1));
    }
  }

  /**
   * Implements functionality for the 'tweet', 'contacts' and 'email' buttons
   *
   * @param view The button which has been clicked
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tweetButton:
        if (tweetText.getText().toString().length() > 0) {
          postTweet();
        } else {
          Toast toast = Toast.makeText(getActivity(), "You forgot the tweet!", Toast.LENGTH_SHORT);
          toast.show();
        }
        break;
      case R.id.contactButton:
        selectContact(getActivity(), REQUEST_CONTACT);
        break;
      case R.id.emailButton:
        sendEmail(getActivity(), emailAddress, "New MyTweet from: " + tweet.getTweeterName(), tweet.getContent());
        break;
    }
  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case REQUEST_CONTACT:
        emailAddress = ContactHelper.getEmail(getActivity(), data);
        contactButton.setText("Send to : " + emailAddress);
        break;
    }
  }

  /**
   * Method to reset the tweet text counter
   */
  private void resetCounter() {
    counter = 140;
    chars.setText("" + counter);
  }

  /**
   * Method to post and save a tweet, resets tweet text area
   */
  private void postTweet() {
    String content = tweetText.getText().toString();
    String dateStr = date.getText().toString();
    tweet.setContent(content);
    tweet.setDate(dateStr);
    MyTweetApp.dbHelper.addTweet(tweet);
    MyTweetApp.deleteTempTweet();

    Toast toast = Toast.makeText(getActivity(), "Tweet posted", Toast.LENGTH_SHORT);
    toast.show();
    Log.v("MyTweet", "Tweet posted by " + MyTweetApp.getCurrentUser().getFirstName() + " " + MyTweetApp.getCurrentUser().getLastName());
    startActivity(new Intent(getActivity(), TweetList.class));
  }
}