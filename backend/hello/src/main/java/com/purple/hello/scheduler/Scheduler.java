package com.purple.hello.scheduler;

import com.purple.hello.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {
    private final NotificationService notificationService;
    public Scheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // @Scheduled(cron = "8,9,10 * * * * ?")
    @Scheduled(cron = "0 0 8,9,10 * * ?")
    public void createQuestionAlarm() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("#" + localDateTime.getSecond() + " CreateQuestionAlarm : " + simpleDateFormat.format(new Date()));
        if(localDateTime.getSecond() == 8){
            List<String> tokenList = new ArrayList();
            tokenList.add("cdSCzwtUTLWm2LQ0LyvCqT:APA91bE0XQcEc43b_KkvpCfNCqpKxtIS-V1MuVIf1lbg4EtE0G0NZYHmIgZplHNiR1QOK7whmqWHUw6C15qX7QmTmsOACvGXnpt5EcNarV54S8-Pc8e0bjLqC23H1tI5Hu3pr0xM1OBP");
            notificationService.sendCommonNotifications(tokenList, localDateTime.getSecond() + "시 알람일까요?.", "피곤할지도");
        }
    }

    // @Scheduled(cron = "19,20,21 * * * * ?")
    @Scheduled(cron = "0 0 19,20,21 * * ?")
    public void remindQuestionAlarm() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("#" + localDateTime.getSecond() + " RemindQuestionAlarm : " + simpleDateFormat.format(new Date()));
    }
}
