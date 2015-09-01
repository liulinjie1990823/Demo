package com.common.library.llj.utils;

import android.os.Build;

/**
 * Created by liulj on 15/8/29.
 */
public class BuildVersionUtil {
    /**
     * 14以后
     *
     * @return
     */
    public static boolean afterIce() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 19以后
     *
     * @return
     */
    public static boolean afterKITKAT() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 19以后
     *
     * @return
     */
    public static boolean afterLOLLIPOP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
