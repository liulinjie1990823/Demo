package com.common.library.llj.adapterhelp;

import android.widget.AbsListView;

import com.common.library.llj.utils.LogUtilLj;

import java.util.List;

/**
 * Created by liulj on 15/8/22.
 */
public class LoadMoreReverseControl<T> {
    private boolean mHasMore;//是否还有更多数据
    private boolean mIsLoading = true; //是否正在加载中
    private int mType;//0下拉，1上拉
    private OnLoadMoreListener mOnLoadMoreListener;
    private QuickAdapter<T> mQuickAdapter;

    public LoadMoreReverseControl(OnLoadMoreListener onLoadMoreListener, AbsListView absListView, QuickAdapter<T> quickAdapter) {
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
                                mType = 1;
                                mQuickAdapter.showIndeterminateProgress(true);
                                mOnLoadMoreListener.doLoadMoreData(mType);
                            }
                        }
                    }
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtilLj.LLJi("firstVisibleItem:" + firstVisibleItem + "visibleItemCount:" + visibleItemCount + "totalItemCount:" + totalItemCount);
                LogUtilLj.LLJi("view.getLastVisiblePosition():" + view.getLastVisiblePosition());
                if (view.getLastVisiblePosition() >= totalItemCount) {
                    this.mIsEnd = true;
                } else {
                    this.mIsEnd = false;
                }

            }
        });
    }

    public boolean checkIfOutofScreen() {

        return true;
    }

    public void onLoadMoreFinish(List<T> elem) {
        mIsLoading = false;
        if (mType == 0) {
            //下拉
            if (elem != null && elem.size() != 0) {
                mHasMore = true;
                mQuickAdapter.getList().addAll(0, elem);
            } else {
                mHasMore = false;
            }
        } else if (mType == 1) {
            //上拉
            if (elem != null && elem.size() != 0) {
                mHasMore = true;
                mQuickAdapter.getList().addAll(elem);
                mQuickAdapter.showIndeterminateProgress(true);
            } else {
                mHasMore = false;
                mQuickAdapter.showIndeterminateProgress(false);
            }
        }


//        if (elem != null && elem.size() != 0) {
//            if (mType == 0) {
//                //下拉
//                //考虑是否要需要先清除之前的数据，这样可以节省内存
//                //mQuickAdapter.getList().clear();
//                mQuickAdapter.getList().addAll(0, elem);
//            } else if (mType == 1) {
//                //上拉
//                mQuickAdapter.addAllNotNotify(elem);
//            }
//        } else {
//            if (mType == 0) {
//                //下拉
//                if (mQuickAdapter.getList().size() == 0) {
//                    mQuickAdapter.showIndeterminateProgress(false);
//                } else {
//                    mQuickAdapter.showIndeterminateProgress(true);
//                }
//
//            } else if (mType == 1) {
//                //上拉
//                mQuickAdapter.showIndeterminateProgress(false);
//            }
//
//
//        }
//        if (mQuickAdapter.getList().size() == 0 || elem == null || (elem != null && elem.size() == 0)) {
//            // 始终没有数据，或者刚开始有数据后来没有数据，隐藏加载更多view
//            mHasMore = false;
//            mQuickAdapter.showIndeterminateProgress(false);
//        } else {
//            // 还有数据，显示隐藏加载更多view，并且更新listview
//            mHasMore = true;
//            mQuickAdapter.showIndeterminateProgress(true);
//        }
        //
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.LoadFinished(mHasMore, mQuickAdapter.getList());
        }
    }

    public int getmType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }


    /**
     *
     */
    public void doLoadFirstPageData() {
        mType = 0;
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.doLoadMoreData(mType);
        }
    }

    /**
     *
     */
    public interface OnLoadMoreListener<T> {
        /**
         * 加载更多数据,0下拉，1上拉
         */
        public void doLoadMoreData(int type);

        /**
         * 每次加载完成
         */
        public void LoadFinished(boolean hasmore, List<T> finishData);
    }
}
