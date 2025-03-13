package com.agiletv.users.infrastructure.persistence;

import jakarta.persistence.*;
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
    private String gender;
    private String picture;
    private String country;
    private String state;
    private String city;
}
