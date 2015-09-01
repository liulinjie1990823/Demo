package com.common.library.llj.utils;

import android.text.TextUtils;

/**
 * 字符串强转，已经处理了异常
 *
 * @author liulj
 */
public class ParseUtilLj {
    /**
     * 1.
     *
     * @param string
     * @return
     */
    public static Integer parseInt(String string) {
        return parseInt(string, 0);
    }

    /**
     * 2.
     *
     * @param string
     * @param def
     * @return
     */
    public static Integer parseInt(String string, Integer def) {
        if (TextUtils.isEmpty(string))
            return def;
        Integer num = def;
        try {
            num = Integer.parseInt(string);
        } catch (Exception e) {
            return num;
        }
        return num;
    }

    /**
     * 3.
     *
     * @param string
     * @return
     */
    public static Long parseLong(String string) {
        return parseLong(string, 0l);
    }

    /**
     * 4.
     *
     * @param string
     * @param def
     * @return
     */
    public static Long parseLong(String string, Long def) {
        if (TextUtils.isEmpty(string))
            return def;
        Long num = def;
        try {
            num = Long.parseLong(string);
        } catch (Exception e) {
            return num;
        }
        return num;
    }

    /**
     * @param string
     * @param def
     * @return
     */
    public static float parseFloat(String string, float def) {
        if (TextUtils.isEmpty(string))
            return def;
        float num = def;
        try {
            num = Float.parseFloat(string);
        } catch (Exception e) {
            return num;
        }
        return num;

    }

    /**
     * @param string
     * @return
     */
    public static float parseFloat(String string) {
        return parseFloat(string, 0f);
    }
}