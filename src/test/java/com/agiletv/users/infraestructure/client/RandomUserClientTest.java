package com.agiletv.users.infraestructure.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.agiletv.users.domain.model.Gender;
import com.agiletv.users.domain.model.User;
import com.agiletv.users.infrastructure.client.RandomUserApiException;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse.Result;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse.Result.Login;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse.Result.Name;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse.Result.Location;
import com.agiletv.users.infrastructure.client.RandomUserApiResponse.Result.Picture;
import com.agiletv.users.infrastructure.client.RandomUserClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@DisplayName("RandomUserClient")
class RandomUserClientTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final RandomUserClient randomUserClient = new RandomUserClient(restTemplate);

    @Nested
    @DisplayName("fetchRandomUsers()")
    class FetchRandomUsersTests {

        @Test
        void shouldFetchRandomUsersSuccessfully() {
            RandomUserApiResponse response = mock(RandomUserApiResponse.class);
            Result result = mock(Result.class);
            when(result.login()).thenReturn(new Login("jdoe"));
            when(result.name()).thenReturn(new Name("Mr", "John", "Doe"));
            when(result.email()).thenReturn("jdoe@example.com");
            when(result.gender()).thenReturn("female");
            when(result.picture()).thenReturn(new Picture("http://example.com/pic.jpg"));
            when(result.location()).thenReturn(new Location("Los Angeles", "California", "USA"));
            when(response.results()).thenReturn(List.of(result));
            when(restTemplate.getForObject(anyString(), eq(RandomUserApiResponse.class))).thenReturn(response);

            List<User> users = randomUserClient.fetchRandomUsers(1);

            var user = users.getFirst();
            assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("jdoe", user.username()),
                () -> assertEquals("John Doe", user.name()),
                () -> assertEquals("jdoe@example.com", user.email()),
                () -> assertEquals(Gender.FEMALE, user.gender()),
                () -> assertEquals("http://example.com/pic.jpg", user.picture()),
                () -> assertEquals("USA", user.country()),
                () -> assertEquals("California", user.state()),
                () -> assertEquals("Los Angeles", user.city())
            );
        }

        @Test
        void shouldThrowExceptionWhenApiCallFails() {
            when(restTemplate.getForObject(anyString(), eq(RandomUserApiResponse.class))).thenThrow(new RestClientException("API error"));

            assertThrows(RandomUserApiException.class, () -> randomUserClient.fetchRandomUsers(1));
        }

        @Test
        void shouldThrowExceptionWhenResponseIsNull() {
            when(restTemplate.getForObject(anyString(), eq(RandomUserApiResponse.class))).thenReturn(null);

            assertThrows(RandomUserApiException.class, () -> randomUserClient.fetchRandomUsers(1));
        }
    }
}
