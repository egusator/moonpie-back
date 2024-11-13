package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.controller.UpdateProfileDto;
import com.example.moonpie_back.api.dto.ProfileInfoDto;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public void save(Client client) {
        clientRepository.save(client);
    }

    public UserDetailsService userDetailsService() {
        return this::getClientByEmail;
    }

    public Client getClientByEmail(String email) {
        Optional<Client> savedClientByEmail =
                clientRepository.findClientByEmail(email);

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
