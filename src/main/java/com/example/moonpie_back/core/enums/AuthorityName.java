package com.example.moonpie_back.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityName {

    USER,

    EMPLOYEE,

    ADMIN;

    @Override
    public String toString() {
        if (this.equals(USER)) {
            return "USER";
        } else if (this.equals(EMPLOYEE)) {
            return "EMPLOYEE";
        } else {
            return "ADMIN";
        }
    }
}
