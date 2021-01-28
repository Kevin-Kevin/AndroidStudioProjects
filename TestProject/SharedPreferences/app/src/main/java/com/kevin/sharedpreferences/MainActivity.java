package com.kevin.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLClientInfoException;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // sharedPreferences 共享参数
    SharedPreferences shared = getSharedPreferences("share", MODE_PRIVATE);
    SharedPreferences.Editor editor = shared.edit();
    editor.putString("DeveloperName","Kevin");
    editor.commit();
         String DeveloperName = shared.getString("DeveloperName","");
    Toast.makeText(getApplicationContext(),DeveloperName,Toast.LENGTH_SHORT).show();





  }

  @Override
  protected void onResume() {
    super.onResume();

  }
}

