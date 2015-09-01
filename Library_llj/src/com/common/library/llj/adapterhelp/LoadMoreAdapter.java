package com.common.library.llj.adapterhelp;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

import com.common.library.llj.utils.LogUtilLj;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载更多adapter
 * Created by liulj on 15/8/21.
 */
public abstract class LoadMoreAdapter<T> extends QuickAdapter<T> {
    /**
     * Create a QuickAdapter.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public LoadMoreAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        mOnLoadMoreListener = (OnLoadMoreListener) context;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with some initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public LoadMoreAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
        mOnLoadMoreListener = (OnLoadMoreListener) context;
    }

    /**
     * @param context
     * @param data
     * @param multiItemSupport
     */
    public LoadMoreAdapter(Context context, ArrayList<T> data, MultiItemTypeSupport<T> multiItemSupport) {
        super(context, data, multiItemSupport);
        mOnLoadMoreListener = (OnLoadMoreListener) context;
    }

    private boolean mHasMore;//是否还有更多数据
    private boolean mIsLoading = true; //是否正在加载中
    private int mPage = 1;//默认第一页开始
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setLoadMoreData(ListView listview) {
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    public void onLoadMoreFinish(List<T> elem, int totle) {
        mIsLoading = false;

        if (mPage == 1) {
            clear();
        }
        if (elem != null && elem.size() != 0)
            addAllNotNotify(elem);
        if (totle != 0 && totle <= getList().size()) {
            // 已经没有数据，隐藏加载更多view
            mHasMore = false;
            showIndeterminateProgress(false);
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.LoadFinished(true);
            }
        } else {
            // 还有数据，显示隐藏加载更多view，并且更新listview
            mHasMore = true;
            showIndeterminateProgress(true);
            mPage++;
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.LoadFinished(false);
            }
        }
    }


    public int getmPage() {
        return mPage;
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
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
