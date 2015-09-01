package com.common.library.llj.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.common.library.llj.R;
import com.common.library.llj.lifecycle.ActivityLifecycleCallbacksCompat;
import com.common.library.llj.lifecycle.LifecycleDispatcher;
import com.common.library.llj.utils.AsyncHttpClientUtil;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 基类application
 *
 * @author liulj
 */
public abstract class BaseApplication extends Application {
    public static String TEMP_PATH = "";// 临时文件路径
    public static String PIC_PATH = "";// 缓存图片路径
    public static String VIDEO_PATH = "";// 缓存语音路径
    public static String APK_PATH = "";// 缓存语音路径
    public static int DISPLAY_HEIGHT;// 屏幕高度
    public static int DISPLAY_WIDTH;// 屏幕宽度
    public static int STATUS_BAR_HEIGHT;// 状态栏高度

    public static List<Activity> mActivityList = new ArrayList<Activity>();// 存放activity的集合
    private AlertDialog dialog;// 捕捉全局弹出的dialog
    public static BaseApplication mInstance;// Application实例
    protected boolean needExceptionDialog;// 是否显示异常对话框
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private Notification mNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;// Application实例
        // 出现应用级异常时的处理
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, final Throwable throwable) {
                uploadThrowable(throwable);// 上传错误到服务器
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        showExceptionDialog(throwable);// 显示错误日志对话框
                    }
                }).start();
            }
        });
        initDisplay();// 初始化屏幕宽高信息
        initSavePath();// 初始化文件存储路径
        LifecycleDispatcher.registerActivityLifecycleCallbacks(this, new MyActivityLifecycleCallbacksCompat());// 注册全局activity的监听
    }

    public void showExceptionDialog(Throwable throwable) {
        // 弹出报错并强制退出的对话框
        if (mActivityList.size() > 0) {
            Looper.prepare();
            // final LoginDialog loginDialog = new
            // LoginDialog(getCurrentActivity(),
            // R.style.dim_dialog, "提示", "啊哦！现金券飞走了  需要重新启动！");
            // loginDialog.setWindowParams();
            // loginDialog.findViewById(R.id.btn_sure).setOnClickListener(new
            // OnClickListener() {
            // @Override
            // public void onClick(View v) {
            // Intent intent = new
            // Intent(getApplicationContext(),
            // LoadingActivity.class);
            // PendingIntent restartIntent =
            // PendingIntent.getActivity(getApplicationContext(),
            // 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            // AlarmManager mgr = (AlarmManager)
            // getSystemService(Context.ALARM_SERVICE);
            // mgr.set(AlarmManager.RTC,
            // System.currentTimeMillis() + 1000,
            // restartIntent); // 1秒钟后重启应用
            // loginDialog.dismiss();
            // finish();
            // }
            // });
            // loginDialog.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
            // loginDialog.show();
            dialog = new AlertDialog.Builder(getCurrentActivity()).create();
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(Log.getStackTraceString(throwable));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 强制退出程序
                    finish();
                }
            });
            dialog.show();
            Looper.loop();
        }
    }

    // 上传错误日志
    public abstract void uploadThrowable(Throwable throwable);

    // 初始化屏幕宽高信息
    private void initDisplay() {
        // 获得屏幕高度（像素）
        BaseApplication.DISPLAY_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        // 获得屏幕宽度（像素）
        BaseApplication.DISPLAY_WIDTH = getResources().getDisplayMetrics().widthPixels;
        // 获得系统状态栏高度（像素）
        BaseApplication.STATUS_BAR_HEIGHT = getStatusBarHeight();
    }

    // 初始化文件存储路径
    private void initSavePath() {
        // 文件路径设置
        String parentPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 使用自己设置的sdcard缓存路径，需要应用里设置清除缓存按钮
            parentPath = Environment.getExternalStorageDirectory().getPath() + File.separator + getPackageName();
        } else {
            // data/data/包名/files（这个文件夹在apk安装的时候就会创建）
            parentPath = getApplicationContext().getFilesDir().getAbsolutePath();
        }
        // 临时文件路径设置
        BaseApplication.TEMP_PATH = parentPath + "/tmp";
        // 图片缓存路径设置
        BaseApplication.PIC_PATH = parentPath + "/.pic";
        // 更新APK路径设置
        BaseApplication.APK_PATH = parentPath + "/apk";
        // 创建各目录
        new File(BaseApplication.TEMP_PATH).mkdirs();
        new File(BaseApplication.PIC_PATH).mkdirs();
        new File(BaseApplication.APK_PATH).mkdirs();
    }

    /**
     * activity的全局监听
     *
     * @author liulj
     */
    private class MyActivityLifecycleCallbacksCompat implements ActivityLifecycleCallbacksCompat {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {
            // 停止该页面的请求
//            AsyncHttpClientUtil.get().cancelRequests(activity, true);
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

    }

    private int NOTIFICATION_ID_UPDATE = 4;

    /**
     * @param url
     * @param name
     */
    public void updateApk(String url, final String name) {
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.get(this, url, new FileAsyncHttpResponseHandler(new File(BaseApplication.APK_PATH + "/" + name)) {
//            @Override
//            public void onStart() {
//                super.onStart();
//                createNotification(name);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, File file) {
//                if (file != null && file.exists()) {
//                    installApk(file);
//                }
//                mNotificationManager.cancel(NOTIFICATION_ID_UPDATE);
//                finish();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
//
//            }
//
//            @Override
//            public void onProgress(long bytesWritten, long totalSize) {
//                super.onProgress(bytesWritten, totalSize);
//                updateProgerss(bytesWritten, totalSize);
//            }
//        });
    }

    /**
     * @param app_name
     */
    public void createNotification(String app_name) {
        // 这里是自己点击触发要跳转的界面
        // Intent notificationIntent = new Intent(this, MenuActivity.class);
        // notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
        // Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // mNotification.contentIntent = PendingIntent.getActivity(this, 0,
        // notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotification = new NotificationCompat.Builder(this).setTicker(app_name + getString(R.string.is_downing)).setSmallIcon(R.drawable.ic_launcher).build();
        mNotification.flags = Notification.FLAG_NO_CLEAR;
        // 自定义通知的view
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, app_name + getString(R.string.is_downing));
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
        mNotification.contentView = contentView;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(R.layout.notification_item, mNotification);
    }

    /**
     * 更新进度框
     *
     * @param bytesWritten
     * @param totalSize
     */
    public void updateProgerss(long bytesWritten, long totalSize) {
        double percent;
        if (bytesWritten == 0) {
            percent = 0;
        } else if (bytesWritten == totalSize) {
            percent = 100;
        } else {
            percent = bytesWritten / totalSize * 100.0;
        }
        mRemoteViews.setTextViewText(R.id.notificationPercent, percent + "%");
        mRemoteViews.setProgressBar(R.id.notificationProgress, 100, (int) percent, false);
        mNotification.contentView = mRemoteViews;
        mNotificationManager.notify(R.layout.notification_item, mNotification);
    }

    /**
     * 安装apk
     *
     * @param file
     */
    public void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 获得当前最顶层的activity
     *
     * @return 当前最顶层的activity
     */
    public static Activity getCurrentActivity() {
        if (mActivityList.size() >= 1) {
            return mActivityList.get(mActivityList.size() - 1);
        }
        return null;
    }

    /**
     * 生成Activity存入列表
     *
     * @param activity
     */
    public static void addCurrentActivity(Activity activity) {
        if (activity != null)
            mActivityList.add(activity);
    }

    /**
     * 移除当前的activity
     *
     * @param activity
     */
    public static void removeCurrentActivity(Activity activity) {
        if (activity != null)
            mActivityList.remove(activity);
    }

    /**
     * 获得顶层下面的activity
     *
     * @return
     */
    public static Activity getPreviousActivity() {
        if (mActivityList.size() >= 2) {
            return mActivityList.get(mActivityList.size() - 2);
        }
        return null;
    }

    /**
     * 清除最上层以下所有的activity
     */
    public static void clearBottomActivities() {
        if (mActivityList.size() >= 1) {
            Activity lastActivity = mActivityList.get(mActivityList.size() - 1);
            for (int i = 0; i < mActivityList.size() - 1; i++) {
                Activity activity = mActivityList.get(i);
                if (activity != null)
                    activity.finish();
            }
            mActivityList.clear();
            mActivityList.add(lastActivity);
        }
    }

    /**
     * 清除所有的activity
     */
    public static void removeAllActivity() {
        for (int i = 0; i < mActivityList.size(); i++) {
            Activity activity = mActivityList.get(i);
            if (activity != null)
                activity.finish();
        }
        mActivityList.clear();
    }

    /**
     * 获取手机状态栏高度
     *
     * @return 手机状态栏高度
     */
    private int getStatusBarHeight() {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object obj = cls.newInstance();
            Field field = cls.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @SuppressLint("NewApi")
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    /**
     * 程序关闭时调用
     */
    public void finish() {
        if (dialog != null)
            dialog.dismiss();
//        AsyncHttpClientUtil.get().cancelAllRequests(true);
        removeAllActivity();
        System.exit(0);
    }

}
