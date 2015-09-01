package cn.com.llj.demo.activity.attach;

import android.annotation.TargetApi;
import android.os.Build;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class TransitionBlueDemo extends DemoActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getIntentData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.transition_blue_demo;
    }
}
