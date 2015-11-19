package cn.com.llj.demo.activity.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by liulj on 15/11/12.
 */
public class MyLinearLyout extends LinearLayout {
    public MyLinearLyout(Context context) {
        super(context);
    }

    public MyLinearLyout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLyout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "MyLinearLyout--dispatchTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "MyLinearLyout--dispatchTouchEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "MyLinearLyout--dispatchTouchEvent:ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "MyLinearLyout--onInterceptTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "MyLinearLyout--onInterceptTouchEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "MyLinearLyout--onInterceptTouchEvent:ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "MyLinearLyout--onTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "MyLinearLyout--onTouchEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "MyLinearLyout--onTouchEvent:ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
