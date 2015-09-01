package com.common.library.llj.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.library.llj.R;

/**
 *
 */
public class LoadMoreDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;

    public LoadMoreDefaultFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.load_more_defult_footer, this);
        mTextView = (TextView) findViewById(R.id.tv_defult_footer);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.listview_is_downing);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        if (!hasMore) {
            //没有更多数据
            setVisibility(VISIBLE);
//            AbsListView.LayoutParams lp = (AbsListView.LayoutParams) getLayoutParams();
//            lp.height = 1;
//            setLayoutParams(lp);
//            this.setVisibility(GONE);
//            if (empty) {
//                this.mTextView.setText("无更多数据是不是比不上");
//            } else {
            mTextView.setText("无更多数据");
//            }
        } else {
            //没有更多数据
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.listview_click_loadmore);
    }

    @Override
    public void onLoadError(LoadMoreContainer var1, int var2, String var3) {
        setVisibility(VISIBLE);
        mTextView.setText("加载错误");
    }
}
