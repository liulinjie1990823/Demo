package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class TextViewFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("TextSwitcherDemo");

    }

    @Override
    protected String getPackageName() {
        return "cn.com.llj.demo.activity.textview";
    }
}

