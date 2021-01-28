package com.kevin.BatteryChangedRecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 电池使用情况记录
 * - 广播接收器接收电池状态改变的广播
 * - 每次都写入到 应用内存存储空间的 batteryRecord.kevin 中
 * - 以图表📈形式显示数据
 */


public class MainActivity extends AppCompatActivity {
  Context context;
  ArrayList<BatteryChangedTimeInformation> batteryList;
  BatteryDatabase db;
  BatteryChangedTimeInformationDao batteryChangedTimeInformationDao;

  ListView listView;
  File dictionary;
  BatteryListAdapter adapter;
  @SuppressLint("StaticFieldLeak")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    listView = findViewById(R.id.listview);
    batteryList = new ArrayList<BatteryChangedTimeInformation>();

      MyReceiver receiver;
      IntentFilter filter;
// 创建一个广播接收器
    receiver = new MyReceiver();
    // 创建一个意图过滤器，只处理指定事件来源的广播
    filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    // 注册广播接收器，注册之后才能正常接收广播
    getApplicationContext().registerReceiver(receiver, filter);



//
//    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//    Intent batteryStatus = (null, ifilter);
    // 创建数据库
    db = Room.databaseBuilder(getApplicationContext(), BatteryDatabase.class, "MyDataBase").build();


    new AsyncTask<BatteryChangedTimeInformationDao, Void, Void>() {

      @Override
      protected Void doInBackground(BatteryChangedTimeInformationDao... userDaos) {

        // 获取数据库访问 DAO 实例
        batteryChangedTimeInformationDao = db.getUserDao();
        // 获取数据列表
        batteryList =
                (ArrayList<BatteryChangedTimeInformation>) batteryChangedTimeInformationDao.getAll();

        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // 实例化适配器
        adapter = new BatteryListAdapter(context, batteryList);
        // listview 使用适配器显示信息
        listView.setAdapter(adapter);
      }
    }.execute(batteryChangedTimeInformationDao);



  }

  public class MyReceiver extends BroadcastReceiver {

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onReceive(final Context context, Intent intent) {

      Log.d("app battery","receive");
      Bundle bundle = intent.getExtras();
      // 获取当前电量
      int current = bundle.getInt("level");
      // 获取总电量
      int total = bundle.getInt("scale");
      final int batteryRemained = current * 100 / total;
      long currentTime = System.currentTimeMillis();

//      SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
//      Date curDate = new Date(System.currentTimeMillis());
//      //获取当前时间
//      String timeStr = formatter.format(curDate);

      // 记录电量信息


//      MyRunnable mythread = new MyRunnable() {
//
//        int batteryRemained;
//        long currentTime;
//
//        @Override
//        public void setParam(int batteryRemained, long currentTime) {
//          this.batteryRemained = batteryRemained;
//          this.currentTime = currentTime;
//        }
//
//        public void run() {
//
//          BatteryChangedTimeInformation batteryChangedTimeInformation = new BatteryChangedTimeInformation();
//          batteryChangedTimeInformation.batteryRemained = batteryRemained;
//          batteryChangedTimeInformation.currentTime = currentTime;
//
//          batteryChangedTimeInformationDao.insertAll(batteryChangedTimeInformation);
//        }
//      };


    new AsyncTask<BatteryChangedTimeInformationDao, Void, Void>() {

      @Override
      protected Void doInBackground(BatteryChangedTimeInformationDao... userDaos) {

        // 获取数据库访问 DAO 实例
        batteryChangedTimeInformationDao = db.getUserDao();


        BatteryChangedTimeInformation batteryChangedTimeInformation =
                new BatteryChangedTimeInformation();
        batteryChangedTimeInformation.batteryRemained = batteryRemained;
        batteryChangedTimeInformation.currentTime = System.currentTimeMillis();
        batteryChangedTimeInformation.id = (int)System.currentTimeMillis();
        batteryChangedTimeInformationDao.insertAll(batteryChangedTimeInformation);


        // 获取数据列表
        batteryList =
                (ArrayList<BatteryChangedTimeInformation>) batteryChangedTimeInformationDao.getAll();




        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

      // ListView 更新
      adapter.notifyDataSetChanged();
      Log.d("app battery","refresh"+" "+String.valueOf(batteryList.size()));

      }
    }.execute(batteryChangedTimeInformationDao);




//
//      // 链表添加电量信息
//      batteryList.add(batteryChangedTimeInformation);
//      // ListView 更新
//      BatteryListAdapter adapter = (BatteryListAdapter) listView.getAdapter();
//      adapter.notifyDataSetChanged();

    }
  }



}
