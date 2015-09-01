package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class GridViewFragment extends ListMenuFragment {

    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("JazzyGridViewDemo");
        mListData.add("StaggeredGridViewDemo");
        mListData.add("StickyGridHeadersDemo");
        mListData.add("PagedDragDropGrid");
        mListData.add("DraggableGridView");
        mListData.add("Android-DraggableGridViewPager");
        mListData.add("AsymmetricGridView");
    }

    @Override
    protected String getPackageName() {
        return "com.example.demo.activity.anim";
    }

}
