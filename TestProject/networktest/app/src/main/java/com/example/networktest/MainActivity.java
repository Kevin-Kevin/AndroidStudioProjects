package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.pm.ApplicationInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void onClick(View v) {

      printAllDataUsage();
    PackageManager pm = getPackageManager();
    // get all the applications in the phone
    List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

    NetworkStatsManager networkStatsManager = (NetworkStatsManager) getBaseContext().getSystemService(Context.NETWORK_STATS_SERVICE);
NetworkStatsHelper networkStatsHelper = new NetworkStatsHelper(networkStatsManager);
    for (ApplicationInfo packageInfo : packages) {
      networkStatsHelper.packageUid = packageInfo.uid;
      String n = packageInfo.loadLabel(pm).toString();
      if(n.contentEquals("百度网盘")){
              Log.d("MYLOG", packageInfo.loadLabel(pm).toString());

      long tx = networkStatsHelper.getPackageTxBytesWifi() ;
     // tx = tx / 1024;
      long rx = networkStatsHelper.getPackageRxBytesWifi() ;
     // rx = rx / 1024;
      sleep(1000);
      // get data usage from networkStatsManager using wifi
      Log.d("MYLOG",
              packageInfo.loadLabel(pm).toString() + " traffic rx : " + String.valueOf(rx)+"KB");
      Log.d("MYLOG", packageInfo.loadLabel(pm).toString() + " traffic tx : " + String.valueOf(tx)+"KB");
      }

    }



    }


  //when using NetworkStatsManager you need the subscriber id
  private String getSubscriberId(Context context, int networkType) {
    if (ConnectivityManager.TYPE_MOBILE == networkType) {
      TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return tm.getSubscriberId();
    }
    return "";
  }


  // to get mobile data recived
  public long getPackageRxBytesMobile(Context context, NetworkStatsManager networkStatsManager,
                                      int packageUid) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_MOBILE,
            getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
            0,
            System.currentTimeMillis(),
            packageUid);
    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
    networkStats.getNextBucket(bucket);
    networkStats.getNextBucket(bucket);
    return bucket.getRxBytes();
  }


  // to get mobile data transmitted
  public long getPackageTxBytesMobile(Context context, NetworkStatsManager networkStatsManager,
                                      int packageUid) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_MOBILE,
            getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
            0,
            System.currentTimeMillis(),
            packageUid);
    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
    networkStats.getNextBucket(bucket);
    return bucket.getTxBytes();
  }


  // to get wifi data received
  public long getPackageRxBytesWifi(NetworkStatsManager networkStatsManager, int packageUid) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_WIFI,
            "",
            0,
            System.currentTimeMillis(),
            packageUid);
    long rxBytes = 0L;
    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
    while (networkStats.hasNextBucket()) {
      networkStats.getNextBucket(bucket);
      rxBytes += bucket.getRxBytes();
    }
    networkStats.close();
    return rxBytes;
  }


  // to get wifi data transmitted
  public long getPackageTxBytesWifi(NetworkStatsManager networkStatsManager, int packageUid) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_WIFI,
            "",
            0,
            System.currentTimeMillis(),
            packageUid);
    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
    networkStats.getNextBucket(bucket);
    return bucket.getTxBytes();
  }


  // print to log all the data usage value per application
  public void printAllDataUsage() {
    PackageManager pm = getPackageManager();
    // get all the applications in the phone
    List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

    NetworkStatsManager networkStatsManager =
            (NetworkStatsManager) getBaseContext().getSystemService(Context.NETWORK_STATS_SERVICE);

    for (ApplicationInfo packageInfo : packages) {
//     Log.d("MYLOG", String.valueOf(packageInfo.uid));
//     Log.d("MYLOG", String.valueOf(packageInfo.name));
//     Log.d("MYLOG", String.valueOf(packageInfo.packageName));
//      Log.d("MYLOG", packageInfo.loadLabel(pm).toString());


//     // get data usage from networkStatsManager using mobile
//     Log.d("MYLOG", String.valueOf(getPackageRxBytesMobile(this,networkStatsManager,packageInfo
//     .uid)));
//     Log.d("MYLOG", String.valueOf(getPackageTxBytesMobile(this,networkStatsManager,packageInfo
//     .uid)));

      long tx = getPackageTxBytesWifi(networkStatsManager, packageInfo.uid)/1024;
tx = tx/1024;
      long rx = getPackageRxBytesWifi(networkStatsManager, packageInfo.uid)/1024;
rx = rx/1024;
      // get data usage from networkStatsManager using wifi
      Log.d("MYLOG",
              packageInfo.loadLabel(pm).toString() + " rx : " + String.valueOf(rx)+"MB");
      Log.d("MYLOG", packageInfo.loadLabel(pm).toString() + " tx : " + String.valueOf(tx)+"MB");
        Log.d(" MYLOG TRAFFIC", packageInfo.loadLabel(pm).toString() +" tx : "+String.valueOf(TrafficStats.getUidTxBytes(packageInfo.uid)));
    }

     Log.d("MYLOG TRAFFIC", " tx : "+String.valueOf(TrafficStats.getTotalTxBytes()));
  }
}
