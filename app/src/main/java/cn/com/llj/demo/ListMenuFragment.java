package cn.com.llj.demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.common.library.llj.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by liulj on 15/9/1.
 */
public abstract class ListMenuFragment extends BaseFragment {
    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.listview)
    ListView mListView;
    private List<String> mListData = new ArrayList<String>();
    private HomeActivity mHomeActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHomeActivity = (HomeActivity) activity;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.animation_fragment, null);
        ButterKnife.bind(this, view);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_light);
        return view;
    }

    @Override
    protected void addListeners(View view, Bundle savedInstanceState) {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    mSwipeRefreshLayout.setEnabled(true);
                else
                    mSwipeRefreshLayout.setEnabled(false);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);
            }
        });
    }

    @OnItemClick(R.id.listview)
    public void listItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent = new Intent();
        // 当前activity的包名，跳转的具体类名路径
        intent.setComponent(new ComponentName(mHomeActivity.getPackageName(), getPackageName() + "." + mListData.get(arg2)));
        startActivity(intent);
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        setListData(mListData);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mHomeActivity, android.R.layout.simple_list_item_1, mListData);
        mListView.setAdapter(adapter2);
    }

    protected abstract void setListData(List<String> mListData);

    protected abstract String getPackageName();

    @Override
    protected void requestOnCreate() {

    }
}
