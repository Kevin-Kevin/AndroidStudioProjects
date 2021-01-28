package com.example.networktest;
import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.SystemClock.sleep;

@TargetApi(Build.VERSION_CODES.M)
public class NetworkStatsHelper {

  NetworkStatsManager networkStatsManager;
  int packageUid;

  public NetworkStatsHelper(NetworkStatsManager networkStatsManager) {
    this.networkStatsManager = networkStatsManager;
  }

  public NetworkStatsHelper(NetworkStatsManager networkStatsManager, int packageUid) {
    this.networkStatsManager = networkStatsManager;
    this.packageUid = packageUid;
  }

  public long getAllRxBytesMobile(Context context) {
    NetworkStats.Bucket bucket;
    try {
      bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
              getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
              0,
              System.currentTimeMillis());
    } catch (RemoteException e) {
      return -1;
    }
    return bucket.getRxBytes();
  }

  public long getAllTxBytesMobile(Context context) {
    NetworkStats.Bucket bucket;
    try {
      bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
              getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
              0,
              System.currentTimeMillis());
    } catch (RemoteException e) {
      return -1;
    }
    return bucket.getTxBytes();
  }

  public long getAllRxBytesWifi() {
    NetworkStats.Bucket bucket;
    try {
      bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
              "",
              0,
              System.currentTimeMillis());
    } catch (RemoteException e) {
      return -1;
    }
    return bucket.getRxBytes();
  }

  public long getAllTxBytesWifi() {
    NetworkStats.Bucket bucket;
    try {
      bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
              "",
              0,
              System.currentTimeMillis());
    } catch (RemoteException e) {
      return -1;
    }
    return bucket.getTxBytes();
  }

  public long getPackageRxBytesMobile(Context context) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_MOBILE,
            getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
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

  public long getPackageTxBytesMobile(Context context) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_MOBILE,
            getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
            0,
            System.currentTimeMillis(),
            packageUid);

    long txBytes = 0L;
    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
    while (networkStats.hasNextBucket()) {
      networkStats.getNextBucket(bucket);
      txBytes += bucket.getTxBytes();
    }
    networkStats.close();
    return txBytes;
  }

  public long getPackageRxBytesWifi() {
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
      long start = bucket.getStartTimeStamp();
      long end = bucket.getEndTimeStamp();
//      start /= 1000;
//      start /= 60;
//      end /= 1000;
//      end /= 60;
//      String s = String.valueOf(start);
//      String e  = String.valueOf(end);
//      String daybegin = String.valueOf(getTimesmorning()/60000);
//      Log.d("百度",s+" "+e + " "+daybegin);
          SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
  Date curDate =  new Date(System.currentTimeMillis());
  String   daybegin   =   formatter.format(curDate);
   Date sD =  new Date(start);
  String   s   =   formatter.format(sD);
     Date eD =  new Date(end);
  String   e   =   formatter.format(eD);
      Log.d("百度 rx","1\n"+"s:"+s+"\n"+"e:"+e + "\n"+"today:"+daybegin);
      rxBytes += bucket.getRxBytes();

    }
     networkStats.getNextBucket(bucket);
      long start = bucket.getStartTimeStamp();
      long end = bucket.getEndTimeStamp();
//      start /= 1000;
//      start /= 60;
//      end /= 1000;
//      end /= 60;
//      String s = String.valueOf(start);
//      String e  = String.valueOf(end);
//      String daybegin = String.valueOf(getTimesmorning()/60000);
//      Log.d("百度",s+" "+e + " "+daybegin);
          SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
  Date curDate =  new Date(System.currentTimeMillis());
  String   daybegin   =   formatter.format(curDate);
   Date sD =  new Date(start);
  String   s   =   formatter.format(sD);
     Date eD =  new Date(end);
  String   e   =   formatter.format(eD);
      Log.d("百度 last bucket rx","1\n"+"s:"+s+"\n"+"e:"+e + "\n"+"today:"+daybegin);
      rxBytes += bucket.getRxBytes();



    networkStats.close();
    return rxBytes;
  }

  public long getPackageTxBytesWifi() {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_WIFI,
            "",
            0,
            System.currentTimeMillis(),
            packageUid);

    long txBytes = 0L;
    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
    while (networkStats.hasNextBucket()) {
      networkStats.getNextBucket(bucket);
            long start = bucket.getStartTimeStamp();
      long end = bucket.getEndTimeStamp();
//      start /= 1000;
//      start /= 60;
//      end /= 1000;
//      end /= 60;
//      String s = String.valueOf(start);
//      String e  = String.valueOf(end);
//      String daybegin = String.valueOf(getTimesmorning()/60000);
    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
  Date curDate =  new Date(System.currentTimeMillis());
  String   daybegin   =   formatter.format(curDate);
   Date sD =  new Date(start);
  String   s   =   formatter.format(sD);
     Date eD =  new Date(end);
  String   e   =   formatter.format(eD);
      Log.d("百度 tx","1\n"+"s:"+s+"\n"+"e:"+e + "\n"+"today:"+daybegin);
      txBytes += bucket.getTxBytes();
    }
          networkStats.getNextBucket(bucket);
            long start = bucket.getStartTimeStamp();
      long end = bucket.getEndTimeStamp();
//      start /= 1000;
//      start /= 60;
//      end /= 1000;
//      end /= 60;
//      String s = String.valueOf(start);
//      String e  = String.valueOf(end);
//      String daybegin = String.valueOf(getTimesmorning()/60000);
    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
  Date curDate =  new Date(System.currentTimeMillis());
  String   daybegin   =   formatter.format(curDate);
   Date sD =  new Date(start);
  String   s   =   formatter.format(sD);
     Date eD =  new Date(end);
  String   e   =   formatter.format(eD);
      Log.d("百度 last bucket tx","1\n"+"s:"+s+"\n"+"e:"+e + "\n"+"today:"+daybegin);
      txBytes += bucket.getTxBytes();


    networkStats.close();
    return txBytes;
  }

  private String getSubscriberId(Context context, int networkType) {
    if (ConnectivityManager.TYPE_MOBILE == networkType) {
      TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return tm.getSubscriberId();
    }
    return "";
  }

    public static long getTimesmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return (cal.getTimeInMillis());
  }
}
