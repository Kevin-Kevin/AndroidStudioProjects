package com.example.netflow;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


//app 列表适配器
public class AppListAdapter extends BaseAdapter {

  private Context context; // 声明一个上下文对象
  private ArrayList<ListForAppListAdapter.listChildInformation> listChildInformations; // 声明一个行星信息队列

  // app 适配器的构造函数，传入上下文与行星队列
  public AppListAdapter(Context context,
                        ArrayList<ListForAppListAdapter.listChildInformation> packageInfo_list) {
    this.listChildInformations = packageInfo_list;
    this.context = context;
  }


  // 获取列表项的个数
  public int getCount() {
    return listChildInformations.size();
  }

  // 获取列表项的数据
  public Object getItem(int arg0) {
    return listChildInformations.get(arg0);
  }

  // 获取列表项的编号
  public long getItemId(int arg0) {
    return arg0;
  }

  // 获取指定位置的列表项视图
  public View getView(final int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) { // 转换视图为空
      holder = new ViewHolder(); // 创建一个新的视图持有者

      // 根据布局文件item_list.xml生成转换视图对象
      convertView = LayoutInflater.from(context).inflate(R.layout.app_show_view, null);
      holder.iv_icon = convertView.findViewById(R.id.iv_icon);
      holder.tv_appName = convertView.findViewById(R.id.tv_name);
      holder.tv_packageName = convertView.findViewById(R.id.tv_desc);
      holder.rx = convertView.findViewById(R.id.rx);
      holder.tx = convertView.findViewById(R.id.tx);

      // 将视图持有者保存到转换视图当中
      convertView.setTag(holder);
    } else { // 转换视图非空
      // 从转换视图中获取之前保存的视图持有者
      holder = (ViewHolder) convertView.getTag();
    }
    PackageInfo packageInfo = listChildInformations.get(position).packageInfo;
    PackageManager packageManager = context.getPackageManager();

    holder.iv_icon.setImageDrawable(packageInfo.applicationInfo.loadIcon(packageManager));//应用图标

    holder.tv_appName.setText(packageInfo.applicationInfo.loadLabel(packageManager)); //
    // 显示应用名
    holder.tv_packageName.setText(packageInfo.packageName); // 显示包名

    long rx = listChildInformations.get(position).rx;
    long tx = listChildInformations.get(position).tx;
    String listChildRx;



    if (0 == rx >> 10) {
      listChildRx = "↓ : "+String.valueOf(rx) + "B";

    } else if (0 == rx >> 20) {
      rx >>= 10;
      listChildRx = "↓ : "+String.valueOf(rx) + "KB";

    } else if (0 == rx >> 30) {
      rx >>= 20;
      listChildRx = "↓ : "+String.valueOf(rx) + "MB";

    } else {
      rx >>= 30;
      listChildRx = "↓ : "+String.valueOf(rx) + "GB";

    }


    String listChildTx;

    if (0 == tx >> 10) {
      listChildTx = "↑ : "+String.valueOf(tx) + "B";

    } else if (0 == tx >> 20) {
      tx >>= 10;
      listChildTx = "↑ : "+String.valueOf(tx) + "KB";

    } else if (0 == tx >> 30) {
      tx >>= 20;
      listChildTx = "↑ : "+String.valueOf(tx) + "MB";

    } else {
      tx >>= 30;
      listChildTx = "↑ : "+String.valueOf(tx) + "GB";

    }



    holder.rx.setText(listChildRx);
    holder.tx.setText(listChildTx);
    return convertView;
  }

  // 定义一个视图持有者，以便重用列表项的视图资源
  public final class ViewHolder {
    public ImageView iv_icon; // 声明行星图片的图像视图对象
    public TextView tv_appName; // 声明行星名称的文本视图对象
    public TextView tv_packageName; // 声明行星描述的文本视图对象
    public TextView rx;
    public TextView tx;

  }

}