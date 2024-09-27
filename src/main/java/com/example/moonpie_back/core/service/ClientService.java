package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.entity.Color;
import com.example.moonpie_back.core.entity.Item;
import com.example.moonpie_back.core.entity.Size;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final PasswordService passwordService;

    private final JwtService jwtService;

    public void registerNewClient(ClientRegistrationDto clientRegistrationDto) {
        Optional<Client> savedClientByEmail = Optional.ofNullable(
                clientRepository.findClientByEmail(clientRegistrationDto.email()));

        if (savedClientByEmail.isPresent()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_EMAIL_ALREADY_EXISTS,
                    "User with the email you specified already exists");
        }

        String encodedPassword = passwordService.encodePassword(clientRegistrationDto.password());
        Client client = Client.builder()
                .name(clientRegistrationDto.name())
                .password(encodedPassword)
                .email(clientRegistrationDto.email())
                .build();
        clientRepository.save(client);
    }

    public String auth(String email, String password) {
        Client client = Optional.ofNullable(
                clientRepository.findClientByEmail(email)).orElseThrow(() ->
                new BusinessException(UserAuthEvent.USER_WITH_THIS_EMAIL_IS_NOT_REGISTERED,
                        "User with this email is not registered yet")
        );

        if (!passwordService.checkPassword(password, client.getPassword())) {
            throw new BusinessException(UserAuthEvent.USER_PASSWORD_IS_NOT_CORRECT,
                    "Password not correct");
        }

        return jwtService.generateToken(client.getId().toString());
    }


}
