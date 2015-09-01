package com.common.library.llj.adapterhelp;

import android.widget.AbsListView;

import com.common.library.llj.utils.LogUtilLj;

import java.util.List;

/**
 * Created by liulj on 15/8/21.
 */
public class LoadMoreContral<T> {
    private boolean mHasMore;//是否还有更多数据
    private boolean mIsLoading = true; //是否正在加载中
    private int mPage = 1;//默认第一页开始
    private int mTotle;
    private OnLoadMoreListener mOnLoadMoreListener;
    private QuickAdapter<T> mQuickAdapter;

    public LoadMoreContral(OnLoadMoreListener onLoadMoreListener, AbsListView absListView, QuickAdapter<T> quickAdapter) {
        mOnLoadMoreListener = onLoadMoreListener;
        mQuickAdapter = quickAdapter;
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean mIsEnd = false;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0 && this.mIsEnd) {
                    if (mOnLoadMoreListener != null) {
                        if (mHasMore) {
                            if (!mIsLoading) {
                                mIsLoading = true;
                                mOnLoadMoreListener.doLoadMoreData();
                            }
                        }
                    }
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtilLj.LLJi("firstVisibleItem:" + firstVisibleItem + "visibleItemCount:" + visibleItemCount + "totalItemCount:" + totalItemCount);
                LogUtilLj.LLJi("view.getLastVisiblePosition():" + view.getLastVisiblePosition());
                if (view.getLastVisiblePosition() >= totalItemCount - 1) {
                    this.mIsEnd = true;
                } else {
                    this.mIsEnd = false;
                }

            }
        });
    }


    public void onLoadMoreFinish(List<T> elem) {
        mIsLoading = false;

        if (mPage == 1) {
            mQuickAdapter.clear();
        }
        if (elem != null && elem.size() != 0)
            mQuickAdapter.addAllNotNotify(elem);
        if (mTotle != 0 && mTotle <= mQuickAdapter.getList().size()) {
            // 已经没有数据，隐藏加载更多view
            mHasMore = false;
            mQuickAdapter.showIndeterminateProgress(false);
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.LoadFinished(true);
            }
        } else {
            // 还有数据，显示隐藏加载更多view，并且更新listview
            mHasMore = true;
            mQuickAdapter.showIndeterminateProgress(true);
            mPage++;
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.LoadFinished(false);
            }
        }
    }


    public int getmPage() {
        return mPage;
    }

    public void setTotle(int mTotle) {
        this.mTotle = mTotle;
    }

    /**
     *
     */
    public void doLoadFirstPageData() {
        mPage = 1;
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.doLoadMoreData();
        }
    }

    /**
     *
     */
    public interface OnLoadMoreListener {
        /**
         * 加载更多数据
         */
        public void doLoadMoreData();

        /**
         * 每次加载完成
         */
        public void LoadFinished(boolean complete);
    }
}
