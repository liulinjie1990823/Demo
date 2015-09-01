package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class TitlebarFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("FlexibleSpaceTitlebarScrollViewDemo");
        mListData.add("ListViewTitlebarDemo");
        mListData.add("NoBoringActionBarDemo");
        mListData.add("ParallaxToolbarScrollViewDemo");
        mListData.add("ScrollViewTitlebarDemo");
        mListData.add("ViewPagerTabScrollViewFragment");

    }

    @Override
    protected String getPackageName() {
        return "com.example.demo.activity.anim";
    }
}

