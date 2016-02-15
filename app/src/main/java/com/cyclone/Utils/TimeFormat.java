package com.cyclone.Utils;

/**
 * Created by solusi247 on 15/02/16.
 */
//2016(4)02(7)05(10)
// 05(13)52(16)42(19)741Z
public class TimeFormat {

    public static String toMounth(String timestamp) {
        return timestamp.substring(5, 7);
    }

    public static String toDay(String timestamp) {
        return timestamp.substring(8, 10);
    }

    public static String toYear(String timestamp) {
        return timestamp.substring(0, 4);
    }

    public static String toHours(String timestamp) {
        return timestamp.substring(11, 13);
    }

    public static String toMinutes(String timestamp) {
        return timestamp.substring(14, 16);
    }

    public static String toSec(String timestamp) {
        return timestamp.substring(17, 19);
    }

    public static String toDate(String timestamp, String dividing) {
        return toDay(timestamp) + dividing + toMounth(timestamp) + dividing + toYear(timestamp);
    }

    public static String tofullHours(String timestamp, String dividing) {
        return toHours(timestamp) + dividing + toMinutes(timestamp) + dividing + toSec(timestamp);
    }

    public static String toHours_HH_MM(String timestamp, String dividing) {
        return toHours(timestamp) + dividing + toMinutes(timestamp);
    }

    public static String toDateHours(String timestamp) {
        return toDate(timestamp, ".") + " - " + toHours_HH_MM(timestamp, ":");
    }
}
