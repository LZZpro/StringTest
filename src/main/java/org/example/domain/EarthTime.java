package org.example.domain;

import java.time.LocalDateTime;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/29 19:52
 */
public class EarthTime {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    private static final int[] DAYS_IN_MONTH = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public EarthTime(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        normalize();
    }

    public EarthTime plusSeconds(long seconds) {
        this.second += seconds;
        normalize();
        return this;
    }

    public EarthTime minusSeconds(long seconds) {
        this.second -= seconds;
        normalize();
        return this;
    }

    private void normalize() {
        while (this.second >= 60) {
            this.second -= 60;
            this.minute += 1;
        }
        while (this.second < 0) {
            this.second += 60;
            this.minute -= 1;
        }
        while (this.minute >= 60) {
            this.minute -= 60;
            this.hour += 1;
        }
        while (this.minute < 0) {
            this.minute += 60;
            this.hour -= 1;
        }
        while (this.hour >= 24) {
            this.hour -= 24;
            this.day += 1;
        }
        while (this.hour < 0) {
            this.hour += 24;
            this.day -= 1;
        }
        while (this.day > DAYS_IN_MONTH[this.month - 1] || (this.month == 2 && isLeapYear(this.year) && this.day > 29)) {
            if (this.month == 2 && isLeapYear(this.year) && this.day > 29) {
                this.day -= 29;
            } else {
                this.day -= DAYS_IN_MONTH[this.month - 1];
            }
            this.month += 1;
            if (this.month > 12) {
                this.month = 1;
                this.year += 1;
            }
        }
        while (this.day <= 0) {
            this.month -= 1;
            if (this.month <= 0) {
                this.month = 12;
                this.year -= 1;
            }
            if (this.month == 2 && isLeapYear(this.year)) {
                this.day += 29;
            } else {
                this.day += DAYS_IN_MONTH[this.month - 1];
            }
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static EarthTime localTime2EarthTime(LocalDateTime time)
    {
        return new EarthTime(time.getYear(),time.getMonth().getValue(),time.getDayOfMonth(),
                time.getHour(),time.getMinute(),time.getSecond());
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }
}
