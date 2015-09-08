package cn.com.llj.demo.activity.titlebar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.common.library.llj.utils.AnimUtilLj;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * @author liulj
 */
public class ParallaxToolbarScrollViewDemo extends DemoActivity implements ObservableScrollViewCallbacks {
    private View mImageView;
    private LinearLayout mTitlebar;
    private ObservableScrollView mScrollView;

    @Override
    public int getLayoutId() {
        return R.layout.parallax_titlebar_scrollview_demo;
    }


    @Override
    public void findViews(Bundle savedInstanceState) {
        mImageView = findViewById(R.id.image);
        mTitlebar = (LinearLayout) findViewById(R.id.li_titlebar);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        AnimUtilLj.setTitlebarBackgroundColor(mTitlebar, 360, scrollY, Color.parseColor("#424234"));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void requestOnCreate() {

    }

    @Override
    public void onDownMotionEvent() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }
}
