package cn.com.llj.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.common.library.llj.utils.ScrollUtilLj;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;

import java.util.ArrayList;

public abstract class BaseTitlebarControlActivity<S extends Scrollable> extends DemoActivity implements ObservableScrollViewCallbacks {
    private LinearLayout mTitlebar;
    private S mScrollable;

    @Override
    public void getIntentData() {

    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        mTitlebar = (LinearLayout) findViewById(R.id.li_titlebar);
        mScrollable = createScrollable();
        mScrollable.setScrollViewCallbacks(this);
    }

    protected abstract S createScrollable();

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
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if ((scrollState == ScrollState.UP) && ScrollUtilLj.titlebarIsShown(mTitlebar)) {
            // 如果是向上滑且titlebar显示着
            ScrollUtilLj.animTitlebarAdjustScrollView(mTitlebar, (float) -mTitlebar.getHeight(), (View) mScrollable, getScreenHeight());
        } else if ((scrollState == ScrollState.DOWN) && ScrollUtilLj.titlebarIsHidden(mTitlebar)) {
            // 如果是向下滑且titlebar隐藏着
            ScrollUtilLj.animTitlebarAdjustScrollView(mTitlebar, 0, (View) mScrollable, getScreenHeight());
        }
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }

}
