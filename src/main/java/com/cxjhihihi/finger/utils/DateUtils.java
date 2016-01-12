package com.cxjhihihi.finger.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.netease.vipnew.common.util.MathUtils;

/**
 * @author hzcaixinjia
 */
public class DateUtils {

    public static java.util.Date stringToDate(String strDate, String pattern) {
        if (strDate == null || strDate.trim().length() <= 0)
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(java.util.Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date dateAddMinutes(Date date, int minutes) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static Date dateAddHours(Date date, int hours) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }

    public static Date dateAddDays(Date date, int addDays) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, addDays);
        return cal.getTime();
    }

    public static Date dateAddMonths(Date date, int addMonths) {
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int oldDaysOfMonth = cal.get(Calendar.DATE);
        double oldMonth = MathUtils.div(oldDaysOfMonth, getDaysOfMonth(date));

        cal.add(Calendar.MONTH, addMonths);

        Date date2 = cal.getTime();
        int newDays = (int) (oldMonth * getDaysOfMonth(date2));
        cal.set(Calendar.DATE, newDays);
        return cal.getTime();
    }

    public static Date dateAddMonths(Date date, double months) {
        if (date == null)
            return null;
        int addMonths = (int) Math.floor(months);
        double decmonth = MathUtils.sub(months, addMonths);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int oldDaysOfMonth = cal.get(Calendar.DATE);
        double oldMonth = MathUtils.div(oldDaysOfMonth, getDaysOfMonth(date));
        //1.
        cal.add(Calendar.MONTH, addMonths);
        Date date2 = cal.getTime();
        //ä¸è·¨æ?
        double totalMonth = MathUtils.add(decmonth, oldMonth);
        if (totalMonth <= 1.0161) {
            int newDays = MathUtils.round(totalMonth * getDaysOfMonth(date2));
            if (newDays > 0)
                cal.set(Calendar.DATE, newDays);
        } else {
            //è·¨æ
            cal.add(Calendar.MONTH, 1);
            Date date3 = cal.getTime();
            int newDays = MathUtils.round(MathUtils.sub(totalMonth, 1)
                * getDaysOfMonth(date3));
            if (newDays == 0)
                newDays = 1;
            cal.set(Calendar.DATE, newDays);
        }

        return cal.getTime();
    }

    public static Date dateAddYears(Date date, int addYears) {
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, addYears);
        return cal.getTime();
    }

    public static boolean isDate(String strDate, String pattern) {
        if (strDate == null || strDate.trim().length() <= 0)
            return false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.parse(strDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static int getIntervalOfDay(Date d1, Date d2) {
        int elapsed = 0;
        boolean isAfter = false;
        Date temp;
        if (d1.after(d2)) {
            temp = d1;
            d1 = d2;
            d2 = temp;
            isAfter = true;
        }

        while (d1.before(d2)) {
            d1 = dateAddDays(d1, 1);
            elapsed++;
        }
        if (isAfter)
            elapsed = 0 - elapsed;
        return elapsed;
    }

    /**
     * Elapsed days based on current time
     */
    public static int getElapsedDays(Date date) {
        return elapsed(date, Calendar.DATE);
    }

    /**
     * Elapsed days based on two Date objects
     */
    public static int getElapsedDays(Date d1, Date d2) {
        return elapsed(d1, d2, Calendar.DATE);
    }

    /**
     * Elapsed months based on current time
     */
    public static int getElapsedMonths(Date date) {
        return elapsed(date, Calendar.MONTH);
    }

    /**
     * Elapsed months based on two Date objects
     */
    public static int getElapsedMonths(Date d1, Date d2) {
        return elapsed(d1, d2, Calendar.MONTH);
    }

    /**
     * Elapsed years based on current time
     */
    public static int getElapsedYears(Date date) {
        return elapsed(date, Calendar.YEAR);
    }

    /**
     * Elapsed years based on two Date objects
     */
    public static int getElapsedYears(Date d1, Date d2) {
        return elapsed(d1, d2, Calendar.YEAR);
    }

    /**
     * All elaspsed types
     * 
     * @param g1
     *            GregorianCalendar
     * @param g2
     *            GregorianCalendar
     * @param type
     *            int (Calendar.FIELD_NAME)
     * @return int number of elapsed "type"
     */
    private static int elapsed(GregorianCalendar g1, GregorianCalendar g2,
        int type) {
        GregorianCalendar gc1, gc2;
        int elapsed = 0;

        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        if (type == Calendar.MONTH || type == Calendar.YEAR) {
            gc1.clear(Calendar.DATE);
            gc2.clear(Calendar.DATE);
        }
        if (type == Calendar.YEAR) {
            gc1.clear(Calendar.MONTH);
            gc2.clear(Calendar.MONTH);
        }
        while (gc1.before(gc2)) {
            gc1.add(type, 1);
            elapsed++;
        }
        return elapsed;
    }

    /**
     * All elaspsed types based on date and current Date
     * 
     * @param date
     *            Date
     * @param type
     *            int (Calendar.FIELD_NAME)
     * @return int number of elapsed "type"
     */
    private static int elapsed(Date date, int type) {
        return elapsed(date, new Date(), type);
    }

    /**
     * All elaspsed types
     * 
     * @param d1
     *            Date
     * @param d2
     *            Date
     * @param type
     *            int (Calendar.FIELD_NAME)
     * @return int number of elapsed "type"
     */
    private static int elapsed(Date d1, Date d2, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        cal.setTime(d2);
        GregorianCalendar g2 = new GregorianCalendar(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return elapsed(g1, g2, type);
    }

    /**
     * å¾å°æå®æä»½çå¤©æ?
     */
    public static int getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getMonthDateCount(cal);
    }

    /**
     * å¾å°æå®æä»½çå¤©æ?
     */
    protected static int getMonthDateCount(Calendar cal) {
        Calendar cal2 = (Calendar) cal.clone();
        cal2.add(Calendar.MONTH, 1);
        cal2.set(Calendar.DAY_OF_MONTH, 1);
        cal2.add(Calendar.DAY_OF_MONTH, -1);
        return cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(dateToString(calendar.getTime(), "yyyy/MM/dd"));
        System.out.println(dateToString(calendar.getTime(), "HH"));
        System.out.println(dateToString(calendar.getTime(), "mm"));
        System.out.println(getElapsedDays(getEndDate(new Date()),
            getZeroDate(new Date())));

        System.out.println();
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            Date d = DateUtils.dateAddDays(now, i);
            int dow = DateUtils.getDayOfWeek(d);
            System.out.println(dow);
        }

    }

    /**
     * è·åæå®æ¥ææ¶é´çé¶æ¶æ¥æ?
     */
    public static Date getZeroDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * è·åæå®æ¥ææ¶é´çæ«æ¶æ¥æ?
     */
    public static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * å¤æ­æ¥æä¸ºææå ,7ä¸ºææå­,1ä¸ºææå¤©ï¼ä¾æ­¤ç±»æ?
     */
    public static int getDayOfWeek(Date date) {
        //é¦åå®ä¹ä¸?¸ªcalendarï¼å¿é¡»ä½¿ç¨getInstance()è¿è¡å®ä¾å?
        Calendar aCalendar = Calendar.getInstance();
        //éé¢éå¯ä»¥ç´æ¥æå¥dateç±»å 
        aCalendar.setTime(date);
        //è®¡ç®æ­¤æ¥ææ¯ä¸?¨ä¸­çåªä¸å¤?
        int x = aCalendar.get(Calendar.DAY_OF_WEEK);
        return x;
    }

    /**
     * è·åæå®æ¥ææ¶é´çå¨åé¶æ¶æ¥æ?
     */
    public static Date getWeekZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = 0 - dayOfWeek + 1;
        calendar.add(Calendar.DATE, dayOfWeek);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * è·åæå®æ¥ææ¶é´çå¨æ«æ«æ¶æ¥æ?
     */
    public static Date getWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, dayOfWeek);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * è·åæå®æ¥ææ¶é´çæåé¶æ¶æ¥æ?
     */
    public static Date getMonthZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * è·åæå®æ¥ææ¶é´çææ«æ«æ¶æ¥æ?
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            getMonthDateCount(calendar), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * å¾å°æå®æ¥æå½æçå¤©æ?
     */
    public static int getMonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);//ææ¥æè®¾ç½®ä¸ºå½æç¬¬ä¸å¤? 
        calendar.roll(Calendar.DATE, -1);//æ¥æåæ»ä¸?¤©ï¼ä¹å°±æ¯æ?ä¸?¤©  
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }
}
