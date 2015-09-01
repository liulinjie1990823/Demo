package com.common.library.llj.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class ScrollUtilLj {
	/**
	 * 限制一个值在一定的区间内
	 * 
	 * @param value
	 *            目标值
	 * @param minValue
	 *            最小值，如果目标值小于最小值就返回最小值
	 * @param maxValue
	 *            最大值，如果目标值大于最小值就返回最大值
	 * @return 在区间之内的就返回目标值
	 */
	public static Float getFloat(float value, float minValue, float maxValue) {
		return Math.min(maxValue, Math.max(minValue, value));
	}

	/**
	 * 
	 * @param value
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public static Integer getInteger(int value, int minValue, int maxValue) {
		return Math.min(maxValue, Math.max(minValue, value));
	}

	/**
	 * Create a color integer value with specified alpha. This may be useful to change alpha value of background color.
	 * 
	 * @param alpha
	 *            alpha value from 0.0f to 1.0f.
	 * @param baseColor
	 *            base color. alpha value will be ignored.
	 * @return a color with alpha made from base color
	 */
	public static int getColorWithAlpha(float alpha, int baseColor) {
		int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
		int rgb = 0x00ffffff & baseColor;
		return a + rgb;
	}

	/**
	 * 设置titlebar随着滑动颜色渐变
	 * 
	 * @param view
	 *            titlebar
	 * @param duringHeight
	 *            alpha的值在这个高度内从0变为1
	 * @param scrollY
	 *            向上滑传进来的scrollY
	 * @param color
	 *            titlebar的颜色
	 */
	public static void setTitlebarBackgroundColor(View view, int duringHeight, int scrollY, int color) {
		float alpha = 1 - (float) Math.max(0, duringHeight - scrollY) / duringHeight;
		view.setBackgroundColor(getColorWithAlpha(alpha, color));
	}

	/**
	 * ViewPager
	 * 
	 * @param titlebar
	 * @return
	 */
	public static Boolean titlebarIsShown(View titlebar) {
		return ViewHelper.getTranslationY(titlebar) == 0;
	}

	/**
	 * ViewPager
	 * 
	 * @param titlebar
	 * @return
	 */
	public static Boolean titlebarIsHidden(View titlebar) {
		return ViewHelper.getTranslationY(titlebar) == -titlebar.getHeight();
	}

	/**
	 * ViewPager 显示titlebar，需要先隐藏在调用该方法显示（当底部布局时framlayout, titlebar覆盖在listview或者scrollview上面的时候）
	 * 
	 * @param titlebar
	 */
	public static void animShowTitlebar(View titlebar) {
		ViewPropertyAnimator.animate(titlebar).cancel();
		// 向下移动Titlebar的高度
		ViewPropertyAnimator.animate(titlebar).translationY(0).setDuration(200).start();
	}

	/**
	 * ViewPager 向上滑动时隐藏titlebar（当底部布局时framlayout,titlebar覆盖在listview或者scrollview上面的时候）
	 * 
	 * @param titlebar
	 */
	public static void animHideTitlebar(View titlebar) {
		int height = titlebar.getMeasuredHeight();
		ViewPropertyAnimator.animate(titlebar).cancel();
		// 向上移动Titlebar的高度
		ViewPropertyAnimator.animate(titlebar).translationY(-height).setDuration(200).start();
	}

	/**
	 * ViewPager 当底部布局时framlayout,titlebar覆盖在listview或者scrollview上面的时候
	 * 
	 * @param titlebar
	 * @param scrollY
	 * @param toolbarHeight
	 */
	public static void moveTitlebar(View headView, int scrollY, int toolbarHeight) {
		// headerTranslationY为负的代表向上移动
		float headerTranslationY = getFloat(-scrollY, -toolbarHeight, 0);
		ViewPropertyAnimator.animate(headView).cancel();
		ViewHelper.setTranslationY(headView, headerTranslationY);
	}

	/**
	 * 适用于外层布局时FrameLayout,titlebar在顶部，scrollView设置了topMargin顶部的距离（ 会改变scrollView的高度）
	 * 
	 * @param titlebar
	 *            需要移动的titlebar
	 * @param translationY
	 *            向上隐藏为传-mTitlebar.getHeight();向下隐藏传0
	 * @param view
	 * @param screenHeight
	 *            屏幕高度
	 */
	public static void animTitlebarAdjustScrollView(final View titlebar, float translationY, final View view, final int screenHeight) {
		if (ViewHelper.getTranslationY(titlebar) == translationY) {
			return;
		}
		// 使用ValueAnimator需要自己往titlebar里设置值
		ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(titlebar), translationY).setDuration(200);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float translationY = Float.parseFloat(String.valueOf(animation.getAnimatedValue()));
				ViewHelper.setTranslationY(titlebar, translationY);
				ViewHelper.setTranslationY(view, translationY);
				FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) view).getLayoutParams();
				lp.height = (int) -translationY + screenHeight - lp.topMargin;
				view.requestLayout();
			}
		});
		animator.start();
	}

	/**
	 * 移动一个view到titlebar上
	 * 
	 * @param scrollY
	 * @param maxFlexibleHeight
	 *            用来计算translationView应该放大的比例
	 * @param translationView
	 *            需要移动的view
	 * @param titlebar
	 *            标题栏
	 */
	public static void updateFlexibleSpaceText(final int scrollY, int maxFlexibleHeight, View translationView, View titlebar) {
		int adjustedScrollY = getInteger(scrollY, 0, maxFlexibleHeight);
		// 计算最大放大比例
		float maxScale = (float) (maxFlexibleHeight - titlebar.getHeight()) / titlebar.getHeight();
		// 计算随着translationView移动后应该缩放的比例
		float scale = maxScale * ((float) maxFlexibleHeight - adjustedScrollY) / maxFlexibleHeight;
		// 设置以左上角为中心进行放大
		ViewHelper.setPivotX(translationView, 0);
		ViewHelper.setPivotY(translationView, 0);
		ViewHelper.setScaleX(translationView, 1 + scale);
		ViewHelper.setScaleY(translationView, 1 + scale);
		// 设置translationView向下移动的最大位置，也就是初始移动的位置
		int maxTitleTranslationY = titlebar.getMeasuredHeight();
		// 计算以后向上移动的时候translationView实际移动的titleTranslationY(比maxTitleTranslationY-adjustedScrollY的值偏大，就是比如应该移动到90的位置，实际移动的却是95)
		int titleTranslationY = (int) (maxTitleTranslationY * ((float) maxFlexibleHeight - adjustedScrollY) / maxFlexibleHeight);
		Log.i("llj", titleTranslationY + "");
		ViewHelper.setTranslationY(translationView, titleTranslationY);
	}

	public static void updateFlexibleSpaceText2(final int scrollY, int maxFlexibleHeight, View translationView, View titlebar, Interpolator interpolator) {
		float ratio = getFloat(scrollY / maxFlexibleHeight, 0.0f, 1.0f);
		// 计算最大放大比例
		float maxScale = (float) (maxFlexibleHeight - titlebar.getHeight()) / titlebar.getHeight();
		// 计算随着translationView移动后应该缩放的比例
		float scale = 1 + maxScale - maxScale * interpolator.getInterpolation(ratio);
		// 设置以左上角为中心进行放大
		ViewHelper.setPivotX(translationView, 0);
		ViewHelper.setPivotY(translationView, 0);
		ViewHelper.setScaleX(translationView, scale);
		ViewHelper.setScaleY(translationView, scale);
		// 设置translationView向下移动的最大位置，也就是初始移动的位置
		int maxTitleTranslationY = titlebar.getMeasuredHeight();
		// 计算以后向上移动的时候translationView实际移动的titleTranslationY(比maxTitleTranslationY-adjustedScrollY的值偏大，就是比如应该移动到90的位置，实际移动的却是95)
		float titleTranslationY = maxTitleTranslationY - maxTitleTranslationY * interpolator.getInterpolation(ratio);
		ViewHelper.setTranslationY(translationView, titleTranslationY);
	}

	/**
	 * Add an OnGlobalLayoutListener for the view. This is just a convenience method for using {@code ViewTreeObserver.OnGlobalLayoutListener()}. This also handles removing listener when
	 * onGlobalLayout is called.
	 * 
	 * @param view
	 *            the target view to add global layout listener
	 * @param runnable
	 *            runnable to be executed after the view is laid out
	 */
	public static void addOnGlobalLayoutListener(final View view, final Runnable runnable) {
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				runnable.run();
			}
		});
	}
}
