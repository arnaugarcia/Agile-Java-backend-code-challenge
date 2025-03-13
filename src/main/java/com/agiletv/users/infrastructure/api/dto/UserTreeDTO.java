package com.agiletv.users.infrastructure.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "UserTree", description = "Tree of users by country, state and city")
public record UserTreeDTO(String country, List<StateDTO> states) {

    public record StateDTO(String state, List<CityDTO> cities) {}

    public record CityDTO(String city, List<UserDTO> users) {}
}
