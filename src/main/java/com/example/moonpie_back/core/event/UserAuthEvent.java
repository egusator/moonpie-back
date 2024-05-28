package com.example.moonpie_back.core.event;

import com.example.moonpie_back.core.exception.EventInfo;
import lombok.AllArgsConstructor;
import org.slf4j.event.Level;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum UserAuthEvent implements EventInfo {
    USER_WITH_THIS_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, Level.INFO),
    USER_PASSWORD_IS_NOT_CORRECT(HttpStatus.BAD_REQUEST, Level.INFO),
    USER_WITH_THIS_EMAIL_IS_NOT_REGISTERED(HttpStatus.BAD_REQUEST, Level.INFO);

    private final HttpStatus status;
    private final Level level;
}
