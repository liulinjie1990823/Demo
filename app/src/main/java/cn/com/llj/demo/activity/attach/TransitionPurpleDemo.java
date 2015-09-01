package cn.com.llj.demo.activity.attach;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Explode;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class TransitionPurpleDemo extends DemoActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getIntentData() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        getWindow().setEnterTransition(explode);
        getWindow().setReturnTransition(explode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.transition_purple_demo;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
