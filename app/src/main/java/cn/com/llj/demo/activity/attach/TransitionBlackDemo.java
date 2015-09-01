package cn.com.llj.demo.activity.attach;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class TransitionBlackDemo extends DemoActivity {
    @Bind(R.id.iv_center)
    ImageView mCenterIv;
    @Bind(R.id.btn_start)
    Button mStartBtn;

    @Override
    public int getLayoutId() {
        return R.layout.tansition_black_demo;
    }

    @OnClick(R.id.btn_start)
    public void mStartBtn() {
        if (!mCenterIv.isShown()) {
            revealImageCircular();
        } else {
            hideImageCircular();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideImageCircular() {

        Animator anim = ViewAnimationUtils.createCircularReveal(mCenterIv, mCenterIv.getWidth() / 2, mCenterIv.getHeight() / 2, mCenterIv.getWidth() / 2, 0);
        anim.setDuration(1000);
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCenterIv.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealImageCircular() {

        Animator anim = ViewAnimationUtils.createCircularReveal(mCenterIv, 0, 0, 0, mCenterIv.getWidth() );

        anim.setDuration(1000);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCenterIv.setVisibility(View.VISIBLE);
            }
        });

        anim.start();
    }
}
