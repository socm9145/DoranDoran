package com.purple.hello.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    void init();
    void sendCommonNotifications(List<String> tokenList, String title, String content);
}
