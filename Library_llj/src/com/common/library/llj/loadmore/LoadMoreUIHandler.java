package com.common.library.llj.loadmore;

/**
 * 用来控制加载布局的ui变化，加载view需要实现
 *
 * @author
 */
public interface LoadMoreUIHandler {

    public static final int STATUS_LOADED_EMPTY = 1;
    public static final int STATUS_LOADED_NO_MORE = 1;

    /**
     * 正在加载中的状态，控制加载的view
     *
     * @param container linearlayout
     */
    public void onLoading(LoadMoreContainer container);

    /**
     * 自动加载完成，控制加载的view
     *
     * @param container linearlayout
     * @param empty
     * @param hasMore
     */
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore);

    /**
     * 非自动加载，等待用户点击，控制加载的view
     *
     * @param container linearlayout
     */
    public void onWaitToLoadMore(LoadMoreContainer container);

    /**
     * @param var1
     * @param var2
     * @param var3
     */
    public void onLoadError(LoadMoreContainer var1, int var2, String var3);
}