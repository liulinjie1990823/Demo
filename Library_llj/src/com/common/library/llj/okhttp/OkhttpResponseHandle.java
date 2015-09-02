package com.common.library.llj.okhttp;

import android.content.Context;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.ToastUtilLj;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * 返回处理类
 * Created by liulj on 15/9/2.
 */
public abstract class OkhttpResponseHandle<T extends BaseReponse> extends OkhttpAsyncResponseHandler<T> {
    private FormEncodingBuilder mFormEncodingBuilder;
    private static String NET_BAD = "网络连接差，请稍后再试";

    /**
     * 需要传参数的可以在回调的RequestParams对象中传入字段参数
     *
     * @return RequestParams对象
     */
    public void setFormParams(FormEncodingBuilder formEncodingBuilder) {
        mFormEncodingBuilder = formEncodingBuilder;
    }

    public FormEncodingBuilder getFormParams() {
        return mFormEncodingBuilder;
    }

    public FormEncodingBuilder addFormParams(FormEncodingBuilder formEncodingBuilder) {
        return formEncodingBuilder;
    }

    @Override
    public void networkUnAvailable(Context context) {
        ToastUtilLj.show(context, NET_BAD);
    }

    @Override
    public void onToast(String str) {
        ToastUtilLj.show(context, str);
    }

    @Override
    public void onStart(Request request) {
        LogUtilLj.LLJi("url:" + request.urlString());
        if (request.body() != null)
            LogUtilLj.LLJi("Params:" + request.body().toString());
    }

    @Override
    public void onSuccessByOtherStatus(T response) {

    }

    @Override
    public void onFailure(Request request, IOException e) {
        LogUtilLj.LLJe(e);
    }

    @Override
    public void onFinish() {

    }
}
