package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class ScrollViewFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("Discrollview");
        mListData.add("Android-ObservableScrollView");
        mListData.add("PullToOverScrollViewDemo");
        mListData.add("StretchScrollViewDemo");
        mListData.add("ReboundScrollViewDemo");
        mListData.add("ParallaxScrollViewDemo");
        mListData.add("PullToLongScrollViewDemo");

        mListData.add("PullToRefreshScrollViewDemo");
    }

    @Override
    protected String getPackageName() {
        return "com.example.demo.activity.anim";
    }
}

