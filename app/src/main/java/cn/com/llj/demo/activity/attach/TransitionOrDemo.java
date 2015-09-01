package cn.com.llj.demo.activity.attach;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;

import cn.com.llj.demo.R;

/**
 * Slide针对某个view在AppCompatActivity下无效
 * Created by liulj on 15/9/1.
 */
public class TransitionOrDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_or_demo);
        findViews(savedInstanceState);
    }

    public int getLayoutId() {
        return R.layout.transition_or_demo;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void findViews(Bundle savedInstanceState) {
        // Explode explode = new Explode();
        // explode.setDuration(2000);
        // getWindow().setEnterTransition(explode);
//        if (BuildVersionUtil.afterLOLLIPOP()) {
        TransitionSet set = new TransitionSet();

        Slide explode = new Slide(Gravity.TOP);
        explode.setDuration(1000);
        explode.addTarget(R.id.v_blue);
        explode.setStartDelay(100);
        set.addTransition(explode);

        Slide explode2 = new Slide(Gravity.BOTTOM);
        explode2.setDuration(1000);
        explode2.addTarget(R.id.v_blue2);
        explode2.setStartDelay(500);
        set.addTransition(explode2);
        // TransitionSet set2 = new TransitionSet();
        // Transition transition =
        // getWindow().getSharedElementEnterTransition();
        // transition.setStartDelay(100);
        // Fade fade = new Fade();
        // fade.setDuration(2000);
        // set2.addTransition(transition);
        // set2.addTransition(fade);
        // getWindow().setSharedElementEnterTransition(set2);

        getWindow().setReturnTransition(set);
        getWindow().setEnterTransition(set);

        // getWindow().setEnterTransition(explode);
        // getWindow().setEnterTransition(explode);
        // Fade fade = new Fade();
        // fade.setDuration(2000);
        // getWindow().setReturnTransition(fade);
        // Slide fade = new Slide();
        // fade.setDuration(1000);
        // getWindow().setReturnTransition(fade);
        // Fade fade = new Fade();
        // fade.setDuration(2000);
        // getWindow().setSharedElementEnterTransition(fade);
//        }
    }
}
