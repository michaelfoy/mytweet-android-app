package org.wit.mytweet.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.AbsListView;
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
public class UserTweetListFragment extends ListFragment implements AdapterView.OnItemClickListener, AbsListView.MultiChoiceModeListener {
  public MyTweetApp app;
  private ListView listView;
  private UserTweetAdapter adapter;
  private String CurrentUserId;
  private List<Tweet> tweetList;

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
    CurrentUserId = MyTweetApp.getCurrentUser().id.toString();
    tweetList = app.getAllTweetsForUser(CurrentUserId);
    adapter = new UserTweetAdapter(getActivity(), tweetList);
    setListAdapter(adapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View v = super.onCreateView(inflater, parent, savedInstanceState);
    listView = (ListView) v.findViewById(android.R.id.list);
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
    listView.setMultiChoiceModeListener(this);
    return v;
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    Tweet tweet = ((UserTweetAdapter) getListAdapter()).getItem(position);
    MyTweetApp.setTempTweet(tweet);
    startActivity(new Intent(getActivity(), UserNewTweet.class));
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
        startActivity(new Intent(getActivity(), UserNewTweet.class));
        return true;

      case R.id.action_settings:
        Toast toast1 = Toast.makeText(getActivity(), "settings", Toast.LENGTH_SHORT);
        toast1.show();
        return true;

      case R.id.action_clear_tweets:
        deleteAllTweets();
        Toast deleteToast = Toast.makeText(getActivity(), "All tweets deleted", Toast.LENGTH_SHORT);
        deleteToast.show();
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

  @Override
  public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
  {
    MenuInflater inflater = actionMode.getMenuInflater();
    inflater.inflate(R.menu.tweet_list_context, menu);
    return true;
  }

  @Override
  public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
  {
    switch (menuItem.getItemId())
    {
      case R.id.menu_item_delete_tweet:
        deleteTweet(actionMode);
        return true;
      default:
        return false;
    }
  }

  /**
   * Deletes all selected tweets
   *
   * @param actionMode Provides the alternate context for deletion
   */
  private void deleteTweet(ActionMode actionMode)
  {
    for (int i = adapter.getCount() - 1; i >= 0; i--)
    {
      if (listView.isItemChecked(i))
      {
        app.deleteTweet(adapter.getItem(i).id.toString());
      }
    }
    actionMode.finish();
    updateTweetList();
  }

  /**
   * Deletes all of a user's tweets
   */
  private void deleteAllTweets() {
    for (int i = adapter.getCount() - 1; i >= 0; i--) {
      app.deleteTweet(adapter.getItem(i).id.toString());
    }
    updateTweetList();
  }

  /**
   * Updates the list of tweets following a deletion
   */
  public void updateTweetList() {
    List<Tweet> newList = app.getAllTweetsForUser(CurrentUserId);
    tweetList.clear();
    tweetList.addAll(newList);
    adapter.notifyDataSetChanged();
  }

  @Override
  public boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
  {
    return false;
  }

  @Override
  public void onDestroyActionMode(ActionMode actionMode)
  {
  }

  @Override
  public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
  {
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

    return convertView;
  }
}