package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class OtherFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("BoltsDemo");
    }

    @Override
    protected String getPackageName() {
        return "cn.com.llj.demo.activity.other";
    }
}

