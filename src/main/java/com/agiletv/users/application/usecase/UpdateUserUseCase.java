package com.agiletv.users.application.usecase;


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
        var user = userRepository.findByUsername(username).orElseThrow();
        user.update(user.picture(), userRequest.country(), userRequest.state(), userRequest.city());
        userRepository.save(user);
        log.debug("User {} updated", username);
    }
}
