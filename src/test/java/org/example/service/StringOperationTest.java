package org.example.service;

import org.example.StringTestApplication;
import org.example.domain.AlienTime;
import org.example.domain.EarthTime;
import org.example.filter.TimeBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.annotation.security.RunAs;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/10 22:45
 */
@SpringBootTest
public class StringOperationTest {

    public static AlienTime baseAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88);

    @Resource
    IStringOperation stringOperation;

    @Test
    public void function1Test()
    {
        String str = "sdgdfhhghhhhnnnbb";
        System.out.println("Input: " + str);
        System.out.println("Output:\t");
        String result = stringOperation.deleteConsecutiveCharactersMoreThen3(str);
        System.out.println("result:"+result);
    }

    @Test
    public void function2Test()
    {
        String str = "abcccbad";
        System.out.println("Input: " + str);
        System.out.println("Output:\t");
        String result = stringOperation.replaceConsecutiveByPreAlpha(str);
        System.out.println("result:"+result);
    }

    @Test
    public void validateAlienTime()
    {
        String time = "2810-06-32 21:82:70";
        //AlienTime parse = AlienTime.string2AlienTime(time);
//        AlienTime parse = new AlienTime(2810,06,32,21,82,70);
//        boolean b = compareIsBig(parse);
//        System.out.println(b);
        int sum = 44+42+48+40+48+44+40+44+42+40+40+42+44+48+42+40+44+38;
        System.out.println("sum:"+sum);
        AlienTime ct = AlienTime.string2AlienTime(time);
        //指定时间到基准外星时间经历过的总秒速
        long differenceInSecond = AlienTime.getDifferenceInSecond(ct);
        EarthTime et = EarthTime.localTime2EarthTime(TimeBase.baseEarthTime.plusSeconds(differenceInSecond / 2));
        //Current Earth Time: 163957-03-14 23:09:47
        System.out.println(et);
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now.format(fmt));
//        System.out.println("地球时间类："+ EarthTime.localTime2EarthTime(now));

    }

    public boolean compareIsBig(AlienTime time)
    {
        if (baseAlienTime.getYear() < time.getYear())
        {
            return true;
        }else if (baseAlienTime.getYear() == time.getYear())
        {
            if (baseAlienTime.getMonth() < time.getMonth())
            {
                return true;
            }else if (baseAlienTime.getMonth() == time.getMonth())
            {
                  if (baseAlienTime.getDay() < time.getDay())
                  {
                      return true;
                  }else if (baseAlienTime.getDay() == time.getDay())
                  {
                       if (baseAlienTime.getHour() < time.getHour())
                       {
                           return true;
                       }else if (baseAlienTime.getHour() == time.getHour())
                       {
                           if (baseAlienTime.getMinute() < time.getMinute())
                           {
                               return true;
                           }else if (baseAlienTime.getMinute() == time.getMinute())
                           {
                               return baseAlienTime.getSecond() < time.getSecond();
                           }else {
                               return false;
                           }
                       }else {
                           return false;
                       }
                  }else {
                      return false;
                  }
            }else {
                return false;
            }
        }
        return false ;
    }
}
