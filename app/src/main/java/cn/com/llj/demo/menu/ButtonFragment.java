package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class ButtonFragment extends ListMenuFragment {

    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("RippleButtonDemo");
        mListData.add("CircularProgressButtonDemo");
        mListData.add("FloatingButtonDemo");
        mListData.add("ProcessButtonDemo");
        mListData.add("RadioButtonDemo");
    }

    @Override
    protected String getPackageName() {
        return "cn.com.llj.demo.activity.button";
    }

}