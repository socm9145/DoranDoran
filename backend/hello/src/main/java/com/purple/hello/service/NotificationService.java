package com.purple.hello.service;

import com.purple.hello.dto.tool.NotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    int sendCommonNotifications(List<NotificationDTO> notificationDTOS);
    void sendCommonNotifications(List<String> tokenList, String title, String content);
}
