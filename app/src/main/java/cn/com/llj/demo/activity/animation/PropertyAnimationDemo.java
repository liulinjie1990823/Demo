package cn.com.llj.demo.activity.animation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/18.
 */
public class PropertyAnimationDemo extends DemoActivity {
    @Bind(R.id.btn_argb)
    Button mButton;

    @Override
    public int getLayoutId() {
        return R.layout.property_animation_demo;
    }

    //背景颜色的动画渐变
    @OnClick(R.id.btn_argb)
    public void animatiorArgb() {
        int color1 = Color.parseColor("#ff8080");
        int color2 = Color.parseColor("#8080ff");
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mButton, "backgroundColor", color1, color2);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.setDuration(3000);
        //设置动画的设置目标
        objectAnimator.setTarget(mButton);
        objectAnimator.start();
    }
}
