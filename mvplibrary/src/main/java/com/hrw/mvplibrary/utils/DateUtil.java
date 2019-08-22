package com.hrw.mvplibrary.utils;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/14 11:23
 * @desc:
 */
public class DateUtil {
    public static String getFormatDate(@NonNull String pattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getFormatDate(Date date, @NonNull String pattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getFormatDate(long milli, String pattern) {
        Date date = new Date();
        date.setTime(milli);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
