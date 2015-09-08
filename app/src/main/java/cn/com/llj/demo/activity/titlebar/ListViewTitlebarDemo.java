package cn.com.llj.demo.activity.titlebar;

import android.widget.ArrayAdapter;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import cn.com.llj.demo.BaseTitlebarControlActivity;
import cn.com.llj.demo.R;

/**
 * @author liulj
 */
public class ListViewTitlebarDemo extends BaseTitlebarControlActivity<ObservableListView> {

    @Override
    protected ObservableListView createScrollable() {
        ObservableListView listView = (ObservableListView) findViewById(R.id.scrollable);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getDummyData(100)));
        return listView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.listview_titlebar_demo;
    }

}
