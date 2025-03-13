package com.agiletv.users.infrastructure.config;

import com.agiletv.users.application.usecase.CreateUserUseCase;
import com.agiletv.users.application.usecase.DeleteUserUseCase;
import com.agiletv.users.application.usecase.GenerateUsersUseCase;
import com.agiletv.users.application.usecase.GetAllUsersUseCase;
import com.agiletv.users.application.usecase.GetUserByUsernameUseCase;
import com.agiletv.users.application.usecase.UpdateUserUseCase;
import com.agiletv.users.domain.repository.UserRepository;
import com.agiletv.users.infrastructure.client.RandomUserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UseCaseConfig {

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(UserRepository userRepository) {
        return new GetAllUsersUseCase(userRepository);
    }

    @Bean
    public GetUserByUsernameUseCase getUserByUsernameUseCase(UserRepository userRepository) {
        return new GetUserByUsernameUseCase(userRepository);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository) {
        return new UpdateUserUseCase(userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    @Bean
    public GenerateUsersUseCase generateUsersUseCase(UserRepository userRepository, RandomUserClient randomUserClient) {
        return new GenerateUsersUseCase(userRepository, randomUserClient);
    }
}
