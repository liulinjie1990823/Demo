package com.common.library.llj.loadmore;

import android.view.View;
import android.widget.AbsListView;

public interface LoadMoreContainer {
    /**
     * @param showLoading
     */
    public void setShowLoadingForFirstPage(boolean showLoading);

    /**
     * 设置自动加载更多
     *
     * @param autoLoadMore
     */
    public void setAutoLoadMore(boolean autoLoadMore);

    /**
     * 设置滚动监听
     *
     * @param l
     */
    public void setOnScrollListener(AbsListView.OnScrollListener l);

    /**
     * 设置加载更多 view
     *
     * @param view
     */
    public void setLoadMoreView(View view);

    /**
     * 设置加载更多加载 view控制器
     *
     * @param handler
     */
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler);

    /**
     * @param handler
     */
    public void setLoadMoreHandler(LoadMoreHandler handler);

    /**
     * 一页加载完成后自己手动调用
     *
     * @param emptyResult
     * @param hasMore
     */
    public void loadMoreFinish(boolean emptyResult, boolean hasMore);

    /**
     * 加载失败的时候，手动调用
     */
    public void loadMoreError(int var1, String var2);

}
