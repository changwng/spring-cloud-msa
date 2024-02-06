package com.covenant.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledTasks {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss:SSS");

    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() throws InterruptedException {
        log.info("시작시간 - {}", formatter.format(LocalDateTime.now()));
        TimeUnit.SECONDS.sleep(5);
        log.info("종료시간 - {}", formatter.format(LocalDateTime.now()));
        Integer.parseInt("3");
    }

    @Scheduled(cron = "0 0 7 * * MON")
    public void cronExpression() {
        log.info("현재시간 - {}", formatter.format(LocalDateTime.now()));
    }
}
