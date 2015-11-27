package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class OtherFragment extends ListMenuFragment {
    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("Camera2VideoDemo");
        mListData.add("Camera2ImageDemo");
        mListData.add("MediaDecodeDemo");
        mListData.add("MediaEffectsDemo");
        mListData.add("MediaRecorderDemo");
        mListData.add("PermissionDemo");
        mListData.add("CursorLoaderDemo");
        mListData.add("RenderScriptDemo");
        mListData.add("ActivityManagerDemo");
        mListData.add("NotificationDemo");
        mListData.add("UsageStatsManagerDemo");
        mListData.add("SensorManagerDemo");
        mListData.add("BoltsDemo");
        mListData.add("FlowLayoutDemo");
        mListData.add("MaterialDesignDemo");
        mListData.add("BlurringViewDemo");
        mListData.add("WaveViewDemo");
        mListData.add("TouchEventDemo");
    }

    @Override
    protected String getPackageName() {
        return "cn.com.llj.demo.activity.other";
    }
}

