package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class AnimationFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("ActivityAnimationDemo");
        mListData.add("AnimationDemo");
        mListData.add("AnimationDialogDemo");
        mListData.add("LayoutAnimationDemo");
        mListData.add("TransitionDemo");
        mListData.add("TransitionDemo1");
        mListData.add("TransitionWhereDemo");
    }

    @Override
    protected String getPackageName() {
        return "cn.com.llj.demo.activity.animation";
    }


}

