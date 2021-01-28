package com.example.appwidgetdesktop;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.File;
import java.util.Calendar;


public class MyAppWidgetProvider extends AppWidgetProvider {

  static long totalTx = 0;
  static long totalRx = 0;
  static int flag = 1;

  public MyAppWidgetProvider() {
    super();
  }


  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.d("app widget", "on enabled");
    // setAlarm(context);
    strartRefreshService(context);

  }


  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);

    Log.d("app widget", "widget receive " + intent.getAction());
    // 刷新 流量
    NetWorkString netWorkString = GetNetworkFlowString(context);
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    RemoteViews widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget);
    widgetViews.setTextViewText(R.id.tx, netWorkString.tx);
    widgetViews.setTextViewText(R.id.rx, netWorkString.rx);
    // 刷新 ram rom 容量

    //获取手机内部可用空间大小
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    am.getMemoryInfo(memoryInfo);

    // memoryInfo.availMem 当前系统的可用内存 , 将获取的内存大小规格化输出

    widgetViews.setTextViewText(R.id.ramString, Formatter.formatFileSize(context, memoryInfo.availMem));

    // 获取手机内部可用空间大小
    File path = Environment.getDataDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockFreeSize = stat.getFreeBytes();
    String blockFreeSizeString = Formatter.formatFileSize(context, blockFreeSize);
    widgetViews.setTextViewText(R.id.romString, blockFreeSizeString);// 将获取的内存大小规格化输出
    appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class),
            widgetViews);
    Log.d("app widget", "on fresh " + String.valueOf(netWorkString.rx));

  }

  private NetWorkString GetNetworkFlowString(Context context) {
    NetWorkString netWorkString = new NetWorkString();

    if (flag == 1) {
      totalRx = TrafficStats.getTotalRxBytes();
      totalTx = TrafficStats.getTotalTxBytes();
      Log.d("app widget", "flag = 1" + String.valueOf(flag));
      flag = 0;

    } else {
      Log.d("app widget", "flag = 0" + String.valueOf(flag));
      long currentTotalRxBytes = TrafficStats.getTotalRxBytes();
      long currentTotalTxBytes = TrafficStats.getTotalTxBytes();
      long tx = currentTotalTxBytes - totalTx;
      long rx = currentTotalRxBytes - totalRx;
      totalRx = currentTotalRxBytes;
      totalTx = currentTotalTxBytes;



      netWorkString.rx =Formatter.formatFileSize(context, rx);;
      netWorkString.tx = Formatter.formatFileSize(context, tx);;
    }

    return netWorkString;
  }

  class NetWorkString {
    String rx;
    String tx;
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    Log.d("app widget", "on update ");

  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    Log.d("app widget", "on disabled ");
    stopRefreshService(context);
  }

  private void setAlarm(Context context) {

    Intent intent = new Intent();
    intent.setAction("com.kevin.nihao");
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
            Calendar.getInstance().getTimeInMillis(), 1000, pendingIntent);
    Log.d("Alarm", "been set");
  }

  // 开启后台服务,每秒刷新 appWidget
  private void strartRefreshService(Context context) {
    Intent intent;
    intent = new Intent(context, refreshAppWidgetService.class);
    context.startService(intent);

  }

  // 关闭服务
  private void stopRefreshService(Context context) {
    Intent intent;
    intent = new Intent(context, refreshAppWidgetService.class);
    context.stopService(intent);
  }

}


