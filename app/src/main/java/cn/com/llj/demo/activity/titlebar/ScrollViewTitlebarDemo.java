package cn.com.llj.demo.activity.titlebar;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import cn.com.llj.demo.BaseTitlebarControlActivity;
import cn.com.llj.demo.R;

/**
 * @author liulj
 */
public class ScrollViewTitlebarDemo extends BaseTitlebarControlActivity<ObservableScrollView> {

    @Override
    public int getLayoutId() {
        return R.layout.scrollview_titlebar_demo;
    }

    @Override
    protected ObservableScrollView createScrollable() {
        return (ObservableScrollView) findViewById(R.id.scrollable);
    }
}
