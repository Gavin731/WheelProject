package com.rzm.socialsecurity.util;

import java.util.Calendar;

public class DateUtil {
    /**
     * 使用 Calendar 计算月份差
     */
    public static int getMonthDifferenceWithCalendar(int startYear, int startMonth, int endYear, int endMonth) {
        try {
            return (endYear - startYear) * 12 + (endMonth - startMonth);
        } catch (Exception e) {
            throw new IllegalArgumentException("日期格式错误", e);
        }
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
}
