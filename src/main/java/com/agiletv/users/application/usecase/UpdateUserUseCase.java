package com.agiletv.users.application.usecase;


import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;

public class UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(User user) {
        userRepository.update(user);
    }
}
