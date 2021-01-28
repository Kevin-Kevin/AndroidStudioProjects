package com.kevin.appfileio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  Context context;
  TextView dirTextView;
  TextView fileTextView;
  File dir;
  AppDatabase db;
  UserDao userDao;
  List<User> list;

  @SuppressLint("StaticFieldLeak")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    dirTextView = findViewById(R.id.dictionary);
    fileTextView = findViewById(R.id.file);

    dir = context.getFilesDir();

    String dirStr = dir.toString();
    dirTextView.setText(dirStr);

    Button button = findViewById(R.id.button);
    button.setOnClickListener(this);

    db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "MyDataBase").build();




//    new AsyncTask<UserDao, Void, Void>() {
//
//      @Override
//      protected Void doInBackground(UserDao... userDaos) {
//        userDao = db.getUserDao();
//        User kevin = new User();
//        kevin.firstName = "zane";
//        kevin.lastName = "kevin";
//        kevin.uid = (int) System.currentTimeMillis();
//        userDao.insertAll(kevin);
//        return null;
//      }
//    }.execute();
    new Thread(new Runnable() {
      @Override
      public void run() {
        userDao = db.getUserDao();
        User kevin = new User();
        kevin.firstName = String.valueOf(System.currentTimeMillis());
        kevin.lastName = "kevin";
        kevin.uid = (int) System.currentTimeMillis();
        userDao.insertAll(kevin);
      }
    }).start();

  }


    User user;
  StringBuffer str;
  @SuppressLint("StaticFieldLeak")
  @Override
  public void onClick(View v) {

    new Thread(new Runnable() {
      @Override
      public void run() {

        User kevin = new User();
        kevin.firstName = String.valueOf(System.currentTimeMillis());
        kevin.lastName = "kevin";
        kevin.uid = (int) System.currentTimeMillis();

        userDao.insertAll(kevin);
      }
    }).start();

    new AsyncTask<UserDao, Void, Void>() {

      @Override
      protected Void doInBackground(UserDao... userDaos) {

        list = userDao.getAll();
        int size = list.size();
        user = list.get(size-1);
        str = new StringBuffer();
        str.append(user.firstName + " " + user.lastName);
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
            dirTextView.setText(str);
      }
    }.execute(userDao);





    // 创建文件
    File file = new File(context.getFilesDir().toString() + "/test.txt");
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


  }

  private static class UpdateAsyncTask extends AsyncTask<UserDao, Void, Void> {

    @Override
    protected Void doInBackground(UserDao... userDaos) {

      return null;
    }
  }
}
