package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
  ArrayList<String> hourStingArray = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    for(int i = 0;i<24;i++){
      String index = String.valueOf(i);
      hourStingArray.add(index);
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.text_view_for_adapter,hourStingArray);

    ListView listView = findViewById(R.id.listview);
    listView.setHapticFeedbackEnabled(true);
    listView.setAdapter(adapter);
    listView.setOnScrollListener(this);

  }


  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {

  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                       int totalItemCount) {
    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);

  }
}
