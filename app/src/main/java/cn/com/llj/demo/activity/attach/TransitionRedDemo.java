package cn.com.llj.demo.activity.attach;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.WindowManager;

import com.common.library.llj.utils.BuildVersionUtil;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class TransitionRedDemo extends DemoActivity {
    @Override
    public int getLayoutId() {
        return R.layout.transition_red_demo;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void findViews(Bundle savedInstanceState) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        //设置了这个属性，就相当于状态栏覆盖在activity上面，且状态栏背景透明；如果设置了setFitsSystemWindows后（activity的顶部有padding值）
        params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        // 效果和上面的一样的，但是设置这个属性后即使设置了setFitsSystemWindows无效（activity的顶部也不会有padding值）
        // params.flags = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
        getWindow().setAttributes(params);
        if (BuildVersionUtil.afterLOLLIPOP()) {
//            Transition transition = getWindow().getSharedElementEnterTransition();
//            transition.getTargetIds();
            // Explode explode = new Explode();
            // explode.setDuration(2000);
            // getWindow().setEnterTransition(explode);
            Slide explode = new Slide();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
            // Fade fade = new Fade();
            // fade.setDuration(2000);
            // getWindow().setReturnTransition(fade);
            Slide fade = new Slide();
            fade.setDuration(1000);
            getWindow().setReturnTransition(fade);
        }
    }
}
