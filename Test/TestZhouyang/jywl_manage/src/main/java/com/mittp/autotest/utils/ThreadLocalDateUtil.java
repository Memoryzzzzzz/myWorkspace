package com.mittp.autotest.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.util.StringUtils;

/**
 * 构造线程安全的DateUtils
 *
 * @author zhouyang
 * 2019-07-03 19:16
 */
public class ThreadLocalDateUtil {

    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<>();

    private static DateFormat getDateFormat(String format) {
        DateFormat df = threadLocal.get();
        if (df == null) {
            if (StringUtils.isEmpty(format)) {
                df = new SimpleDateFormat(DATE_FORMAT_SECOND);
            } else {
                df = new SimpleDateFormat(format);
            }
            threadLocal.set(df);
        }
        return df;
    }


    public static String formatDate(Date date, String format){
        return getDateFormat(format).format(date);
    }

    public static Date parse(String strDate, String format) throws ParseException {
        return getDateFormat(format).parse(strDate);
    }

}
