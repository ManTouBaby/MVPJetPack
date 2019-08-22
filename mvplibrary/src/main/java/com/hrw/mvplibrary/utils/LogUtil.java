package com.hrw.mvplibrary.utils;

import android.content.Context;
import android.util.Log;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/15 9:24
 * @desc:
 */
public class LogUtil {
    private static String mTAG = "MtHappy";
    private static boolean mIsOpen = true;

    public static void init(boolean isOpen) {
        mIsOpen = isOpen;
    }

    public static void init(Context content, boolean isOpen) {
        mTAG = content.getPackageName();
        mIsOpen = isOpen;
    }

    public static void init(String tagName, boolean isOpen) {
        mTAG = tagName;
        mIsOpen = isOpen;
    }

    public static void d(String msg) {
        if (mIsOpen) d(mTAG, msg);
    }

    public static void d(String TAG, String msg) {
        //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
//        int max_str_length = 2001 - TAG.length();
//        //大于4000时
//        while (msg.length() > max_str_length) {
//           Log.d(TAG, msg.substring(0, max_str_length));
//            msg = msg.substring(max_str_length);
//        }
        //剩余部分
        if (mIsOpen) Log.d(TAG, msg);

    }

    public static void e(String msg) {
        if (mIsOpen) e(mTAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (mIsOpen) Log.e(TAG, msg);
    }

    public static void i(String msg) {
        if (mIsOpen) i(mTAG, msg);
    }

    public static void i(String TAG, String msg) {
        if (mIsOpen) Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (mIsOpen) w(mTAG, msg);
    }

    public static void w(String TAG, String msg) {
        if (mIsOpen) Log.w(TAG, msg);
    }

    public static void v(String msg) {
        if (mIsOpen) v(mTAG, msg);
    }

    public static void v(String TAG, String msg) {
        if (mIsOpen) Log.v(TAG, msg);
    }
}
