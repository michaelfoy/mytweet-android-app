package org.wit.mytweet.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.model.Tweet;

import java.util.List;

/**
 * @file UserTweetListFragment.java
 * @brief Controller for TweetListFragment activity
 * @author michaelfoy
 * @version 2016.10.18
 */
public class UserTweetListFragment extends ListFragment implements AdapterView.OnItemClickListener {
  public MyTweetApp app;

  /**
   * Implements the layout, instantiates all fields
   *
   * @param savedInstanceState Data from previously saved instance
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    getActivity().setTitle(R.string.my_tweet_profile);
    app = MyTweetApp.getApp();
    String CurrentUserId = MyTweetApp.getCurrentUser().id.toString();
    List<Tweet> tweetList = app.getAllTweetsForUser(CurrentUserId);
    UserTweetAdapter adapter = new UserTweetAdapter(getActivity(), tweetList);
    setListAdapter(adapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View v = super.onCreateView(inflater, parent, savedInstanceState);
    return v;
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    Tweet tweet = ((UserTweetAdapter) getListAdapter()).getItem(position);
    MyTweetApp.setTempTweet(tweet);
    startActivity(new Intent(getActivity(), NewTweet.class));
  }

  /**
   * Listener for click on Tweet item in list. If selected tweet was posted
   * by the logged-in-user, option to email tweet supplied. Otherwise, tweet is read-only.
   *
   * @param parent The parent adapter view
   * @param view The view that was clicked within the adapterView
   * @param position Position of the view in the adapter
   * @param id Row id of clicked item
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
    inflater.inflate(R.menu.user_profile_menu, menu);
    super.onCreateOptionsMenu(menu,inflater);
  }

  /**
   * Returns true if selected menu item is implemented
   *
   * @param item The selected menu item
   * @return True if item implemented
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case R.id.menuItemNewTweet: Tweet tweet = new Tweet();
        tweet.setId();
        tweet.setTweeter(MyTweetApp.getCurrentUser().id.toString());
        MyTweetApp.setTempTweet(tweet);
        startActivity(new Intent(getActivity(), NewTweet.class));
        return true;

      case R.id.action_settings:
        Toast toast1 = Toast.makeText(getActivity(), "settings", Toast.LENGTH_SHORT);
        toast1.show();
        return true;

      case R.id.action_clear_tweets:
        Toast toast2 = Toast.makeText(getActivity(), "clearing", Toast.LENGTH_SHORT);
        toast2.show();
        return true;

      case R.id.action_logout:
        MyTweetApp.logout();
        startActivity(new Intent(getActivity(), Welcome.class));
        Toast logoutToast = Toast.makeText(getActivity(), "Successfully logged out :)", Toast.LENGTH_LONG);
        logoutToast.show();
        return true;

      default: return super.onOptionsItemSelected(item);
    }
  }
}

class UserTweetAdapter extends ArrayAdapter<Tweet> {
  private Context context;
  public UserTweetAdapter(Context context, List<Tweet> tweets) {
    super(context, 0, tweets);
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.list_item_tweet, null);
    }
    Tweet tweet = getItem(position);

    TextView tweetContent = (TextView) convertView.findViewById(R.id.tweetListItemContent);
    tweetContent.setText(tweet.getContent());

    TextView dateTextView = (TextView) convertView.findViewById(R.id.tweetListItemDate);
    dateTextView.setText(tweet.getDate());

    TextView tweeter = (TextView) convertView.findViewById(R.id.tweetListItemTweeter);
    tweeter.setText("");

    return convertView;
  }
}