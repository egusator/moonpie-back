package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.controller.UpdateProfileDto;
import com.example.moonpie_back.api.dto.AdminRegistrationDto;
import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.api.dto.ProfileInfoDto;
import com.example.moonpie_back.core.entity.Authority;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.AuthorityRepository;
import com.example.moonpie_back.core.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final AuthorityRepository authorityRepository;

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
                .firstName(clientRegistrationDto.firstName())
                .lastName(clientRegistrationDto.lastName())
                .middleName(clientRegistrationDto.middleName())
                .password(encodedPassword)
                .email(clientRegistrationDto.email())
                .build();
        clientRepository.save(client);
    }

    public void registerNewEmployeeOrAdmin(AdminRegistrationDto adminRegistrationDto) {
        Optional<Client> savedClientByEmail = Optional.ofNullable(
                clientRepository.findClientByEmail(adminRegistrationDto.email()));

        if (savedClientByEmail.isPresent()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_EMAIL_ALREADY_EXISTS,
                    "User with the email you specified already exists");
        }
        Authority authority = authorityRepository.findAuthorityByName(adminRegistrationDto.authorityName());
        String encodedPassword = passwordService.encodePassword(adminRegistrationDto.password());
        Client client = Client.builder()
                .firstName(adminRegistrationDto.firstName())
                .lastName(adminRegistrationDto.lastName())
                .middleName(adminRegistrationDto.middleName())
                .password(encodedPassword)
                .email(adminRegistrationDto.email())
                .authorities(Collections.singleton(authority))
                .build();
        clientRepository.save(client);


    }

    public Client getClientByEmail(String email) {
        Optional<Client> savedClientByEmail = Optional.ofNullable(
                clientRepository.findClientByEmail(email));

        if (savedClientByEmail.isEmpty()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_EMAIL_IS_NOT_REGISTERED,
                    "User with this email is not registered");
        }
        return savedClientByEmail.get();
    }

    public ProfileInfoDto getProfileInfo(Long clientId) {
        Optional<Client> clientById = clientRepository.findClientById(clientId);

        if (clientById.isEmpty()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_ID_IS_NOT_FOUND,
                    "User with this id does not exist");
        }

        Client client = clientById.get();
        return ProfileInfoDto.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber()).build();
    }

    public void fullUpdateProfileInfo(Long clientId, UpdateProfileDto updateProfileDto) {
        Optional<Client> clientById = clientRepository.findClientById(clientId);

        if (clientById.isEmpty()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_ID_IS_NOT_FOUND,
                    "User with this id does not exist");
        }

        Client client = clientById.get();

        client.setEmail(updateProfileDto.email());
        client.setFirstName(updateProfileDto.firstName());
        client.setLastName(updateProfileDto.lastName());
        client.setMiddleName(updateProfileDto.middleName());
        client.setPhoneNumber(updateProfileDto.phoneNumber());

        clientRepository.save(client);
    }

    public void partialUpdateProfileInfo(Long clientId, UpdateProfileDto updateProfileDto) {
        Optional<Client> clientById = clientRepository.findClientById(clientId);

        if (clientById.isEmpty()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_ID_IS_NOT_FOUND,
                    "User with this id does not exist");
        }

        Client client = clientById.get();

        client.setEmail(
                updateProfileDto.email() == null ?
                        client.getEmail() :
                        updateProfileDto.email()
        );

        client.setFirstName(
                updateProfileDto.firstName() == null ?
                        client.getFirstName() :
                        updateProfileDto.firstName()
        );

        client.setLastName(
                updateProfileDto.lastName() == null ?
                        client.getLastName() :
                        updateProfileDto.lastName()
        );

        client.setMiddleName(
                updateProfileDto.middleName() == null ?
                        client.getMiddleName() :
                        updateProfileDto.middleName()
        );

        client.setPhoneNumber(
                updateProfileDto.phoneNumber() == null ?
                        client.getPhoneNumber() :
                        updateProfileDto.phoneNumber()
        );

        clientRepository.save(client);
    }
}
