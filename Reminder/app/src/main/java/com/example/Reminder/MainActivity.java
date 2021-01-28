package com.example.Reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.view.ViewGroup.LayoutParams.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ScrollView scrollView = findViewById(R.id.scrollView);
        FloatingActionButton button = findViewById(R.id.floatingActionButton2);
        Button goToAllPageButton = findViewById(R.id.GoToAllPage);

        // 给 button 设置点击监听器
        button.setOnClickListener(new buttonOnClickListener());
        // goToAllPageButton 设置监听器
        goToAllPageButton.setOnClickListener(new goToAllPageButtonListener());

        final SharedPreferences reminder = getSharedPreferences("reminder", MODE_PRIVATE);
        SharedPreferences.Editor editor = reminder.edit();
        


    }
public int n = 0;
    // 悬浮按钮点击事件
    class buttonOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            ScrollView scrollView = findViewById(R.id.scrollView);
            LinearLayout linearLayout = findViewById(R.id.scrollViewLinearLayout);
            // 创建编辑文本框
            EditText editText = new EditText(getApplicationContext());
            // 注册文本框监听事件
            editText.addTextChangedListener(new EditTextWatcher());

            Button button = new Button(getApplicationContext());
            LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
            button.setText("○");
            int width = dip2px(getApplicationContext(),50L);
            button.setLayoutParams(new ViewGroup.LayoutParams(width, WRAP_CONTENT));

            editText.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

            linearLayout1.addView(button);
            linearLayout1.addView(editText);

            linearLayout1.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            linearLayout.addView(linearLayout1);

            Toast.makeText(MainActivity.this, "添加提醒事项", Toast.LENGTH_SHORT).show();
            n++;
            System.out.println("add view "+n);
        }
    }
    // edit text 监听事件
    class EditTextWatcher implements TextWatcher{

        public void beforeTextChanged(CharSequence s,int start, int count,int after){}
        public void onTextChanged(CharSequence s,int start, int before, int count){
            String output = s.toString();
            Log.i("edit text changed: ", output);

        }
        public void afterTextChanged(Editable s){}
    }
    // goToAllPageButton 点击事件
    class goToAllPageButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, AllActivity.class);
            startActivity(intent);
        }
    }

    // 转换 dp to px
    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    // 转换 px to dp
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
}
