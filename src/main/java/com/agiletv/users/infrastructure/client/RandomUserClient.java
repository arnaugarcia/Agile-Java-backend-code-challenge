package com.agiletv.users.infrastructure.client;

import com.agiletv.users.domain.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RandomUserClient {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://randomuser.me/api/?results=";

    public List<User> fetchRandomUsers(int count) {
        String url = API_URL + count;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<User> users = new ArrayList<>();
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        for (Map<String, Object> result : results) {
            Map<String, Object> nameMap = (Map<String, Object>) result.get("name");
            Map<String, Object> locationMap = (Map<String, Object>) result.get("location");
            Map<String, Object> pictureMap = (Map<String, Object>) result.get("picture");

            String username = (String) ((Map<String, Object>) result.get("login")).get("username");
            String name = nameMap.get("first") + " " + nameMap.get("last");
            String email = (String) result.get("email");
            String gender = (String) result.get("gender");
            String picture = (String) pictureMap.get("large");
            String country = (String) locationMap.get("country");
            String state = (String) locationMap.get("state");
            String city = (String) locationMap.get("city");

            users.add(new User(username, name, email, gender, picture, country, state, city));
        }

        return users;
    }
}
