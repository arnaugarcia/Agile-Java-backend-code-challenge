package com.agiletv.users.domain.model;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String name;
    private String email;
    private String gender;
    private String picture;
    private String country;
    private String state;
    private String city;

    @Builder
    public User(String username,
                String name,
                String email,
                String gender,
                String picture,
                String country,
                String state,
                String city) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

        this.username = username;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.picture = picture;
        this.country = country;
        this.state = state;
        this.city = city;
    }
}
