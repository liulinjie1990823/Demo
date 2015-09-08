package cn.com.llj.demo.menu;

import java.util.List;

import cn.com.llj.demo.ListMenuFragment;

/**
 * Created by liulj on 15/9/1.
 */
public class ImageViewFragment extends ListMenuFragment {

    @Override
    protected void setListData(List<String> mListData) {
        mListData.add("PhotoImageViewDemo");
        mListData.add("SlideSwitchDemo");
        mListData.add("RippleViewDemo");
        mListData.add("ImageViewZoom");
        mListData.add("SlideSwitch");
        mListData.add("android-autofittextview");
        mListData.add("android-flowtextview");
        mListData.add("Shimmer-android");
        mListData.add("Titanic");
        mListData.add("supertooltips");
        mListData.add("PhotoImageDemo");
        mListData.add("android-gif-drawable");
        mListData.add("RoundedImageViewDemo");
        mListData.add("SelectableRoundedImageViewDemo");
        mListData.add("KenBurnsView");
        mListData.add("CardViewDemo");

        mListData.add("android-flip");
        mListData.add("FlipImageViewDemo");
        mListData.add("UndoBarDemo");
        mListData.add("SwipeCardDemo");
        mListData.add("FloatingButtonDemo");
    }

    @Override
    protected String getPackageName() {
        return "cn.com.llj.demo.activity.imageview";
    }

}
