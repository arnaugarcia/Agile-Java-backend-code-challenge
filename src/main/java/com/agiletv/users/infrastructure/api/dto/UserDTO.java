package com.agiletv.users.infrastructure.api.dto;

import com.agiletv.users.domain.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User", description = "User that represents a user in the system")
public record UserDTO(String username, String email, String country, String state, String city) {
    public static UserDTO from(User user) {
        return new UserDTO(user.username(), user.email(), user.country(), user.state(), user.city());
    }
}
