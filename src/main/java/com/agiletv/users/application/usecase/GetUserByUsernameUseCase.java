package com.agiletv.users.application.usecase;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;
import java.util.Optional;

public class GetUserByUsernameUseCase {

    private final UserRepository userRepository;

    public GetUserByUsernameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(String username) {
        return userRepository.findByUsername(username);
    }
}
