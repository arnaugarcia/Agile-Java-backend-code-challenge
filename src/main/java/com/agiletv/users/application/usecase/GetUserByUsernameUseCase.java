package com.agiletv.users.application.usecase;

import com.agiletv.users.domain.repository.UserRepository;
import com.agiletv.users.infrastructure.api.dto.UserDTO;
import java.util.Optional;

public class GetUserByUsernameUseCase {

    private final UserRepository userRepository;

    public GetUserByUsernameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDTO> execute(String username) {
        return userRepository.findByUsername(username)
            .map(UserDTO::from);
    }
}
