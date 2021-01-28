package com.example.appwidgetdesktop;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class refreshAppWidgetService extends Service {
  RefreshThread refreshThread;
  public int time = 0;
  boolean threadRunning = true;
  IBinder binder = new MyBinder();


  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Log.d("app widget", "on bind");
    return binder;
  }

  public class MyBinder extends Binder {
    public refreshAppWidgetService getServices() {
      return refreshAppWidgetService.this;
    }

    public int getTime() {
      Log.d("app widget", "get time");
      return time;
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    refreshThread = new RefreshThread();
    refreshThread.start();
  }

  class RefreshThread extends Thread {
    @Override
    public void run() {
      super.run();
      while (threadRunning) {
        // 睡眠一秒
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Log.d("refresh sevice", "Interrupted Exception");
        }
        time++;
        Log.d("app widge", "thread running..." + String.valueOf(time));

        // 发送 broadcast 刷新 appWidget
        String action = new String(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Intent intent = new Intent(action);
        //intent.setClass(getApplicationContext(), MyAppWidgetProvider.class);
        sendBroadcast(intent);

        action = new String("com.kevin.appwidgetdesktop");
        intent = new Intent(action);
        //intent.setClass(getApplicationContext(), MainActivity.class);
        sendBroadcast(intent);

        Log.d("app widget", "sent broadcast " + action);
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    threadRunning = false;
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
  }
}
