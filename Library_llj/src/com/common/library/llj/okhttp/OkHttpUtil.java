package com.common.library.llj.okhttp;

import android.content.Context;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.NetWorkUtilLj;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp的工具类
 * Created by liulj on 15/9/2.
 */
public class OkHttpUtil {
    private static final String CHARSET_NAME = "UTF-8";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new Gson();

    private static OkHttpClient mOkHttpClient = null;
    private static OkHttpUtil okHttpUtil = null;
    private static String NETWORK_INSTABILITY = "网络不稳定，请稍后再试试！";

    /**
     * 获得单例模式
     *
     * @return
     */
    public static OkHttpUtil get() {
        if (okHttpUtil == null || mOkHttpClient == null) {
            synchronized (AsyncHttpClientUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                    mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
                    mOkHttpClient.networkInterceptors().add(new StethoInterceptor());
                }
            }
        }
        return okHttpUtil;

    }


    /**
     * 该不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * post异步提交json数据
     *
     * @param url
     * @param json
     */
    public <T extends BaseReponse> void postJson(final Context context, String url, String json, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!isNetworkAvailable(context, okhttpResponseHandle))
            return;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).tag(context).build();
        handleCallback(request, context, reponseClass, okhttpResponseHandle);
    }

    /**
     * post异步提交form表单
     */
    public <T extends BaseReponse> void postform(final Context context, String url, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!isNetworkAvailable(context, okhttpResponseHandle))
            return;
        Request request = null;
        if (okhttpResponseHandle != null) {
            okhttpResponseHandle.setFormParams(new FormEncodingBuilder());
            request = new Request.Builder().url(url).post(okhttpResponseHandle.addFormParams(okhttpResponseHandle.getFormParams()).build()).tag(context).build();
        }
        handleCallback(request, context, reponseClass, okhttpResponseHandle);
    }

    /**
     * put异步提交form表单
     */
    public <T extends BaseReponse> void putform(final Context context, String url, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!isNetworkAvailable(context, okhttpResponseHandle))
            return;
        Request request = null;
        if (okhttpResponseHandle != null) {
            okhttpResponseHandle.setFormParams(new FormEncodingBuilder());
            request = new Request.Builder().url(url).put(okhttpResponseHandle.addFormParams(okhttpResponseHandle.getFormParams()).build()).tag(context).build();
        }
        handleCallback(request, context, reponseClass, okhttpResponseHandle);
    }

    /**
     * delete异步提交form表单
     */
    public <T extends BaseReponse> void deleteform(final Context context, String url, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!isNetworkAvailable(context, okhttpResponseHandle))
            return;
        Request request = null;
        if (okhttpResponseHandle != null) {
            okhttpResponseHandle.setFormParams(new FormEncodingBuilder());
            request = new Request.Builder().url(url).delete(okhttpResponseHandle.addFormParams(okhttpResponseHandle.getFormParams()).build()).tag(context).build();
        }
        handleCallback(request, context, reponseClass, okhttpResponseHandle);
    }

    /**
     * delete异步提交
     */
    public <T extends BaseReponse> void delete(final Context context, String url, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!isNetworkAvailable(context, okhttpResponseHandle))
            return;
        Request request = null;
        request = new Request.Builder().url(url).delete().tag(context).build();
        handleCallback(request, context, reponseClass, okhttpResponseHandle);
    }

    /**
     * get异步提交数据
     */
    public <T extends BaseReponse> void get(final Context context, String url, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!isNetworkAvailable(context, okhttpResponseHandle))
            return;
        Request request = new Request.Builder().url(url).build();
        handleCallback(request, context, reponseClass, okhttpResponseHandle);
    }

    /**
     * @param request
     * @param context
     * @param reponseClass
     * @param okhttpResponseHandle
     * @param <T>
     */
    private <T extends BaseReponse> void handleCallback(final Request request, final Context context, final Class<T> reponseClass, final OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (okhttpResponseHandle != null) {
            okhttpResponseHandle.setContext(context);
            okhttpResponseHandle.sendStartMessage(request);
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //取消，或者io异常
                if (okhttpResponseHandle != null) {
                    okhttpResponseHandle.sendFailureMessage(request, e);
                    okhttpResponseHandle.sendToastMessage(NETWORK_INSTABILITY);
                    okhttpResponseHandle.sendFinishMessage();
                }
            }

            @Override
            public void onResponse(Response response) {
                if (!response.isSuccessful()) {
                    if (okhttpResponseHandle != null) {
                        okhttpResponseHandle.sendFailureMessage(request, new IOException("Unexpected code " + response));
                        okhttpResponseHandle.sendToastMessage(NETWORK_INSTABILITY);
                        okhttpResponseHandle.sendFinishMessage();
                    }
                } else {
                    try {
                        if (response.body() != null && response.body().charStream() != null) {
                            LogUtilLj.LLJi("==================================================");
                            T gsonResponse = gson.fromJson(response.body().charStream(), reponseClass);
                            if (gsonResponse != null) {
                                switch (gsonResponse.getStatus()) {
                                    case 1:
                                        if (okhttpResponseHandle != null) {
                                            okhttpResponseHandle.sendSuccessMessage(gsonResponse);
                                        }
                                        break;
                                    default:
                                        if (okhttpResponseHandle != null) {
                                            okhttpResponseHandle.sendSuccessByOtherStatus(gsonResponse);
                                        }
                                        break;
                                }
                            }
                        }
                    } catch (IOException | JsonSyntaxException e) {
                        e.printStackTrace();
                        if (okhttpResponseHandle != null) {
                            okhttpResponseHandle.sendFailureMessage(request, e);
                            okhttpResponseHandle.sendToastMessage(NETWORK_INSTABILITY);
                        }
                    } finally {
                        //
                        if (okhttpResponseHandle != null) {
                            okhttpResponseHandle.sendFinishMessage();
                        }
                    }
                }
            }
        });
    }

    /**
     * 判断网络是否可以使用，不可以就回调
     *
     * @param context
     * @param okhttpResponseHandle
     * @param <T>
     * @return
     */
    private <T extends BaseReponse> boolean isNetworkAvailable(Context context, OkhttpResponseHandle<T> okhttpResponseHandle) {
        if (!NetWorkUtilLj.isNetworkAvailable(context)) {
            okhttpResponseHandle.networkUnAvailable(context);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 这里使用了HttpClinet的API。只是为了方便
     *
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return url + "?" + formatParams(params);
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     *
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }
}
