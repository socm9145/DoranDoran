package com.purple.hello.config;

import org.python.util.PythonInterpreter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JythonConfig {
    @Bean
    public PythonInterpreter pythonInterpreter(){
        System.setProperty("python.import.site", "false");
        return new PythonInterpreter();
    }
}
