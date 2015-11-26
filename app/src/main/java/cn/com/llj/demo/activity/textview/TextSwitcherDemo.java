package cn.com.llj.demo.activity.textview;

import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/23.
 */
public class TextSwitcherDemo extends DemoActivity {
    @Bind(R.id.switcher)
    TextSwitcher mSwitcher;
    int mCounter;
    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            TextView t = new TextView(TextSwitcherDemo.this);
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(TextSwitcherDemo.this, android.R.style.TextAppearance_Large);
            return t;
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.text_switcher_demo;
    }

    @Override
    public void initViews() {
        // BEGIN_INCLUDE(setup)
        // Set the factory used to create TextViews to switch between.
        mSwitcher.setFactory(mFactory);

        /*
         * Set the in and out animations. Using the fade_in/out animations
         * provided by the framework.
         */
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);

        // Set the initial text without an animation
        mSwitcher.setCurrentText(String.valueOf(mCounter));
    }

    @OnClick(R.id.button)
    public void button() {
        mCounter++;
        // BEGIN_INCLUDE(settext)
        mSwitcher.setText(String.valueOf(mCounter));
        // END_INCLUDE(settext)
    }


}
