package com.common.library.llj.utils;

import android.app.Activity;

import com.common.library.llj.R;

/**
 * 界面跳转动画，在跳转代码后调用
 * 
 * @author llj
 * 
 */
public class ActivityAnimUtilLj {
	/**
	 * 新界面从右边进入
	 * 
	 * @param activity
	 */
	public static void startPullFromRight(Activity activity) {
		activity.overridePendingTransition(R.anim.pull_in_right, R.anim.no_fade);
	}

	/**
	 * 关闭界面从右边出去
	 * 
	 * @param activity
	 */
	public static void finishPushOutRight(Activity activity) {
		activity.overridePendingTransition(R.anim.no_fade, R.anim.push_out_right);
	}

	/**
	 * 进入界面从底部进入到中间
	 * 
	 * @param activity
	 */
	public static void startBottomToCenter(Activity activity) {
		activity.overridePendingTransition(R.anim.bottom_to_center, R.anim.no_fade);
	}

	/**
	 * 关闭界面从中间向底部退去
	 * 
	 * @param activity
	 */
	public static void finishCenterToBottom(Activity activity) {
		activity.overridePendingTransition(R.anim.no_fade, R.anim.center_to_bottom);
	}

	/**
	 * 系統淡入淡出,进出公用
	 * 
	 * @param activity
	 */
	public static void fadeInAndOut(Activity activity) {
		activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	/**
	 * 左滑进右滑出,有透明值,进出公用
	 * 
	 * @param activity
	 */
	public static void slideInLeftAndRightOut(Activity activity) {
		activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

	/**
	 * 水平翻转,类似关闭打开，进出公用
	 * 
	 * @param activity
	 */
	public static void flipHorizontal(Activity activity) {
		activity.overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
	}

	/**
	 * 垂直翻转,类似关闭打开，进出公用
	 * 
	 * @param activity
	 */
	public static void flipVertical(Activity activity) {
		activity.overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
	}

	/**
	 * 自定义淡入淡出,进出公用
	 * 
	 * @param activity
	 */
	public static void fadeAnimation(Activity activity) {
		activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	/**
	 * 左上角消失，左上角出来
	 * 
	 * @param activity
	 */
	public static void disappearTopLeft(Activity activity) {
		activity.overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
	}

	/**
	 * 左上角出来，左上角消失
	 * 
	 * @param activity
	 */
	public static void appearTopLeftAnimation(Activity activity) {
		activity.overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void disappearBottomRightAnimation(Activity activity) {
		activity.overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
	}

	/**
	 * 
	 * @param activity
	 */
	public static void appearBottomRightAnimation(Activity activity) {
		activity.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
	}

	/**
	 * 先放大后缩小，进出公用
	 * 
	 * @param activity
	 */
	public static void unzoomAnimation(Activity activity) {
		activity.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
	}

	/**
	 * 左边出去右边进入
	 * 
	 * @param activity
	 */
	public static void pullRightPushLeft(Activity activity) {
		activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
	}

	/**
	 * 右边出去左边进入
	 * 
	 * @param activity
	 */
	public static void pullLeftPushRight(Activity activity) {
		activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
	}
}
