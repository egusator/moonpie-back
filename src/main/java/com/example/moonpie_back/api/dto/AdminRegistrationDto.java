package com.example.moonpie_back.api.dto;

import com.example.moonpie_back.core.enums.AuthorityName;

public record AdminRegistrationDto(

        String firstName,

        String lastName,

        String middleName,

        String email,

        String password,

        AuthorityName authorityName
)  {


}
