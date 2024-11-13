package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.AdminRegistrationDto;
import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.core.entity.Authority;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.enums.AuthorityName;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.AuthorityRepository;
import com.example.moonpie_back.core.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final ClientService clientService;

    private final PasswordEncoder passwordService;

    private final AuthorityRepository authorityRepository;

    private final ClientRepository clientRepository;


    public void registerNewClient(ClientRegistrationDto clientRegistrationDto) {

        Optional<Client> clientById = clientRepository.findClientByEmail(clientRegistrationDto.email());

        if (clientById.isPresent()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_EMAIL_ALREADY_EXISTS,
                    "User with this id does not exist");
        }

        String encodedPassword = passwordService.encode(clientRegistrationDto.password());
        Client client = Client.builder()
                .firstName(clientRegistrationDto.firstName())
                .authorities(Collections.singleton(authorityRepository.findAuthorityByName(AuthorityName.USER)))
                .lastName(clientRegistrationDto.lastName())
                .middleName(clientRegistrationDto.middleName())
                .password(encodedPassword)
                .email(clientRegistrationDto.email())
                .build();

        clientService.save(client);
    }

    public void registerNewEmployeeOrAdmin(AdminRegistrationDto adminRegistrationDto) {
        clientService.getClientByEmail(adminRegistrationDto.email());

        Authority authority = authorityRepository.findAuthorityByName(adminRegistrationDto.authorityName());

        String encodedPassword = passwordService.encode(adminRegistrationDto.password());
        Client client = Client.builder()
                .firstName(adminRegistrationDto.firstName())
                .lastName(adminRegistrationDto.lastName())
                .middleName(adminRegistrationDto.middleName())
                .password(encodedPassword)
                .email(adminRegistrationDto.email())
                .authorities(Collections.singleton(authority))
                .build();

        clientService.save(client);
    }
}
