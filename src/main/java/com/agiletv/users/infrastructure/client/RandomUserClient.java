package com.agiletv.users.infrastructure.client;

import com.agiletv.users.domain.exception.ExternalApiException;
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
            throw new ExternalApiException("Error calling Random User API");
        }

        log.debug("Fetched random users from {}", url);

        if (Objects.isNull(response)) {
            throw new ExternalApiException("Random response is empty");
        }

        List<User> users = new ArrayList<>();
        List<Result> results = response.results();

        for (Result result : results) {
            String username = result.login().username();
            String name = result.name().first() + " " + result.name().last();
            String email = result.email();
            String gender = result.gender();
            String picture = result.picture().large();
            String country = result.location().country();
            String state = result.location().state();
            String city = result.location().city();

            users.add(new User(username, name, email, gender, picture, country, state, city));
        }

        log.debug("Parsed {} random users", users.size());

        return users;
    }
}
