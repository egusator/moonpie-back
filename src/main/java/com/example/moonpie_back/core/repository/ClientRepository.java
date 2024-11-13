package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByEmail(String email);

    List<Client> findByEmail(String email);

    Optional<Client> findClientById(Long id);
}
