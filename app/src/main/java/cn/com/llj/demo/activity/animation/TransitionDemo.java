package cn.com.llj.demo.activity.animation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.library.llj.utils.BuildVersionUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;
import cn.com.llj.demo.activity.attach.TransitionBlackDemo;
import cn.com.llj.demo.activity.attach.TransitionBlueDemo;
import cn.com.llj.demo.activity.attach.TransitionOrDemo;
import cn.com.llj.demo.activity.attach.TransitionPurpleDemo;
import cn.com.llj.demo.activity.attach.TransitionRedDemo;

/**
 * Created by liulj on 15/9/1.
 */
public class TransitionDemo extends DemoActivity {
    @Bind(R.id.scene_root)
    ViewGroup sceneRoot;
    @Bind(R.id.square_red)
    TextView squareRed;
    @Bind(R.id.square_blue)
    TextView squareBlue;
    @Bind(R.id.square_green)
    TextView squareGreen;
    @Bind(R.id.square_orange)
    TextView squareOrange;

    @Override
    public int getLayoutId() {
        return R.layout.transition_demo;
    }

    @OnClick(R.id.square_red)
    public void squareRed() {
        // Explode explode = new Explode();
        // explode.setDuration(2000);
        // getWindow().setExitTransition(explode);
        if (BuildVersionUtil.afterLOLLIPOP()) {
            Slide explode = new Slide();
            explode.setDuration(1000);
            getWindow().setExitTransition(explode);

            // Fade fade = new Fade();
            // fade.setDuration(2000);
            // getWindow().setReenterTransition(fade);
            Slide fade = new Slide();
            fade.setDuration(1000);
            getWindow().setReenterTransition(fade);
        }
        Intent i = new Intent(mBaseFragmentActivity, TransitionRedDemo.class);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mBaseFragmentActivity);
        ActivityCompat.startActivity(mBaseFragmentActivity, i, transitionActivityOptions.toBundle());
        //
        // startActivity(i);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.square_blue)
    public void squareBlue() {
        // 共享元素
        getWindow().setSharedElementReenterTransition(new ChangeBounds());
        Intent i = new Intent(mBaseFragmentActivity, TransitionBlueDemo.class);
        View sharedView = squareBlue;
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mBaseFragmentActivity, sharedView, "square_blue");
        ActivityCompat.startActivity(mBaseFragmentActivity, i, transitionActivityOptions.toBundle());
    }

    @OnClick(R.id.square_green)
    public void squareGreen() {
        if (BuildVersionUtil.afterLOLLIPOP()) {
            TransitionManager.beginDelayedTransition(sceneRoot);
        }
        setViewWidth(squareRed, 500);
        setViewWidth(squareBlue, 500);
        setViewWidth(squareGreen, 500);
        setViewWidth(squareOrange, 500);
    }

    @OnClick(R.id.square_orange)
    public void squareOrange() {
        // 共享元素
        Intent i = new Intent(mBaseFragmentActivity, TransitionOrDemo.class);
        View sharedView = squareOrange;
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mBaseFragmentActivity, sharedView, "square_orange");
        ActivityCompat.startActivity(mBaseFragmentActivity, i, transitionActivityOptions.toBundle());
    }

    @OnClick(R.id.square_purple)
    public void squarePurple() {

        if (BuildVersionUtil.afterLOLLIPOP()) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setExitTransition(explode);
            getWindow().setReenterTransition(explode);
        }
        Intent i = new Intent(mBaseFragmentActivity, TransitionPurpleDemo.class);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mBaseFragmentActivity);
        ActivityCompat.startActivity(mBaseFragmentActivity, i, transitionActivityOptions.toBundle());
    }

    @OnClick(R.id.square_black)
    public void squareBlack() {
        // 共享元素
        Intent i = new Intent(mBaseFragmentActivity, TransitionBlackDemo.class);
        View sharedView = squareOrange;
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mBaseFragmentActivity, sharedView, "square_orange");
        ActivityCompat.startActivity(mBaseFragmentActivity, i, transitionActivityOptions.toBundle());

    }

    private void setViewWidth(View view, int x) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = x;
        view.setLayoutParams(params);
    }
}
