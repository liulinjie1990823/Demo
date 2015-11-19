package cn.com.llj.demo.activity.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by liulj on 15/11/12.
 */
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "MyButton--dispatchTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "MyButton--dispatchTouchEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "MyButton--dispatchTouchEvent:ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "MyButton--onTouchEvent:ACTION_DOWN");
                Log.i("llj", "-----------------------------------------");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "MyButton--onTouchEvent:ACTION_MOVE");
                Log.i("llj", "-----------------------------------------");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "MyButton--onTouchEvent:ACTION_UP");
                Log.i("llj", "-----------------------------------------");
                break;
        }
        return super.onTouchEvent(event);
    }
}
