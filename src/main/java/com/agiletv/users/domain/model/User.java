package com.agiletv.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String username;
    private String name;
    private String email;
    private String gender;
    private String picture;
    private String country;
    private String state;
    private String city;
}
