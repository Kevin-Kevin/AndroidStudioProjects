package com.kevin.BatteryChangedRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BatteryListAdapter extends BaseAdapter {

  Context context;
ArrayList<BatteryChangedTimeInformation> list;


  public BatteryListAdapter(Context context, ArrayList<BatteryChangedTimeInformation> list){
    this.context = context;
    this.list = list;
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;

    if(convertView == null){
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(context).inflate(R.layout.adapter_child_view_layout, null);
      viewHolder.textView = convertView.findViewById(R.id.textView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder)convertView.getTag();
    }


    viewHolder.textViewString = String.valueOf(list.get(position).currentTime)+"  "+list.get(position).batteryRemained+"%";
    viewHolder.textView.setText(viewHolder.textViewString);


    return convertView;
  }

  private class ViewHolder{
    TextView textView;
    String textViewString;
  }
}
