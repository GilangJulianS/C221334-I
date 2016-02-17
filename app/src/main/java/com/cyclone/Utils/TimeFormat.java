package com.cyclone.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by solusi247 on 15/02/16.
 */
//2016-02-16T06:29:10.366Z
public class TimeFormat {

    private static String YEAR_TAG = "year ago";
    private static String SECOND_TAG = "sec ago";
    private static String DAY_TAG = "day ago";
    private static String HOURS_TAG = "hours ago";
    private static String MINUTE_TAG = "min ago";

    private static String JANUARI = "Jan";
    private static String FEBRUARI = "Feb";
    private static String MARET = "Mar";
    private static String APRIL = "Apr";
    private static String MEI = "May";
    private static String JUNI = "Jun";
    private static String JULI = "Jul";
    private static String AGUSTUS = "Aug";
    private static String SEPTEMBER = "Sep";
    private static String OKTOBER = "Oct";
    private static String NOVEMBER = "Nov";
    private static String DESEMBER = "Dec";

    public static void setYearTag(String yearTag) {
        YEAR_TAG = yearTag;
    }

    public static void setSecondTag(String secondTag) {
        SECOND_TAG = secondTag;
    }

    public static void setMinuteTag(String minuteTag) {
        MINUTE_TAG = minuteTag;
    }

    public static String toMounth(String timestamp) {
        if (timestamp.length() < 7) return "00";
        return timestamp.substring(5, 7);
    }

    public static String toDay(String timestamp) {
        if (timestamp.length() < 10) return "00";
        return timestamp.substring(8, 10);
    }

    public static String toYear(String timestamp) {
        if (timestamp.length() < 4) return "00";
        return timestamp.substring(0, 4);
    }

    public static String toHours(String timestamp) {
        if (timestamp.length() < 13) return "00";
        return timestamp.substring(11, 13);
    }

    public static String toMinutes(String timestamp) {
        if (timestamp.length() < 16) return "00";
        return timestamp.substring(14, 16);
    }

    public static String toSec(String timestamp) {
        if (timestamp.length() < 19) return "00";
        return timestamp.substring(17, 19);
    }

    public static String toDate(String timestamp, String dividing) {
        if (timestamp.length() < 19) return timestamp;

        return toDay(timestamp) + dividing + toMounth(timestamp) + dividing + toYear(timestamp);
    }

    public static String tofullHours(String timestamp, String dividing) {
        if (timestamp.length() < 19) return timestamp;

        return toHours(timestamp) + dividing + toMinutes(timestamp) + dividing + toSec(timestamp);
    }

    public static String toHours_HH_MM(String timestamp, String dividing) {
        if (timestamp.length() < 19) return timestamp;

        return toHours(timestamp) + dividing + toMinutes(timestamp);
    }

    public static String toDateHours(String timestamp) {
        if (timestamp.length() < 19) return timestamp;

        return toDate(timestamp, ".") + " - " + toHours_HH_MM(timestamp, ":");
    }

    public static String toThisTime(String timestamp) {
        Date date = new Date();
        String currDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
        String now = "" + new Timestamp(date.getTime());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + now);
        System.out.println(currDate);
        if (count(toYear(now), toYear(timestamp)) != 0) {
            return count(toYear(now), toYear(timestamp)) + " " + YEAR_TAG;
        } else if (count(toMounth(now), toMounth(timestamp)) != 0) {
            return toDay(timestamp) + " " + toName(Integer.valueOf(toMounth(timestamp)));
        } else if (count(toDay(now), toDay(timestamp)) != 0) {
            return count(toDay(now), toDay(timestamp)) + " " + DAY_TAG;
        } else if (count(toHours(now), toHours(timestamp)) != 0) {
            return count(toHours(now), toHours(timestamp)) + " " + HOURS_TAG;
        } else if (count(toMinutes(now), toMinutes(timestamp)) != 0) {
            return count(toMinutes(now), toMinutes(timestamp)) + " " + MINUTE_TAG;
        } else {
            return count(toSec(now), toSec(timestamp)) + " " + SECOND_TAG;
        }

    }

    private static int count(String now, String target) {
        int Yn = Integer.parseInt(now);
        int Yt = Integer.parseInt(target);
        int res = Yn - Yt;
        return res;
    }

    private static String toName(int bulan) {
        if (bulan == 1) return JANUARI;
        else if (bulan == 2) return FEBRUARI;
        else if (bulan == 3) return MARET;
        else if (bulan == 4) return APRIL;
        else if (bulan == 5) return MEI;
        else if (bulan == 6) return JUNI;
        else if (bulan == 7) return JULI;
        else if (bulan == 8) return AGUSTUS;
        else if (bulan == 9) return SEPTEMBER;
        else if (bulan == 10) return OKTOBER;
        else if (bulan == 11) return NOVEMBER;
        else if (bulan == 12) return DESEMBER;

        return null;
    }

}
