package org.example.filter;

import org.example.domain.AlienTime;
import org.example.domain.EarthTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author：Linzz
 * @Describe: 外星时间配置
 * @Date：2024/7/26 23:53
 */
@Component
public class TimeBase implements InitializingBean {

    public static final LocalDateTime baseEarthTime = LocalDateTime
            .of(1970, 1, 1, 0, 0, 0);
    public static AlienTime  currentAlienTime;
    public static final AlienTime baseAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88);
    public static EarthTime currentEarthTime;
    public static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    TimeBase()
    {
       // baseAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88);
        // 计算从基准地球时间到当前时间的秒数
        long earthSeconds = baseEarthTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        currentAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88).plusSeconds(earthSeconds * 2);
    }

    /**
     * 比较时间大小，入参大就返回true,否则false
     * @param time
     * @return
     */
    public static boolean compareIsBig(AlienTime time)
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


    @Override
    public void afterPropertiesSet() throws Exception {
        currentEarthTime = EarthTime.localTime2EarthTime(LocalDateTime.now());
        //延时任务，并循环执行: 执行第一次的延迟时间1s，连续执行的间隔时间1s,外星时间+2s,地球1s=外星2s
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                ()-> {
                    currentAlienTime.plusSeconds(2);
                    currentEarthTime.plusSeconds(1);
                }, 1, 1,TimeUnit.SECONDS
        );
    }
}
