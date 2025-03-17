package com.agiletv.users.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    private String username;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private GenderEntity gender;
    private String picture;
    private String country;
    private String state;
    private String city;
}
