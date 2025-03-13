package com.agiletv.users.application.usecase;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;
import java.util.List;

public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll();
    }
}
