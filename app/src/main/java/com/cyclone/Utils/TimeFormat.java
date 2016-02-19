package com.cyclone.Utils;

import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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

    public static String toMilSec(String timestamp) {
        if (timestamp.length() < 19) return "00";
        return timestamp.substring(20, 23);
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
        String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date());
        String now = "" + new Timestamp(date.getTime());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> now : " + now);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> server : " + timestamp);


        String timeStampGMT = toGMT(timestamp);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> timeStampGMT : " + timeStampGMT);
        System.out.println(currDate);
        System.out.println(toMinutes(now) + "-" + toMinutes(timeStampGMT) + " = " + count(toMinutes(now), toMinutes(timeStampGMT)));

        if (count(toYear(now), toYear(timeStampGMT)) != 0) {
            return count(toYear(now), toYear(timeStampGMT)) + " " + YEAR_TAG;
        } else if (count(toMounth(now), toMounth(timeStampGMT)) != 0) {
            return toDay(timeStampGMT) + " " + toName(Integer.valueOf(toMounth(timeStampGMT)));
        } else if (count(toDay(now), toDay(timeStampGMT)) != 0) {
            return count(toDay(now), toDay(timeStampGMT)) + " " + DAY_TAG;
        } else if (count(toHours(now), toHours(timeStampGMT)) != 0) {
            return count(toHours(now), toHours(timeStampGMT)) + " " + HOURS_TAG;
        } else if (count(toMinutes(now), toMinutes(timeStampGMT)) != 0) {
            return count(toMinutes(now), toMinutes(timeStampGMT)) + " " + MINUTE_TAG;
        } else {
            return count(toSec(now), toSec(timeStampGMT)) + " " + SECOND_TAG;
        }

    }

    public static String toTimestamp(String year, String mounth, String day, String hours, String minutes, String secound, String milsec) {
        return year + "-" + mounth + "-" + day + " " + hours + ":" + minutes + ":" + secound + "." + milsec + " EST";
    }

    //2016-02-19 15:50:00 GMT+07:00
    public static String getGMT(String timestamp) {
        String ts = timestamp.substring(23, 29);

        System.out.println(" GMTTT : " + ts);
        return ts;
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

    private static String toGMT(String timestamp) {
        /*String simbol = gmt.substring(0,1);
        int gmtJam = Integer.parseInt(gmt.substring(1, 3));
        int gmtMenit = Integer.parseInt(gmt.substring(4,6));
        int menit = Integer.parseInt(toMinutes(timestamp));
        int jam = Integer.parseInt(toHours(timestamp));
        int tanggal = Integer.parseInt(toDay(timestamp));
        int bulan = Integer.parseInt(toMounth(timestamp));
        int tahun = Integer.parseInt(toYear(timestamp));

        if(gmtMenit != 0){
            menit += gmtMenit;
            if(menit > 59){
                jam += 1;
                menit = 0;
                if(jam > 23){
                    tanggal += 1;
                    jam = 0;
                }
            }
        }*/

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
        String servertime = toYear(timestamp) + "-" + toMounth(timestamp) + "-" + toDay(timestamp) + " " + toHours_HH_MM(timestamp, ":") + ":" + toSec(timestamp) + "." + toMilSec(timestamp) + " GMT";
        System.out.println("SERVERTIME : " + servertime);
        Date date = null;
        long epoch = 0;
        long epochServer = 0;

        try {
            date = df.parse(df.format(new Date()));
            epoch = date.getTime();
            epochServer = df.parse(servertime).getTime();

            Log.d("Time epoch Server", "" + epochServer);
            Log.d("Time epoch Current", "" + epoch);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calGMT(epochServer);
    }

    public static int getCurrentTimezoneOffset() {

        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
        System.out.println("timeMillis : " + offsetInMillis);

        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        System.out.println("GMT" + (offsetInMillis >= 0 ? "+" : "-") + offset);

        return offsetInMillis;
    }

    public static String calGMT(long serverTime) {
        long offsetInMillis = getCurrentTimezoneOffset();
        long server = serverTime;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
        String res = df.format(new Date(server));
        System.out.println("hasil date : " + res);
        return res;
    }

}
