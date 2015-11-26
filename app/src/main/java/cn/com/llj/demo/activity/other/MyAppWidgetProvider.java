package cn.com.llj.demo.activity.other;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.ToastUtilLj;

import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/19.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String CLICK_ACTION = "com.llj.action.click";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        ToastUtilLj.show(context, "onEnabled");
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        LogUtilLj.LLJi("intent.geAction:" + intent.getAction());
        super.onReceive(context, intent);
        if (intent.getAction().equals(CLICK_ACTION)) {
            ToastUtilLj.show(context, CLICK_ACTION);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtilLj.LLJi("setOnClickPendingIntent");
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_done);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.imageView1, rotateBitmap(context, bitmap, degree));
                        Intent intent1 = new Intent();
                        intent1.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
                        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();


        }
    }

    /**
     * 单被添加到桌面，或者是updatePeriodMillis中设置的时间到了才会发送ACTION_APPWIDGET_UPDATE广播
     * 然后onUpdate方法才会被调用
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        ToastUtilLj.show(context, "onUpdate");
        final int counter = appWidgetIds.length;
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);

        }
    }

    public void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intent1 = new Intent();
        intent1.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap temBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return temBitmap;

    }
}
