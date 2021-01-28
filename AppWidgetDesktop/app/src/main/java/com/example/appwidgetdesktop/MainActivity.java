package com.example.appwidgetdesktop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Filter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
  refreshAppWidgetService.MyBinder myBinder;
  TextView textView;
myReceiver receiver ;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.textview);

    // 创建一个广播接收器
   receiver = new myReceiver();
    // 创建一个意图过滤器，只处理指定事件来源的广播
    IntentFilter filter = new IntentFilter("com.kevin.appwidgetdesktop");
    // 注册广播接收器，注册之后才能正常接收广播
    getApplicationContext().registerReceiver(receiver, filter);
  }

  public class myReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (myBinder == null) {
      Log.d("app widget", "my binder null");
    } else {

Log.d("app widget", "receive broadcast :refresh time");
      int time = myBinder.getTime();
      SimpleDateFormat formatter = new SimpleDateFormat("HH 小时:mm 分钟:ss 秒");
      formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
      String hms = formatter.format(time * 1000);
      textView.setText("微件已运行时间：" + "\n\n" + hms);
    }

    }
  }


  @Override
  protected void onResume() {
    super.onResume();
    // binder 绑定 service
    Intent intent = new Intent(this, com.example.appwidgetdesktop.refreshAppWidgetService.class);
    MyServiceConnect myServiceConnect = new MyServiceConnect();
    bindService(intent, myServiceConnect, Context.BIND_AUTO_CREATE);
//    if (myBinder == null) {
//      Log.d("app widget", "my binder null");
//    } else {
//
//
//      int time = myBinder.getTime();
//      SimpleDateFormat formatter = new SimpleDateFormat("HH 小时:mm 分钟:ss 秒");
//      formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
//      String hms = formatter.format(time * 1000);
//      textView.setText("微件已运行时间：" + "\n\n" + hms);
//    }
  }

  private class MyServiceConnect implements ServiceConnection {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      myBinder = (refreshAppWidgetService.MyBinder) service;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      myBinder = null;
    }
  }
}
