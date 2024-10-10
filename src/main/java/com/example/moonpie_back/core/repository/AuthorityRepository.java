package com.example.moonpie_back.core.repository;

import com.example.moonpie_back.core.entity.Authority;
import com.example.moonpie_back.core.enums.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findAuthorityByName(AuthorityName authorityName);
}
