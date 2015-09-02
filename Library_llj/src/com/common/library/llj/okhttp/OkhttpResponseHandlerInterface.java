package com.common.library.llj.okhttp;

import android.content.Context;
import android.os.Message;

import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * 数据处理的方法
 * Created by liulj on 15/9/2.
 */
public interface OkhttpResponseHandlerInterface<T> {
    void sendStartMessage(Request request);

    void sendToastMessage(String str);

    void sendSuccessMessage(T response);


    void sendSuccessByOtherStatus(T response);

    void sendFailureMessage(Request request, Exception e);

    void sendFinishMessage();

    void handleMessage(Message message);

    boolean getUseSynchronousMode();

    void setUseSynchronousMode(boolean sync);

    /**
     * 在主线程弹提示
     *
     * @param str
     */
    void onToast(String str);

    /**
     * 做一些初始化操作
     */
    void onStart(Request request);

    /**
     * 返回数据成功，且解析后status为1
     *
     * @param response
     */
    void onSuccess(T response);

    /**
     * 返回数据成功，且解析后status为其他码
     *
     * @param response
     */
    void onSuccessByOtherStatus(T response);


    /**
     * 请求失败的时候调用,已经空实现，一般自己不用实现
     *
     * @param request
     * @param e
     */
    void onFailure(Request request, IOException e);

    /**
     *
     */
    void onFinish();

    /**
     * 网络不可用时候的返回
     *
     * @param context 上下文
     */
    void networkUnAvailable(Context context);
}
