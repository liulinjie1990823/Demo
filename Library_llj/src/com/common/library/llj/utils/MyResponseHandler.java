package com.common.library.llj.utils;

import com.common.library.llj.base.BaseReponse;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 结果返回的控制类，用于AsyncHttpClient
 *
 * @author liulj
 */
public abstract class MyResponseHandler<T extends BaseReponse> extends TextHttpResponseHandler {
//    public RequestParams mRequestParams;
//
//    /**
//     * 在构造函数中初始化RequestParams
//     */
//    public MyResponseHandler() {
//        mRequestParams = new RequestParams();
//    }
//
//    /**
//     * 获得RequestParams对象
//     *
//     * @return RequestParams对象
//     */
//    public RequestParams getParams() {
//        return mRequestParams;
//    }
//
//    /**
//     * 需要传参数的可以在回调的RequestParams对象中传入字段参数
//     *
//     * @param requestParams {@link #getParams()}
//     * @return RequestParams对象
//     */
//    public RequestParams setParams(RequestParams requestParams) {
//        return requestParams;
//    }
//
//    /**
//     * 访问成功并且状态等于1，并且已经在AsyncHttpClientUtil用Gson解析好数据回调
//     *
//     * @param statusCode  baseReponse中的转态字段
//     * @param headers     请求头
//     * @param baseReponse 用Gson解析好数据
//     */
//    public abstract void onSuccess(int statusCode, Header[] headers, T baseReponse);
//
//    /**
//     * 访问成功并且状态不等于1，用于非正常码的判断，由于该类是写在类库中的，可以在外面实现该类重写这个方法
//     *
//     * @param context     上下文
//     * @param statusCode  baseReponse中的转态字段
//     * @param headers     请求头
//     * @param baseReponse 用Gson解析好数据
//     */
//    public void onNotSuccess(Context context, int statusCode, Header[] headers, T baseReponse) {
//
//    }
//
//    /**
//     * 请求失败的时候调用,已经空实现，一般自己不用实现
//     */
//    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//    }
//
//    /**
//     * 这个直接回调字符串的方法自己不需要实现，自己只需要实现上面的抽象onSuccess方法
//     */
//    @Override
//    public void onSuccess(int statusCode, Header[] headers, String responseString) {
//    }
//
//    /**
//     * 网络不可用时候的返回
//     *
//     * @param context 上下文
//     */
//    public void networkUnAvailable(Context context) {
//    }
}
