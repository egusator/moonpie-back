package com.example.moonpie_back.api.controller;


import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    String TEST_NAME = "TestName";
    String TEST_EMAIL = "test.email@mail.com";
    String TEST_PASSWORD = "test_user_pass";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void clearDatabase() {
        clientRepository.deleteAll();
    }

    @AfterEach
    void clearDatabaseAfter() {
        clientRepository.deleteAll();
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    void testRegisterNewClient() throws Exception {
        ClientRegistrationDto newClientDto = new ClientRegistrationDto(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );

        mockMvc.perform(post(ApiPaths.REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newClientDto)))
                .andExpect(status().isOk());

        Optional<Client> savedClient = Optional.ofNullable(clientRepository.findClientByEmail(newClientDto.email()));
        assertThat(savedClient).isNotEmpty();
    }

    @Test
    void testRegisterNewClientWhenClientWithProvidedEmailAlreadyExists() throws Exception {
        Client client = Client.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        clientRepository.save(client);

        ClientRegistrationDto newClientDtoWithExistingEmail = new ClientRegistrationDto(
                TEST_NAME,
                TEST_EMAIL,
                TEST_PASSWORD
        );

        mockMvc.perform(post(ApiPaths.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newClientDtoWithExistingEmail)))
                .andExpect(status().isBadRequest());


        Optional<List<Client>> savedClients = Optional.ofNullable(
                clientRepository.findByEmail(newClientDtoWithExistingEmail.email())
        );
        assertThat(savedClients).isNotEmpty();
        assertThat(savedClients.get().size()).isEqualTo(1);
    }

    @Test
    void testAuthUserWithCorrectEmailAndPassword() throws Exception {
        Client client = Client.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .password(bCryptPasswordEncoder.encode(TEST_PASSWORD))
                .build();
        clientRepository.save(client);

        mockMvc.perform(get(ApiPaths.AUTH, TEST_PASSWORD)
                        .param("email", TEST_EMAIL)
                        .param("password", TEST_PASSWORD))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthUserWhenUserIsNotRegistered() throws Exception {
        mockMvc.perform(get(ApiPaths.AUTH, TEST_PASSWORD)
                        .param("email", TEST_EMAIL)
                        .param("password", TEST_PASSWORD))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthUserWithIncorrectPassword() throws Exception {
        Client client = Client.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .password(bCryptPasswordEncoder.encode(TEST_PASSWORD))
                .build();
        clientRepository.save(client);

        String incorrectUserPassword = "INCORRECT_PASS_123";
        mockMvc.perform(get(ApiPaths.AUTH, TEST_PASSWORD)
                        .param("email", TEST_EMAIL)
                        .param("password", incorrectUserPassword))
                .andExpect(status().isBadRequest());
    }
}
