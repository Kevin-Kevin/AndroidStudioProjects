package com.kevin.toucheventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyTextView extends AppCompatTextView {
  float lastX = 0;
  float lastY = 0;
  float translationX = 0;
  float translationY = 0;

  public MyTextView(Context context) {
    super(context);
  }

  public MyTextView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // MotionEvent 变量值测试
    int actionMasked = event.getActionMasked();
    int pointerCount = event.getPointerCount();
    // int pointerID = event.getPointerId(pointerCount);

    int action = event.getAction();
    int actionButton = event.getActionButton();
    int actionIndex = event.getActionIndex();

    float axisXValue = event.getAxisValue(event.AXIS_X);
    float axisYValue = event.getAxisValue(event.AXIS_Y);
    float rawX = event.getRawX();
    float rawY = event.getRawY();
    float x = event.getX();
    float y = event.getY();
    Log.d("touch",
            " actionMasked=" + String.valueOf(actionMasked)
                    + ", pointer count=" + String.valueOf(pointerCount)
                    //      + " pointer id=" + String.valueOf(pointerID)
                    + ", action=" + String.valueOf(action)
                    + ", action button=" + String.valueOf(actionButton)
                    + ", action index=" + String.valueOf(actionIndex)
                    + "\nraw x=" + String.valueOf(rawX)
                    + ", raw y=" + String.valueOf(rawY)
                    + ", x=" + String.valueOf(x)
                    + ", y=" + String.valueOf(y)
                    + "\naxis y value=" + String.valueOf(axisYValue)
                    + ", axis y value=" + String.valueOf(axisYValue)
    );


    switch (action) {
      case MotionEvent.ACTION_DOWN:
        lastX = event.getRawX();
        lastY = event.getRawY();
        Log.d("touch", "Action down, index = " + String.valueOf(event.getActionIndex()));
        Log.d("touch",
                "x = " + String.valueOf(event.getX()) + " , y = " + String.valueOf(event.getY()));
        break;
      case MotionEvent.ACTION_MOVE:
        Log.d("touch translation",
                String.valueOf(translationX) + " " + String.valueOf(translationY));
        translationX = translationX + event.getRawX() - lastX;
        translationY = translationY + event.getRawY() - lastY;
        
        lastX = event.getRawX();
        lastY = event.getRawY();
        this.setTranslationX(translationX);
        this.setTranslationY(translationY);


        Log.d("touch", "Action move, index = " + String.valueOf(event.getActionIndex()));
        Log.d("touch",
                "x = " + String.valueOf(event.getX()) + " , y = " + String.valueOf(event.getY()));
        break;
      case MotionEvent.ACTION_UP:
        Log.d("touch", "Action up, index = " + String.valueOf(event.getActionIndex()));
        Log.d("touch",
                "x = " + String.valueOf(event.getX()) + " , y = " + String.valueOf(event.getY()));
        break;
    }
//    this.setX(event.getRawX());
//    this.setY(event.getRawY());
//    this.invalidate();

    Log.d("touch", "view x=" + String.valueOf(this.getX()) + " view y=" + String.valueOf(this.getY()));


    return true;

  }
}
