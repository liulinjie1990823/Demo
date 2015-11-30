package cn.com.llj.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.common.library.llj.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import cn.com.llj.demo.menu.AnimationFragment;
import cn.com.llj.demo.menu.ButtonFragment;
import cn.com.llj.demo.menu.GridViewFragment;
import cn.com.llj.demo.menu.ImageViewFragment;
import cn.com.llj.demo.menu.ListViewFragment;
import cn.com.llj.demo.menu.MenuFragment;
import cn.com.llj.demo.menu.NetFragment;
import cn.com.llj.demo.menu.OtherFragment;
import cn.com.llj.demo.menu.ProgressbarFragment;
import cn.com.llj.demo.menu.ScrollViewFragment;
import cn.com.llj.demo.menu.TextViewFragment;
import cn.com.llj.demo.menu.TimeViewFragment;
import cn.com.llj.demo.menu.TitlebarFragment;
import cn.com.llj.demo.menu.ViewPagerFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class HomeActivity extends DemoActivity {
    @Bind(R.id.left_drawer)
    ListView mLeftDrawer;// 默认当前页为-1，
    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;
    private List<String> mMenuData = new ArrayList<String>();
    private int mShowItem = 0;// 默认当前页为-1，加载第一个fragment后mCurrentItem就为1
    private int mHideItem = 0;
    @Bind(R.id.li_menu)
    LinearLayout mMenuLi;

    @Override
    public int getLayoutId() {
        return R.layout.home_activity_layout;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {

        mMenuLi.getLayoutParams().width = BaseApplication.DISPLAY_WIDTH * 4 / 5;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        // mActionBar.setLogo(R.drawable.ic_launcher);
        // mActionBar.setIcon(R.drawable.ic_launcher);
        mActionBar.setTitle("Demo");
//        mActionBar.setSubtitle("liulinjie1990823");
        //
        mFragmentManager = getSupportFragmentManager();
        // 从savedInstanceState获取到保存的mCurrentItem
        if (savedInstanceState != null) {
            mHideItem = savedInstanceState.getInt("mHideItem", 0);
            mShowItem = savedInstanceState.getInt("mShowItem", 0);
        }
        // 第一次进入默认显示第1页
        selectItem(mHideItem, mShowItem, true);
    }

    @Override
    public void addListeners() {
        // 设置mDrawerLayout的监听器
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View arg0) {
                super.onDrawerOpened(arg0);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View arg0) {
                super.onDrawerClosed(arg0);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @OnItemClick(R.id.left_drawer)
    public void listItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        mLeftDrawer.setItemChecked(arg2, true);
        mDrawerLayout.closeDrawer(mMenuLi);
        if (mShowItem != arg2)
            selectItem(mShowItem, arg2, false);
    }

    /**
     * 有可能被意外销毁之前调用，主动销毁不调用 1.按home回到主页
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mHideItem", mHideItem);
        outState.putInt("mShowItem", mShowItem);
    }

    @Override
    public void initViews() {
        mMenuData.add("AnimationFragment");
        mMenuData.add("ButtonFragment");
        mMenuData.add("GridViewFragment");
        mMenuData.add("ImageViewFragment");
        mMenuData.add("ListViewFragment");
        mMenuData.add("MenuFragment");
        mMenuData.add("NetFragment");
        mMenuData.add("OtherFragment");
        mMenuData.add("ProgressbarFragment");
        mMenuData.add("ScrollViewFragment");
        mMenuData.add("TextViewFragment");
        mMenuData.add("TimeViewFragment");
        mMenuData.add("TitlebarFragment");
        mMenuData.add("ViewPagerFragment");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMenuData);
        mLeftDrawer.setAdapter(adapter2);
    }

    /**
     * 隐藏当前显示的fragment,显示将要显示的fragment
     */
    public void selectItem(int hideItem, int showItem, boolean isOnCreat) {
        // 获得将要显示页的tag
        String currentTag = "fragment" + hideItem;
        // 隐藏当前的的fragment
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 如果被杀后再进来，全部的fragment都会被呈现显示状态，所以都隐藏一边
        if (isOnCreat && mFragmentManager.getFragments() != null) {
            for (Fragment fragment : mFragmentManager.getFragments()) {
                transaction.hide(fragment);
            }
        }
        Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTag);
        if (lastFragment != null) {
            // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.hide(lastFragment);
        }
        // 获得将要显示页的tag
        String toTag = "fragment" + showItem;
        // find要显示的Fragment
        Fragment currentFragment = mFragmentManager.findFragmentByTag(toTag);
        if (currentFragment != null) {
            // 已经存在则显示
            transaction.show(currentFragment);
        } else {
            // 不存在则添加新的fragment
            currentFragment = makeFragment(showItem);
            if (currentFragment != null) {
                transaction.add(R.id.rl_fragment_contain, currentFragment, toTag);
            }
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // 保存当前显示fragment的item
        mHideItem = hideItem;
        mShowItem = showItem;
        transaction.commit();
    }

    /**
     * 根据curItem实例化不同的fragment
     *
     * @param curItem 当前选择项1,2,3,4
     * @return fragment
     */
    private Fragment makeFragment(int curItem) {
        Fragment fragment = null;
        switch (curItem) {
            case 0:
                fragment = new AnimationFragment();
                break;
            case 1:
                fragment = new ButtonFragment();
                break;
            case 2:
                fragment = new GridViewFragment();
                break;
            case 3:
                fragment = new ImageViewFragment();
                break;
            case 4:
                fragment = new ListViewFragment();
                break;
            case 5:
                fragment = new MenuFragment();
                break;
            case 6:
                fragment = new NetFragment();
                break;
            case 7:
                fragment = new OtherFragment();
                break;
            case 8:
                fragment = new ProgressbarFragment();
                break;
            case 9:
                fragment = new ScrollViewFragment();
                break;
            case 10:
                fragment = new TextViewFragment();
                break;
            case 11:
                fragment = new TimeViewFragment();
                break;
            case 12:
                fragment = new TitlebarFragment();
                break;
            case 13:
                fragment = new ViewPagerFragment();
                break;
        }
        return fragment;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
