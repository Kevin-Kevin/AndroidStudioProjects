package com.example.netflow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {
  AppListAdapter appAdapter;
  ArrayList<ListForAppListAdapter.listChildInformation> list;
  ArrayList<ListForAppListAdapter.listChildInformation> listToShow;

  @SuppressLint("ResourceAsColor")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        toolbar.setLogo(R.mipmap.ic_launcher_round);
//
//        toolbar.setTitle("流量");
//        toolbar.setSubtitle("net");
//        toolbar.setTitleTextColor(R.color.black);
//        toolbar.setSubtitleTextColor(R.color.black);
//        toolbar.setBackgroundResource(R.color.colorPrimaryDark);
//        setSupportActionBar(toolbar);


  }

  @Override
  protected void onResume() {
    super.onResume();
    ListForAppListAdapter listForAppListAdapter =
            new ListForAppListAdapter(getApplicationContext());
    list = listForAppListAdapter.getPackageList(1);


//      for (ListForAppListAdapter.listChildInformation listchild : list) {
//        uId = listchild.packageInfo.applicationInfo.uid;
//        //Log.i("reflash run uid ",u);
//
//        long rx = getPackageRxDayBytesWifi(uId);
//        long tx = getPackageTxDayBytesWifi(uId);
//        listchild.rx = rx - listchild.rx;
//        listchild.tx = tx - listchild.tx;
//      }

        //按 tx 进行排序
        Collections.sort(list, new Comparator<ListForAppListAdapter.listChildInformation>() {

          @Override
          public int compare(ListForAppListAdapter.listChildInformation o1, ListForAppListAdapter.listChildInformation o2) {

            return (int)o2.tx-(int)o1.tx;
          }


        });

    //listToShow = getPackageList(0);
    ListView listView = findViewById(R.id.listview);
    Context context = this;
    appAdapter = new AppListAdapter(context, list);
    listView.setAdapter(appAdapter);
    // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
    listView.setOnItemClickListener(new MySelectedListener());
    new getAppNetFlow().start();
    hasPermissionToReadNetworkStats();




  }

  /*
   * -----------------
   * 后台获取应用流量线程
   * -----------------
   */
  private class getAppNetFlow extends Thread {
    @Override
    public void run() {
      while (true) {

        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        mHandler.sendEmptyMessage(1);
        Log.i("thread run", "run");
      }
    }
  }


  @SuppressLint("HandlerLeak")
  private final Handler mHandler = new Handler() {
    public void handleMessage(Message message) {
      try {
        reflashCurrentNetworkFlow();
      } catch (IOException e) {
        e.printStackTrace();

      }
    }
  };
  private void reflashCurrentNetworkFlow() throws IOException {


    //获取每个应用程序在操作系统内的进程id
    int uId;


    NetworkStatsGetHelper networkStatsGetHelper = new NetworkStatsGetHelper(getApplicationContext());
    ListView listView = findViewById(R.id.listview);


    int start = listView.getFirstVisiblePosition();
    for (int i = start, j = listView.getLastVisiblePosition(); i <= j; i++) {
//
      ListForAppListAdapter.listChildInformation listchild = list.get(i);
      uId = listchild.packageInfo.applicationInfo.uid;

      PackageInfo packageInfo = listchild.packageInfo;
      PackageManager pm = getApplicationContext().getPackageManager();
      String n = packageInfo.applicationInfo.loadLabel(pm).toString();
      if (n.contentEquals("百度网盘")) {
        Log.d("MYLOG", packageInfo.applicationInfo.loadLabel(pm).toString());

        long tx = networkStatsGetHelper.getPackageTxBytesWifi(packageInfo.applicationInfo.uid);
        // tx = tx / 1024;
        long rx = networkStatsGetHelper.getPackageRxBytesWifi(packageInfo.applicationInfo.uid);
        // rx = rx / 1024;
        sleep(1000);

        // get data usage from networkStatsManager using wifi
        Log.d("MYLOG",
                packageInfo.applicationInfo.loadLabel(pm).toString() + " rx : " + String.valueOf(rx) + "KB");
        Log.d("MYLOG", packageInfo.applicationInfo.loadLabel(pm).toString() + " tx : " + String.valueOf(tx) + "KB");
        listchild.tx = networkStatsGetHelper.getPackageTxBytesWifi(uId);
        listchild.rx = networkStatsGetHelper.getPackageRxBytesWifi(uId);

        String t = String.valueOf(listchild.tx);
        String r = String.valueOf(listchild.rx);

        Log.i("百度 run", r + " " + t);

      }


      listchild.tx = networkStatsGetHelper.getPackageTxBytesWifi(uId);
      listchild.rx = networkStatsGetHelper.getPackageRxBytesWifi(uId);


      View view = listView.getChildAt(i - start);
      listView.getAdapter().getView(i, view, listView);
      String si = String.valueOf(i);
      String tx = String.valueOf(listchild.tx);
      String rx = String.valueOf(listchild.rx);

      Log.i("reflash run", rx + " " + rx);
    }

  }

  // 列表 点击 事件
  class MySelectedListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Toast.makeText(MainActivity.this, "click" + position, Toast.LENGTH_LONG).show();
    }
  }


  private boolean hasPermissionToReadNetworkStats() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }
    final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
    int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(), getPackageName());
    if (mode == AppOpsManager.MODE_ALLOWED) {
      return true;
    }
    mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_READ_PHONE_STATE, android.os.Process.myUid()
            , getPackageName());
    if (mode == AppOpsManager.MODE_ALLOWED) {
      return true;
    }
    requestReadNetworkStats();

    return false;
  }

  // 打开“有权查看使用情况的应用”页面
  private void requestReadNetworkStats() {
    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
    startActivity(intent);

  }


}


