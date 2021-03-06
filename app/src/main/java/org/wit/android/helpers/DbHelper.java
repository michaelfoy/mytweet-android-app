package org.wit.android.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.wit.mytweet.model.Tweet;
import org.wit.mytweet.model.User;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @file DbHelper.java
 * @brief Class to provide helper methods to create, access and update a mysqlite db
 * @author michaelfoy
 * @version 2016.10.17
 */
public class DbHelper extends SQLiteOpenHelper {
  static final String TAG = "DbHelper";
  static final String DATABASE_NAME = "myTweet.db";
  static final int DATABASE_VERSION = 1;
  static final String TABLE_TWEETS = "tableTweets";
  static final String TABLE_USERS = "tableUsers";

  static final String PRIMARY_KEY = "id";
  static final String CONTENT = "content";
  static final String TWEETER = "tweeter";
  static final String DATE = "date";
  static final String FIRSTNAME = "firstName";
  static final String LASTNAME = "lastName";
  static final String EMAIL = "email";
  static final String PASSWORD = "password";

  Context context;

  /**
   * Creates a new bdHelper object
   *
   * @param context The application context
   */
  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  /**
   * Creates an instance of the database
   *
   * @param db Baseline database to be populated
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    String createTweetTable =
        "CREATE TABLE tableTweets " +
            "(id text primary key, " +
                "content text, date text, " +
                    "tweeter text, FOREIGN KEY (tweeter) REFERENCES tableUsers(id))";

    String createUserTable =
        "CREATE TABLE tableUsers " +
            "(id text primary key, " +
                "firstName text, lastName text," +
                    "email text, password text)";

    db.execSQL(createTweetTable);
    db.execSQL(createUserTable);
    populate(db);
    Log.d(TAG, "DbHelper.onCreated: " + createTweetTable + "; " + createUserTable);
  }

  /**
   * Adds a tweet to the database
   *
   * @param tweet Reference to Tweet object to be added to database
   */
  public void addTweet(Tweet tweet) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(PRIMARY_KEY, tweet.id.toString());
    values.put(CONTENT, tweet.getContent());
    values.put(DATE, tweet.getDate());
    values.put(TWEETER, tweet.getTweeter().toString());
    // Insert record
    db.insert(TABLE_TWEETS, null, values);
    db.close();
  }

  /**
   * Adds a user to the database
   *
   * @param user Reference to the User object to be added
   */
  public void addUser(User user) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(PRIMARY_KEY, user.id.toString());
    values.put(FIRSTNAME, user.getFirstName());
    values.put(LASTNAME, user.getLastName());
    values.put(EMAIL, user.getEmail());
    values.put(PASSWORD, user.getPassword());
    // Insert record
    db.insert(TABLE_USERS, null, values);
    db.close();
  }

  /**
   * Query database and select entire tableUsers.
   *
   * @return A list of User object records
   */
  public List<User> selectAllUsers() {
    List<User> users = new ArrayList<User>();
    String query = "SELECT * FROM " + "tableUsers";
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      int columnIndex = 0;
      do {
        User user = new User();
        user.id = UUID.fromString(cursor.getString(columnIndex++));
        user.setFirstName(cursor.getString(columnIndex++));
        user.setLastName(cursor.getString(columnIndex++));
        user.setEmail(cursor.getString(columnIndex++));
        user.setPassword(cursor.getString(columnIndex++));

        columnIndex = 0;

        users.add(user);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return users;
  }

  /**
   * Query database and select entire tableTweets.
   *
   * @return A list of Tweet object records
   */
  public List<Tweet> selectAllTweets() {
    List<Tweet> tweets = new ArrayList<Tweet>();
    String query = "SELECT * FROM " + "tableTweets";
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      int columnIndex = 0;
      do {
        Tweet tweet = new Tweet();
        tweet.id = UUID.fromString(cursor.getString(columnIndex++));
        tweet.setContent(cursor.getString(columnIndex++));
        tweet.setDate(cursor.getString(columnIndex++));
        tweet.setTweeter(cursor.getString(columnIndex++));

        columnIndex = 0;

        tweets.add(tweet);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return tweets;
  }

  /**
   * Returns all the tweets from an individual user
   *
   * @param id Id of the individual user
   * @return List of the user's tweets
   */
  public List<Tweet> getAllTweetsForUser(String id){
    List<Tweet> tweets = new ArrayList<Tweet>();
    Log.v("MyTweet", "Getting all tweets for user with id: " + id);
    String query = "SELECT * FROM " + "tableTweets WHERE TWEETER='" + id +"'";
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      int columnIndex = 0;
      do {
        Tweet tweet = new Tweet();
        tweet.id = UUID.fromString(cursor.getString(columnIndex++));
        tweet.setContent(cursor.getString(columnIndex++));
        tweet.setDate(cursor.getString(columnIndex++));
        tweet.setTweeter(cursor.getString(columnIndex++));

        columnIndex = 0;

        tweets.add(tweet);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return tweets;
  }

  /**
   * Deletes an individual tweet
   *
   * @param id Id of the tweet to be deleted
   */
  public void deleteTweet(String id) {
    SQLiteDatabase db = this.getWritableDatabase();
    Log.v("MyTweet", "Deleting tweet with id: " + id);
    db.execSQL("DELETE FROM " + TABLE_TWEETS + " WHERE ID= '" + id + "'");
  }

  public void updateUserData(String column, String value, String userId) {

    String query = "ID='" + userId +"'";
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(column, value);
    db.update("tableUsers", values, query, null );
  }

  /**
   * Populates db on startup with Simpsons data
   *
   * @param db The database to populate
   */
  private void populate(SQLiteDatabase db) {
    String date;
    User homer = new User("Homer", "Simpson", "homer@simpson.com", "secret");
    User marge = new User("Marge", "Simpson", "marge@simpson.com", "secret");
    User bart = new User("Bart", "Simpson", "bart@simpson.com", "secret");
    User lisa = new User("Lisa", "Simpson", "lisa@simpson.com", "secret");

    date = "Sun. 6 Nov 2016, 11:52";
    Tweet tweet1 = new Tweet("Marge can we have meatloaf tonight", date, homer);
    date = "Sun. 6 Nov 2016, 11:53";
    Tweet tweet2 = new Tweet("Homer, we're in Ireland, they don't have meatloaf", date, marge);
    date = "Sun. 6 Nov 2016, 11:54";
    Tweet tweet3 = new Tweet("What is meatloaf?", date, bart);
    date = "Sun. 6 Nov 2016, 11:55";
    Tweet tweet4 = new Tweet("You don't want to know", date, lisa);
    date = "Sun. 6 Nov 2016, 11:56";
    Tweet tweet5 = new Tweet("Shut up Lisa!", date, bart);
    date = "Sun. 6 Nov 2016, 11:57";
    Tweet tweet6 = new Tweet("Bart! I'm afraid your sister's right", date, marge);
    date = "Sun. 6 Nov 2016, 11:58";
    Tweet tweet7 = new Tweet("I hear people eat blood in a place called Clonakilty", date, bart);
    date = "Sun. 6 Nov 2016, 11:59";
    Tweet tweet8 = new Tweet("Let's go! We can stop in Middleton on the way :)", date, homer);
    date = "Sun. 6 Nov 2016, 12:00";
    Tweet tweet9 = new Tweet("???!!", date, marge);

    populateUser(homer,db);
    populateUser(marge,db);
    populateUser(bart,db);
    populateUser(lisa,db);

    populateTweet(tweet1, db);
    populateTweet(tweet2, db);
    populateTweet(tweet3, db);
    populateTweet(tweet4, db);
    populateTweet(tweet5, db);
    populateTweet(tweet6, db);
    populateTweet(tweet7, db);
    populateTweet(tweet8, db);
    populateTweet(tweet9, db);
  }

  /**
   * Populates a default user to the database
   *
   * @param user Reference to the User object to be added
   */
  public void populateUser(User user, SQLiteDatabase db) {
    ContentValues values = new ContentValues();
    values.put(PRIMARY_KEY, user.id.toString());
    values.put(FIRSTNAME, user.getFirstName());
    values.put(LASTNAME, user.getLastName());
    values.put(EMAIL, user.getEmail());
    values.put(PASSWORD, user.getPassword());
    // Insert record
    db.insert(TABLE_USERS, null, values);;
  }

  /**
   * Populates a default tweet to the database
   *
   * @param tweet Reference to Tweet object to be added to database
   */
  public void populateTweet(Tweet tweet, SQLiteDatabase db) {
    ContentValues values = new ContentValues();
    values.put(PRIMARY_KEY, tweet.id.toString());
    values.put(CONTENT, tweet.getContent());
    values.put(DATE, tweet.getDate());
    values.put(TWEETER, tweet.getTweeter().toString());
    // Insert record
    db.insert(TABLE_TWEETS, null, values);
  }

  /**
   * Upgrades previous version of the data base with new version
   *
   * @param db The database to be updated
   * @param oldVersion Version number of old database
   * @param newVersion Version number of new database
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists " + TABLE_TWEETS);
    db.execSQL("drop table if exists " + TABLE_USERS);
    Log.d(TAG, "onUpdated");
    onCreate(db);
  }
}