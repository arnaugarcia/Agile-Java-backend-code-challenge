package com.agiletv.users.application.usecase;


import com.agiletv.users.domain.repository.UserRepository;

public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String username) {
        userRepository.deleteByUsername(username);
    }
}
