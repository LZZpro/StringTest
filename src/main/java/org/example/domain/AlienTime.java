package org.example.domain;

import org.example.exception.AlienTimeException;
import org.example.filter.TimeBase;
import org.springframework.util.StringUtils;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/22 17:31
 */
public class AlienTime {

    private  int year;
    private  int month;
    private  int day;
    private  int hour;
    private  int minute;
    private  long second;
    //基准时间换算成的秒数
    private static final long baseAlienTimeTotalSecond =11332579104000L;

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public  int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public long getSecond() {
        return this.second;
    }

    private static final int[] DAYS_IN_MONTH = {
            44, 42, 48, 40, 48, 44, 40, 44, 42, 40, 40, 42, 44, 48, 42, 40, 44, 38
    };

    public AlienTime(int year, int month, int day, int hour, int minute, long second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public AlienTime plusSeconds(long seconds) {
        this.second += seconds;
        normalize();
        return this;
    }

    public AlienTime minusSeconds(long seconds) {
        this.second -= seconds;
        normalize();
        return this;
    }

    private void normalize()
    {
        while (month > 18)
        {
            month -= 18;
            year += 1;
        }
        while (month <= 0)
        {
            month += 18;
            year -= 1;
        }

        while (second >= 90)
        {
            second -= 90;
            minute += 1;
        }
        while (second < 0)
        {
            second += 90;
            minute -= 1;
        }
        while (minute >= 90)
        {
            minute -= 90;
            hour += 1;
        }
        while (minute < 0)
        {
            minute += 90;
            hour -= 1;
        }
        while (hour >= 36)
        {
            hour -= 36;
            day += 1;
        }
        while (hour < 0)
        {
            hour += 36;
            day -= 1;
        }
        while (day > DAYS_IN_MONTH[month - 1])
        {
            day -= DAYS_IN_MONTH[month - 1];
            month += 1;
            if (month > 18) {
                month = 1;
                year += 1;
            }
        }
        while (day <= 0)
        {
            month -= 1;
            if (month <= 0) {
                month = 18;
                year -= 1;
            }
            day += DAYS_IN_MONTH[month - 1];
        }
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    //2820-06-32 12:70:60
    public static AlienTime string2AlienTime(String time)
    {
       if (!StringUtils.hasText(time))
           throw new AlienTimeException("传入时间字符串不能为空！格式为: yyyy-mm-dd hh:mm:ss");
        String[] timeArr = time.trim().split(" ");
       if (timeArr.length!=2)
           throw new AlienTimeException("传入时间格式不正确！正确格式为: yyyy-mm-dd hh:mm:ss");

       String[] preArr = timeArr[0].split("-");
       String[] sufArr = timeArr[1].split(":");
       if (preArr.length!=3 || sufArr.length!=3)
           throw new AlienTimeException("传入时间格式不正确！正确格式为: yyyy-mm-dd hh:mm:ss");

       //检查空串
       if (isNullString(preArr) && isNullString(sufArr))
           throw new AlienTimeException("传入时间格式不正确！正确格式为: yyyy-mm-dd hh:mm:ss");

       int year = Integer.parseInt(preArr[0]);
       int month = Integer.parseInt(preArr[1]);
       int day = Integer.parseInt(preArr[2]);
       int hour = Integer.parseInt(sufArr[0]);
       int minute = Integer.parseInt(sufArr[1]);
       int second = Integer.parseInt(sufArr[2]);
       AlienTime newTime = new AlienTime(year,month,day,hour,minute,second);
        if (!isValid(newTime))
            throw new AlienTimeException("传入时间范围不正确！请查证外星时间范围");
        if (!TimeBase.compareIsBig(newTime))
            throw new AlienTimeException("不能设置时间比基准时间小，基准时间为：2804-18-31 2:2:88");

       return newTime;
    }

    //检查空串""
    private static boolean isNullString(String[] strings)
    {
        for (String s: strings)
        {
            if (!StringUtils.hasText(s))
            {
               return true;
            }
        }
        return false;
    }

    //正确返回true
    public static boolean isValid(AlienTime alienTime) {
        if (alienTime.year <= 0) return false;
        if (alienTime.month < 1 || alienTime.month > 18) return false;
        if (alienTime.day < 1 || alienTime.day > DAYS_IN_MONTH[alienTime.month - 1]) return false;
        if (alienTime.hour < 0 || alienTime.hour >= 36) return false;
        if (alienTime.minute < 0 || alienTime.minute >= 90) return false;
        if (alienTime.second < 0 || alienTime.second >= 90) return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass()!=obj.getClass())
            return false;
        AlienTime inAlienTime = (AlienTime)obj;
       if (year == inAlienTime.getYear() && month == inAlienTime.getMonth()
           && day == inAlienTime.getDay() && hour == inAlienTime.getHour()
           && minute == inAlienTime.getMinute() && second == inAlienTime.getSecond())
           return true;
       return false;
    }

    private static long getTotalSeconds(AlienTime time) {
        long totalSeconds = 0;
        // Convert years to seconds
        totalSeconds += (long) time.getYear() * 18 * 770 * 36 * 90 * 90;
        // Convert months to seconds
        for (int i = 0; i < time.getMonth() - 1; i++) {
            totalSeconds += DAYS_IN_MONTH[i] * 36 * 90 * 90;
        }
        // Convert days to seconds
        totalSeconds += (long) (time.day - 1) * 36 * 90 * 90;

        // Convert hours, minutes and seconds
        totalSeconds += (long) time.hour * 90 * 90;
        totalSeconds += time.minute * 90L;
        totalSeconds += time.second;
        return totalSeconds;
    }

    /**
     * 指定时间 - 基准时间 = 总秒数
     * @param time
     * @return
     */
    public static long getDifferenceInSecond(AlienTime time)
    {
        return getTotalSeconds(time) - baseAlienTimeTotalSecond ;
    }

}
