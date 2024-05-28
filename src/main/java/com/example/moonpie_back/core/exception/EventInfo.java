package com.example.moonpie_back.core.exception;

import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

public interface EventInfo {
    HttpStatus getStatus();
    Level getLevel();
}
