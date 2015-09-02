package com.common.library.llj.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.common.library.llj.utils.LogUtilLj;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * 处理子线程的返回结果
 * Created by liulj on 15/9/2.
 */
public abstract class OkhttpAsyncResponseHandler<T> implements OkhttpResponseHandlerInterface<T> {
    private static final String LOG_TAG = "OkhttpAsyncResponseHandler";
    private boolean useSynchronousMode;//true 使用同步
    private Handler handler;
    private Looper looper = null;

    protected static final int TOAST_MESSAGE = -1;
    protected static final int START_MESSAGE = 0;
    protected static final int SUCCESS_MESSAGE = 1;
    protected static final int SUCCESS_BY_OTHER_CODE_MESSAGE = 2;
    protected static final int FAILURE_MESSAGE = 3;
    protected static final int FINISH_MESSAGE = 4;
    protected Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OkhttpAsyncResponseHandler() {
        this(null);
    }

    public OkhttpAsyncResponseHandler(Looper looper) {
        this.looper = looper == null ? Looper.myLooper() : looper;
        setUseSynchronousMode(false);

    }

    @Override
    public void sendToastMessage(String str) {
        sendMessage(obtainMessage(TOAST_MESSAGE, new Object[]{str}));
    }

    @Override
    public void sendStartMessage(Request request) {
        sendMessage(obtainMessage(START_MESSAGE, new Object[]{request}));
    }

    @Override
    public void sendSuccessMessage(T response) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{response}));
    }

    @Override
    public void sendSuccessByOtherStatus(T response) {
        sendMessage(obtainMessage(SUCCESS_BY_OTHER_CODE_MESSAGE, new Object[]{response}));

    }

    @Override
    public void sendFailureMessage(Request request, Exception e) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{request, e}));
    }

    @Override
    public void sendFinishMessage() {
        sendMessage(obtainMessage(FINISH_MESSAGE, null));
    }

    @Override
    public void handleMessage(Message message) {
        Object[] response;
        try {
            switch (message.what) {
                case TOAST_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null) {
                        onToast(response[0].toString());
                    } else {
                    }
                    break;
                case START_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null) {
                        onStart((Request) response[0]);
                    } else {
                    }
                    break;
                case SUCCESS_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null) {
                        onSuccess((T) response[0]);
                    } else {
                    }
                    break;
                case SUCCESS_BY_OTHER_CODE_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null) {
                        LogUtilLj.LLJe("Thread:" + looper.getThread().getId());
                        onSuccessByOtherStatus((T) response[0]);
                    } else {
                    }
                    break;
                case FAILURE_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null) {
                        onFailure((Request) response[0], (IOException) response[1]);
                    } else {
                    }
                case FINISH_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null) {
                        onFinish();
                    } else {
                    }
                    break;

            }
        } catch (Throwable error) {
            throw new RuntimeException(error);
        }
    }


    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(handler, responseMessageId, responseMessageData);
    }

    @Override
    public boolean getUseSynchronousMode() {
        return useSynchronousMode;
    }

    @Override
    public void setUseSynchronousMode(boolean sync) {
        // A looper must be prepared before setting asynchronous mode.
        if (!sync && looper == null) {
            sync = true;
        }
        // If using asynchronous mode.
        if (!sync && handler == null) {
            // Create a handler on current thread to submit tasks
            handler = new ResponderHandler(this, looper);
        } else if (sync && handler != null) {
            handler = null;
        }

        useSynchronousMode = sync;
    }

    /**
     * @param msg
     */
    protected void sendMessage(Message msg) {
        if (getUseSynchronousMode() || handler == null) {
            handleMessage(msg);
        } else if (!Thread.currentThread().isInterrupted()) { // do not send messages if request has been cancelled
            handler.sendMessage(msg);
        }
    }

    /**
     *
     */
    private static class ResponderHandler extends Handler {
        private final OkhttpAsyncResponseHandler mResponder;

        ResponderHandler(OkhttpAsyncResponseHandler mResponder, Looper looper) {
            super(looper);
            this.mResponder = mResponder;
        }

        @Override
        public void handleMessage(Message msg) {
            mResponder.handleMessage(msg);
        }
    }
}
