package cn.com.llj.demo.activity.titlebar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.common.library.llj.utils.AnimUtilLj;
import com.common.library.llj.views.PagerSlidingTabStrip;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * @author liulj
 */
public class ViewPagerTitlebarDemo extends DemoActivity implements ObservableScrollViewCallbacks {
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private int mScrollY;
    private LinearLayout mHeaderView, mTitlebar;

    @Override
    public int getLayoutId() {
        return R.layout.viewpager_titlebar;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        super.findViews(savedInstanceState);
        mHeaderView = (LinearLayout) findViewById(R.id.header);
        mTitlebar = (LinearLayout) findViewById(R.id.li_titlebar);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setUnderlineHeight(10);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {"Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid", "Top New Free", "Trending"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new ViewPagerTabScrollViewFragment();
            if (0 <= mScrollY) {
                Bundle args = new Bundle();
                args.putInt(ViewPagerTabScrollViewFragment.ARG_SCROLL_Y, mScrollY);
                f.setArguments(args);
            }
            return f;
        }

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            AnimUtilLj.moveTitlebar(mHeaderView, scrollY, mTitlebar.getHeight());
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
