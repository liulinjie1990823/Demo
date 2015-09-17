package cn.com.llj.demo.activity.other;

import android.os.Bundle;
import android.view.View;

import com.fivehundredpx.android.blur.BlurringView;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/9.
 */
public class BlurringViewDemo extends DemoActivity {
    private BlurringView mBlurringView;

    @Override
    public int getLayoutId() {
        return R.layout.blurring_view_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        mBlurringView = (BlurringView) findViewById(R.id.blurring_view);
        View blurredView = findViewById(R.id.blurred_view);

        // Give the blurring view a reference to the blurred view.
        mBlurringView.setBlurredView(blurredView);
    }
}
