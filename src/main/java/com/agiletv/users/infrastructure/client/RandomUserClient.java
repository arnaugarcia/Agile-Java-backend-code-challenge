package com.agiletv.users.infrastructure.client;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomUserClient {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://randomuser.me/api/?results=";

    public List<User> fetchRandomUsers(int count) {
        String url = API_URL + count;
        RandomUserApiResponse response;
        try {
            log.debug("Fetching random users from {}", url);
            response = restTemplate.getForObject(url, RandomUserApiResponse.class);
        } catch (RestClientException e) {
            throw new RandomUserApiException("Error calling Random User API");
        }

        log.debug("Fetched random users from {}", url);

        if (Objects.isNull(response)) {
            throw new RandomUserApiException("Random response is empty");
        }

        List<User> users = new ArrayList<>();
        List<Result> results = response.results();

        for (Result result : results) {
            users.add(buildFrom(result));
        }

        log.debug("Parsed {} random users", users.size());

        return users;
    }

    private static User buildFrom(Result result) {
        return User.builder()
            .username(result.login().username())
            .name(result.name().first() + " " + result.name().last())
            .email(result.email())
            .gender(result.gender())
            .picture(result.picture().large())
            .country(result.location().country())
            .state(result.location().state())
            .city(result.location().city())
            .build();
    }
}
