package com.example.moonpie_back.core.entity;


import com.example.moonpie_back.core.enums.AuthorityName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @Column(name = "description")
    private String description;

    @Override
    public String getAuthority() {
        return name.toString();
    }
}
