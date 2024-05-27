package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final PasswordService passwordService;

    private final JwtService jwtService;

    public void registerNewClient(ClientRegistrationDto clientRegistrationDto) {
        String encodedPassword = passwordService.encodePassword(clientRegistrationDto.password());
        Client client = Client.builder()
                .name(clientRegistrationDto.name())
                .password(encodedPassword)
                .email(clientRegistrationDto.email())
                .build();
        clientRepository.save(client);
    }

    public String auth(String email, String password) {
        Client client = clientRepository.findClientByEmail(email);

        if(!passwordService.checkPassword(password, client.getPassword())) {
            throw new RuntimeException("Password not correct");
        }

        return jwtService.generateToken(client.getId().toString());
    }
}
