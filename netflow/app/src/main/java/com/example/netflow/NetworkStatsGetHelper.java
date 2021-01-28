package com.example.netflow;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.NETWORK_STATS_SERVICE;

public class NetworkStatsGetHelper {
  Context context;
  NetworkStatsManager networkStatsManager;


  public NetworkStatsGetHelper(Context context) {
    this.context = context;
    networkStatsManager = (NetworkStatsManager) context.getSystemService(NETWORK_STATS_SERVICE);
  }
//
//  /**
//   * 获取指定应用 wifi 发送的当天总流量
//   *
//   * @param packageUid 应用的uid
//   * @return
//   */
//  public long getPackageTxDayBytesWifi(int packageUid) {
//
//    NetworkStats networkStats;
//    NetworkStatsManager networkStatsManager =
//            (NetworkStatsManager) context.getSystemService(NETWORK_STATS_SERVICE);
//    networkStats = networkStatsManager.queryDetailsForUid(
//            ConnectivityManager.TYPE_WIFI,
//            "",
//            0,
//            System.currentTimeMillis(),
//            packageUid);
//
//    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
//    networkStats.getNextBucket(bucket);
//    if (bucket.getTxBytes() != 0) {
//      String tx = String.valueOf(bucket.getTxBytes());
//      String appName = "";
//      PackageManager packageManager = context.getPackageManager();
//      List<PackageInfo> packageInfos =
//              packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
//      for (PackageInfo info : packageInfos) {
//        if (info.applicationInfo.uid == packageUid) {
//          appName = info.applicationInfo.loadLabel(packageManager).toString();
//        }
//      }
//
//      Log.i("bucket tx", appName + " : " + tx);
//
//    }
//    return bucket.getTxBytes();
//  }
//
//  /**
//   * 获取指定应用 wifi 发送的当天总流量
//   *
//   * @param packageUid 应用的uid
//   * @return
//   */
//
//  public long getPackageRxDayBytesWifi(int packageUid) {
//
//    NetworkStats networkStats;
//    NetworkStatsManager networkStatsManager =
//            (NetworkStatsManager) context.getSystemService(NETWORK_STATS_SERVICE);
////TelephonyManager tm = (TelephonyManager)getApplicationContext().getSystemService(Context
//// .TELEPHONY_SERVICE);
//    networkStats = networkStatsManager.queryDetailsForUid(
//            ConnectivityManager.TYPE_WIFI,
//            "",
//            0,
//            System.currentTimeMillis(),
//            packageUid);
//
//    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
//    networkStats.getNextBucket(bucket);
//    if (bucket.getRxBytes() != 0) {
//      String rx = String.valueOf(bucket.getRxBytes());
//      String appName = "";
//      PackageManager packageManager = context.getPackageManager();
//      List<PackageInfo> packageInfos =
//              packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
//      for (PackageInfo info : packageInfos) {
//        if (info.applicationInfo.uid == packageUid) {
//          appName = info.applicationInfo.loadLabel(packageManager).toString();
//        }
//      }
//
//      Log.i("bucket rx", appName + " : " + rx);
//
//    }
//    return bucket.getRxBytes();
//  }


  /**
   * 获取当天的零点时间
   *
   * @return
   */
  public static long getTimesmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return (cal.getTimeInMillis());
  }


  public long getPackageRxBytesWifi(int packageUid) {
    context.getPackageManager().getPackagesForUid(packageUid);
    PackageManager packageManager = context.getPackageManager();


    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_WIFI,
            "",
            getTimesmorning(),
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
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
      Date curDate = new Date(System.currentTimeMillis());
      String daybegin = formatter.format(curDate);
      Date sD = new Date(start);
      String s = formatter.format(sD);
      Date eD = new Date(end);
      String e = formatter.format(eD);
    //  Log.d("百度 rx", "1\n" + "s:" + s + "\n" + "e:" + e + "\n" + "today:" + daybegin);
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
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String daybegin = formatter.format(curDate);
    Date sD = new Date(start);
    String s = formatter.format(sD);
    Date eD = new Date(end);
    String e = formatter.format(eD);
   // Log.d("百度 last bucket rx", "1\n" + "s:" + s + "\n" + "e:" + e + "\n" + "today:" + daybegin);
    rxBytes += bucket.getRxBytes();


    networkStats.close();
    return rxBytes;
  }

  public long getPackageTxBytesWifi(int packageUid) {
    NetworkStats networkStats = null;
    networkStats = networkStatsManager.queryDetailsForUid(
            ConnectivityManager.TYPE_WIFI,
            "",
            getTimesmorning(),
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
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
      Date curDate = new Date(System.currentTimeMillis());
      String daybegin = formatter.format(curDate);
      Date sD = new Date(start);
      String s = formatter.format(sD);
      Date eD = new Date(end);
      String e = formatter.format(eD);
    //  Log.d("百度 tx", "1\n" + "s:" + s + "\n" + "e:" + e + "\n" + "today:" + daybegin);
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
    SimpleDateFormat formatter = new
            SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String daybegin = formatter.format(curDate);
    Date sD = new Date(start);
    String s = formatter.format(sD);
    Date eD = new Date(end);
    String e = formatter.format(eD);
   // Log.d("百度 last bucket tx", "1\n" + "s:" + s + "\n" + "e:" + e + "\n" + "today:" + daybegin);
    txBytes += bucket.getTxBytes();


    networkStats.close();
    return txBytes;
  }


}
