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

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

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
    Log.d(TAG, "DbHelper.onCreated: " + createTweetTable + "; " + createUserTable);
  }

  /**
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
   * Query database and select entire tableResidences.
   *
   * @return A list of Residence object records
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
        user.firstName = cursor.getString(columnIndex++);
        user.lastName = cursor.getString(columnIndex++);
        user.email = cursor.getString(columnIndex++);
        user.password = cursor.getString(columnIndex++);

        columnIndex = 0;

        users.add(user);
      } while (cursor.moveToNext());
    }
    cursor.close();
    for (User user : users) {
      Log.v("MyTweet", "DBUSERS: " + user.firstName + " " + user.lastName + " " + user.email + " " + user.password);
    }
     return users;
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists " + TABLE_TWEETS);
    db.execSQL("drop table if exists " + TABLE_USERS);
    Log.d(TAG, "onUpdated");
    onCreate(db);
  }
}