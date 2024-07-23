package org.example.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/22 17:31
 */
public class AlienTime {

    private static int year;
    private static int month;
    private static int day;
    private static int hour;
    private static int minute;
    private static long second;

    private static final int[] DAYS_IN_MONTH = {
            44, 42, 48, 40, 48, 44, 40, 44, 42, 40, 40, 42, 44, 48, 42, 40, 44, 38
    };

    public AlienTime(int year, int month, int day, int hour, int minute, long second) {
        AlienTime.year = year;
        AlienTime.month = month;
        AlienTime.day = day;
        AlienTime.hour = hour;
        AlienTime.minute = minute;
        AlienTime.second = second;
    }

    public AlienTime plusSeconds(long seconds) {
        AlienTime.second += seconds;
        normalize();
        return this;
    }

    public AlienTime minusSeconds(long seconds) {
        AlienTime.second -= seconds;
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

    public static AlienTime parse(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return new AlienTime(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
    }
}
