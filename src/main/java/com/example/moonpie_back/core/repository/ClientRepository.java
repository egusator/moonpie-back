package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByEmail(String email);

    List<Client> findByEmail(String email);

    Client findClientById(Long id);
}
