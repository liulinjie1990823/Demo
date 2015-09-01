package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class ProgressbarFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("Android-ViewPagerIndicator");
        mListData.add("JazzyViewPagerDemo");
        mListData.add("JellyViewPager");
        mListData.add("Android-DirectionalViewPager");
        mListData.add("FancyCoverFlow");
        mListData.add("Android Auto Scroll ViewPager");
        mListData.add("PagerSlidingTabStripDemo");
        mListData.add("android_page_curl");
        mListData.add("VerticalViewPagerDemo");
    }

    @Override
    protected String getPackageName() {
        return "com.example.demo.activity.anim";
    }
}

