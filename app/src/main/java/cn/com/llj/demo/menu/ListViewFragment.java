package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class ListViewFragment extends ListMenuFragment {

    @Override
    protected void setListData(List<String> mListData) {
        // 列表
        mListData.add("android-Ultra-Pull-to-Refresh");
        mListData.add("DragSortListViewDemo");
        mListData.add("SwipeListViewDemo");
        mListData.add("StickyListHeaders");
        mListData.add("PinnedSectionListViewDemo");
        mListData.add("IndexableListViewDemo");
        mListData.add("CustomFastScrollView");
        mListData.add("ScrollBarPanelDemo");
        mListData.add("SlideExpandableListViewDemo");

        mListData.add("JazzyListViewDemo");

        mListData.add("ListViewAnimations");
        mListData.add("TwoWayViewDemo");
        mListData.add("HorizontalVariableListView");
        mListData.add("LinearListViewDemo");
        mListData.add("MultiChoiceAdapter");
        mListData.add("ListBuddies");
        mListData.add("SwipeMenuListViewDemo");
        mListData.add("PullToZoomViewDemo");
        mListData.add("PullSeparateListView");
        mListData.add("Pull-to-Refresh.Rentals-Android");

        mListData.add("PullToRefreshListViewDemo");
        mListData.add("StickyListviewDemo");
    }

    @Override
    protected String getPackageName() {
        return "com.example.demo.activity.anim";
    }

}
