package com.purple.hello.service;

import org.springframework.stereotype.Service;

@Service
public interface FileService {
    boolean compareExtensions(String fileExtensions, String[] imageExtensions);
    String getExtension(String fileName);
}
