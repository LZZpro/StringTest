package org.example.controller;

import org.example.domain.AlienTime;
import org.example.domain.R;
import org.example.filter.TimeBase;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    static Map<String, String> response = new HashMap<>();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //http://localhost:8848/test/api/alien-time
    @GetMapping("/alien-time")
    public Map<String, String> getAlienTime() {
        response.put("alienTime", TimeBase.currentAlienTime.toString());
        response.put("earthTime", LocalDateTime.now().format(fmt));
        return response;
    }

    @PostMapping("/set-alien-time")
    public void setAlienTime(@RequestBody Map<String, String> payload) {
        String userTime = payload.get("userTime");
        //设置外星时间为指定时间
        TimeBase.currentAlienTime = AlienTime.string2AlienTime(userTime);
    }

    //重置外星时钟为当前时间
    @GetMapping("/reset-alien-time")
    public Map<String, String> resetAlienTime()
    {
        long earthSeconds = TimeBase.baseEarthTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        TimeBase.currentAlienTime = new AlienTime(2804, 18, 31, 2, 2, 88)
                .plusSeconds(earthSeconds * 2);
        response.put("alienTime", TimeBase.currentAlienTime.toString());
        return response;
    }

    /**
     * 验证输入的时间是否正确
     * @param payload
     * @return
     */
    @PostMapping("/validateTime")
    public R<?> setAlarm(@RequestBody Map<String, String> payload)
    {
        String alarmTime = payload.get("alarmTime");
        AlienTime alarm = AlienTime.string2AlienTime(alarmTime);
        return R.ok(true);
    }


}
