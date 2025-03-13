package com.agiletv.users.application.usecase;

import com.agiletv.users.domain.repository.UserRepository;
import com.agiletv.users.infrastructure.api.dto.UserDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetUserByUsernameUseCase {

    private final UserRepository userRepository;

    public GetUserByUsernameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDTO> execute(String username) {
        log.debug("Getting user by username {}", username);
        return userRepository.findByUsername(username)
            .map(UserDTO::from);
    }
}
