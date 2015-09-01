package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class TimeViewFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("TimesSquareDemo");
        mListData.add("android-calendar-card");
        mListData.add("AndroidWheel");
        mListData.add("DateTimePicker");
        mListData.add("Android Week View");
        mListData.add("CalendarListviewDemo");
    }

    @Override
    protected String getPackageName() {
        return "com.example.demo.activity.anim";
    }
}

