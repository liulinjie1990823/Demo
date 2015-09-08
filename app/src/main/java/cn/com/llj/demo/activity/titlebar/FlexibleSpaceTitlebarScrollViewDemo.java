package cn.com.llj.demo.activity.titlebar;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.library.llj.utils.ScrollUtilLj;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

public class FlexibleSpaceTitlebarScrollViewDemo extends DemoActivity implements ObservableScrollViewCallbacks {
    @Bind(R.id.scroll)
    ObservableScrollView scrollView;
    @Bind(R.id.li_titlebar)
    LinearLayout mToolbarView;
    @Bind(R.id.title)
    TextView mTitleView;
    @Bind(R.id.top_title)
    TextView mTopTitleView;

    private int mFlexibleSpaceHeight;

    @Override
    public int getLayoutId() {
        return R.layout.flexible_space_titlebar_scrollview_demo;
    }


    @Override
    public void findViews(Bundle savedInstanceState) {

        mTitleView.setText(getTitle());
        mTopTitleView.setText(getTitle());

        scrollView.setScrollViewCallbacks(this);

        mFlexibleSpaceHeight = 144;
        int flexibleSpaceAndToolbarHeight = mFlexibleSpaceHeight + 112;

        findViewById(R.id.body).setPadding(0, flexibleSpaceAndToolbarHeight, 0, 0);

        ScrollUtilLj.addOnGlobalLayoutListener(mTitleView, new Runnable() {
            @Override
            public void run() {
                ScrollUtilLj.updateFlexibleSpaceText(scrollView.getCurrentScrollY(), mFlexibleSpaceHeight, mTitleView, mToolbarView);
            }
        });
        ScrollUtilLj.addOnGlobalLayoutListener(mTopTitleView, new Runnable() {
            @Override
            public void run() {
                ScrollUtilLj.updateFlexibleSpaceText(scrollView.getCurrentScrollY(), mFlexibleSpaceHeight, mTopTitleView, mToolbarView);
            }
        });
    }


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ScrollUtilLj.updateFlexibleSpaceText(scrollY, mFlexibleSpaceHeight, mTitleView, mToolbarView);
        ScrollUtilLj.updateFlexibleSpaceText(scrollY, mFlexibleSpaceHeight, mTopTitleView, mToolbarView);

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

}
