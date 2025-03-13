package com.agiletv.users.infrastructure.api.dto;

import static java.util.Objects.isNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserRequest", description = "Object for creating or updating a user")
public record UserRequestDTO(String username,
                             String name,
                             String email,
                             String gender,
                             String picture,
                             String country,
                             String state,
                             String city) {

    public UserRequestDTO {
        if (isNull(username) || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (isNull(email) || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
