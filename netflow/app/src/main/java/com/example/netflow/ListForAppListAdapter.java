package com.example.netflow;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class ListForAppListAdapter {
  Context context;

  public ListForAppListAdapter(Context context) {
    this.context = context;
  }

  public class listChildInformation {
    PackageInfo packageInfo;
    long rx;
    long tx;
    long preTx;
    long preRx;

  }


  /* --------------------------------------
   * 获取系统 package 列表
   * flag:
   *      0 --- user and system packages
   *      1 --- user packages
   *      2 --- system packages
   * --------------------------------------
   */
  public ArrayList<listChildInformation> getPackageList(int flag) {

    PackageManager packageManager = context.getPackageManager();
    List<PackageInfo> installedPackages =
            packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
    ArrayList<listChildInformation> allInstalledPackages = new ArrayList<>();
    ArrayList<listChildInformation> userPackages = new ArrayList<>();
    ArrayList<listChildInformation> systemPackages = new ArrayList<>();
    NetworkStatsGetHelper networkStatsGetHelper = new NetworkStatsGetHelper(context);
    switch (flag) {
      case 0:
        //所有应用
        for (PackageInfo info : installedPackages) {
          listChildInformation listchild = new listChildInformation();
          listchild.packageInfo = info;
          listchild.rx = networkStatsGetHelper.getPackageRxBytesWifi(info.applicationInfo.uid);
          listchild.tx = networkStatsGetHelper.getPackageTxBytesWifi(info.applicationInfo.uid);
          listchild.preRx = 0;
          listchild.preRx = 0;
          allInstalledPackages.add(listchild);

        }
        return allInstalledPackages;
      case 1:
        //用户应用
        for (PackageInfo info : installedPackages) {
          if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

            listChildInformation listchild = new listChildInformation();
            listchild.packageInfo = info;
            listchild.rx = networkStatsGetHelper.getPackageRxBytesWifi(info.applicationInfo.uid);
            listchild.tx = networkStatsGetHelper.getPackageTxBytesWifi(info.applicationInfo.uid);
            userPackages.add(listchild);
          }
        }
        return userPackages;
      case 2:
        for (PackageInfo info : installedPackages) {
          if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            //系统应用
            listChildInformation listchild = new listChildInformation();
            listchild.packageInfo = info;
            listchild.rx = networkStatsGetHelper.getPackageRxBytesWifi(info.applicationInfo.uid);
            listchild.tx = networkStatsGetHelper.getPackageTxBytesWifi(info.applicationInfo.uid);
            systemPackages.add(listchild);
          }
        }
        return systemPackages;
    }
    return userPackages;
  }


}


