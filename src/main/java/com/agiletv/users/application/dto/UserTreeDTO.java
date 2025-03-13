package com.agiletv.users.application.dto;

import java.util.List;

public record UserTreeDTO(String country, List<StateDTO> states) {

    public record StateDTO(String state, List<CityDTO> cities) {}

    public record CityDTO(String city, List<UserDTO> users) {}
}
