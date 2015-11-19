package cn.com.llj.demo.activity.other;

import android.util.Log;
import android.view.MotionEvent;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/12.
 */
public class TouchEventDemo extends DemoActivity {
    @Override
    public int getLayoutId() {
        return R.layout.touch_event_demo;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "TouchEventDemo--dispatchTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "TouchEventDemo--dispatchTouchEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "TouchEventDemo--dispatchTouchEvent:ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("llj", "TouchEventDemo--onTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("llj", "TouchEventDemo--onTouchEvent:ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("llj", "TouchEventDemo--onTouchEvent:ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
