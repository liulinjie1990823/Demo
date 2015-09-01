package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class MenuFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        // menu
        mListData.add("DrawerLeftDemo");
        mListData.add("OverlaybottomDemo");
        mListData.add("OverlayLeftDemo");

        mListData.add("ArtMenuDemo");
        mListData.add("FoldingNavigationDrawer");
        mListData.add("ResideMenuDemo");
        mListData.add("SlidingPaneLayoutDemo");
        mListData.add("SlidingUpPanelLayoutDemo");

        mListData.add("DragLayoutDemo");
        mListData.add("DrawerLayoutDemo");
        mListData.add("Side-Menu.Android");
    }

    @Override
    protected String getPackageName() {
        return null;
    }
}
