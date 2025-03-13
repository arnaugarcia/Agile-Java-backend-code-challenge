package com.agiletv.users.application.usecase;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;
import com.agiletv.users.infrastructure.client.RandomUserClient;
import java.util.List;

public class GenerateUsersUseCase {

    private final UserRepository userRepository;
    private final RandomUserClient randomUserClient;

    public GenerateUsersUseCase(UserRepository userRepository, RandomUserClient randomUserClient) {
        this.userRepository = userRepository;
        this.randomUserClient = randomUserClient;
    }

    public void execute(int count) {
        List<User> users = randomUserClient.fetchRandomUsers(count);
        users.forEach(userRepository::save);
    }
}
