package com.bm.lobby.util;

import org.apache.commons.lang3.StringUtils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/1 10:30
 */
public class DateUtil {

    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public final static DateTimeFormatter FORMATTER_SIMPLE_SHORT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public final static DateTimeFormatter COMMON_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseDate(String dateStr) {
        LocalDateTime date = LocalDateTime.parse(dateStr, FORMATTER);
        return date;
    }

    public static LocalDateTime parseDate(String dateStr, DateTimeFormatter formatter) {
        LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
        return date;
    }

    public static String format(LocalDateTime date) {
        return date.format(FORMATTER);
    }

    public static String formatNormal(LocalDateTime date) {
        return date.format(COMMON_FORMATTER);
    }

    public static String formatSimple(LocalDateTime date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    public static String toComonFormat(String datatime) {
        return parseDate(datatime, FORMATTER).format(COMMON_FORMATTER);
    }


    /**
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * @param timeZoneOffset
     * @return
     */
    public String getFormatedDateString(int timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
        if (ids.length == 0) {
            // if no ids were returned, something is wrong. use default TimeZone
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    /**
     * 根据时区id 格式化时间
     * @param timeZoneId
     * @return
     */
    public static String getFormatedDateString(String timeZoneId ,String dateFormat){
        TimeZone timeZone = null;
        if(StringUtils.isEmpty(timeZoneId)){
            timeZone = TimeZone.getDefault();
        }else{
            timeZone = TimeZone.getTimeZone(timeZoneId);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(timeZone);
        String dateStr =  sdf.format(new Date());
        return dateStr;
    }

    public static String[] fecthAllTimeZoneIds() {
        Vector v = new Vector();
        String[] ids = TimeZone.getAvailableIDs();
        for (int i = 0; i < ids.length; i++) {
            v.add(ids[i]);
        }
        java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
        v.copyInto(ids);
        return ids;
    }


    /**
     * 获取当天偏移几天的日期，负数为前几天
     * @param delay 偏移天数
     * @return
     */
    public static Date nextDay(int delay){
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(delay);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static Timestamp nowTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     *  根据时间差获取时间戳
     * @param delay 负数表示当前时间之前
     * @return
     */
    public static Timestamp getDelayTimeStamp(long delay){
        return new Timestamp(System.currentTimeMillis() + delay);
    }

    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getCurrDay() {
        return LocalDateTime.now().format(FORMATTER_SIMPLE_SHORT);
    }

    /*
    public static void main(String [] str){
       System.out.println(getCurrDay());
    }
    */

}

