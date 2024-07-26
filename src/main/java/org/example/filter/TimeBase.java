package org.example.filter;

import org.example.domain.AlienTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public static AlienTime baseAlienTime;
    TimeBase()
    {
        baseAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88);
        // 计算从基准地球时间到当前时间的秒数
        long earthSeconds = baseEarthTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        currentAlienTime = baseAlienTime.plusSeconds(earthSeconds * 2);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //延时任务，并循环执行: 执行第一次的延迟时间1s，连续执行的间隔时间1s
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                ()->currentAlienTime.plusSeconds(1),1, 1,TimeUnit.SECONDS
        );
    }
}
