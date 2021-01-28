package com.example.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;

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
    filter.addAction(AppWidgetManager.ACTION_APPWIDGET_DELETED);
    filter.addAction("com.kevin.nihao");
    // 注册广播接收器，注册之后才能正常接收广播
    getApplicationContext().registerReceiver(receiver, filter);

  }

  public void onclick(View v) {

Intent intent = new Intent();
    intent.setAction("com.kevin.nihao");

context.sendBroadcast(intent);

    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(), 1000, pendingIntent);


    Log.d("Alarm", "send broadcast");
  }

  public class myReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

      Log.d("Alarm", "my receive " + intent.getAction());

    }
  }

}
