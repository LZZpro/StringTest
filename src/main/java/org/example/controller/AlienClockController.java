package org.example.controller;

import org.example.domain.AlienTime;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/7/22 15:57
 */
@RestController
@RequestMapping("/api")
public class AlienClockController {

    private static final LocalDateTime baseEarthTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    static AlienTime currentAlienTime;
    static AlienTime baseAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88);
    static
    {
        // 计算从基准地球时间到当前时间的秒数
        long earthSeconds = baseEarthTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        currentAlienTime = baseAlienTime.plusSeconds(earthSeconds * 2);
        currentAlienTime.minusSeconds(1);
    }
    static Map<String, String> response = new HashMap<>();


    //http://localhost:8848/test/api/alien-time
    @GetMapping("/alien-time")
    public Map<String, String> getAlienTime() {

        currentAlienTime.plusSeconds(1);
        response.put("alienTime", currentAlienTime.toString());
        response.put("earthTime", LocalDateTime.now().toString());
        return response;
    }

    @PostMapping("/set-alien-time")
    public void setAlienTime(@RequestBody Map<String, String> payload) {
        String userTime = payload.get("userTime");
        if (!StringUtils.hasText(userTime))
            throw new DateTimeException("传入时间不能为空");
        //设置外星时间为指定时间
        currentAlienTime = AlienTime.parse(userTime);
    }

    @GetMapping("/reset-alien-time")
    public Map<String, String> resetAlienTime()
    {
        baseAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88);
        long earthSeconds = baseEarthTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        currentAlienTime = baseAlienTime.plusSeconds(earthSeconds * 2);
        response.put("alienTime", currentAlienTime.toString());
        return response;
    }


}
