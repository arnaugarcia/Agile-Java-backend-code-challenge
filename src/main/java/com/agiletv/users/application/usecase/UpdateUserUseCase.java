package com.agiletv.users.application.usecase;


import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;
import com.agiletv.users.infrastructure.api.dto.UserRequestDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String username, UserRequestDTO userRequest) {
        log.debug("Updating user {}", username);
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setCity(userRequest.city());
        user.setCountry(userRequest.country());
        user.setPicture(userRequest.picture());
        user.setState(userRequest.state());
        userRepository.update(user);
        log.debug("User {} updated", username);
    }
}
