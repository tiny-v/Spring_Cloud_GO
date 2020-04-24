package com.tiny.sc.core.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * @author mayue
 * @date 2020/04/20
 */
public class DateTimeUtil {

    private static SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String convertTimeStamp(long timeStamp, String format) {
        SimpleDateFormat tFormat = new SimpleDateFormat(format);
        return tFormat.format(new Date(timeStamp));
    }

    /**
     * UTC时间转换为本地时间
     *
     * @param utcTime         格式为【yyyy-MM-dd'T'HH:mm:ss'Z'】的字符串
     * @param localTimePatten 本地时间格式
     * @return 本地时间字符串
     */
    public static String utc2Local(String utcTime, String localTimePatten) {
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        return localFormater.format(gpsUTCDate.getTime());
    }

    /**
     * UTC时间转换为本地时间
     *
     * @param utcTime 格式为【yyyy-MM-dd'T'HH:mm:ss'Z'】的字符串
     * @return 本地时间字符串
     */
    public static String utc2Local(String utcTime) {
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            return utcTime;
        }
        localFormater.setTimeZone(TimeZone.getDefault());
        return localFormater.format(gpsUTCDate.getTime());
    }


    /**
     * 纳秒转换为本地时间
     *
     * @param timestamp 纳秒时间戳
     * @return 本地时间
     */
    public static String timestamp2LocalNano(long timestamp) {
        timestamp = TimeUnit.NANOSECONDS.toMillis(timestamp);
        Date date = new Date(timestamp);
        return localFormater.format(date);
    }

    public static String timestamp2Local(long timestamp) {
        Date date = new Date(timestamp);
        return localFormater.format(date);
    }

    public static String getCurrentTime(Date date, String format) {
        SimpleDateFormat tFormat = new SimpleDateFormat(format);
        return tFormat.format(date);
    }
}
