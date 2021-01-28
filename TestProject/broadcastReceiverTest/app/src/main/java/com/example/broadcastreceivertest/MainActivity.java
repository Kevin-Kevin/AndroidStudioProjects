package com.example.broadcastreceivertest;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  myReceiver receiver;
  IntentFilter filter;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    // 创建一个闹钟的广播接收器
    receiver = new myReceiver();
    // 创建一个意图过滤器，只处理指定事件来源的广播
    filter = new IntentFilter(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//    filter.addAction(AppWidgetManager.ACTION_APPWIDGET_DELETED);
    filter.addAction("com.kevin.nihao");
    // 注册广播接收器，注册之后才能正常接收广播
    getApplicationContext().registerReceiver(receiver, filter);

  }

  public class myReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

      Log.d("Alarm", "my receive " + intent.getAction());

    }
  }
}