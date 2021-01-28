package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.listview.R;

import java.util.ArrayList;

/**
 * Created by ouyangshen on 2017/9/24.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpinner();

    }

    // 初始化下拉框
    private void initSpinner() {
        for(int i=0;i<10;i++){
            appList.add("1");
            appList.add("2");
        }
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, appList);
        // 设置数组适配器的布局样式
        //starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        // 从布局文件中获取名叫sp_dialog的下拉框
        ListView listView = findViewById(R.id.listview);

        // 设置下拉框的数组适配器
        listView.setAdapter(starAdapter);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        listView.setOnItemClickListener(new MySelectedListener());
    }

    // 定义下拉列表需要显示的文本数组
    private ArrayList<String> appList = new ArrayList<String>();

    class MySelectedListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, "您选择的是" + appList.get(position), Toast.LENGTH_LONG).show();
        }
    }



}
