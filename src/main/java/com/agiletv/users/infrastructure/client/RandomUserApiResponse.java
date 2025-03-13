package com.agiletv.users.infrastructure.client;

import java.util.List;

public record RandomUserApiResponse(List<Result> results) {
    public record Result(
        String gender,
        Name name,
        Location location,
        Picture picture,
        String email,
        Login login) {
        public record Name(String title, String first, String last) {}

        public record Location(
            String city,
            String state,
            String country
        ) {}

        public record Picture(String large) {}

        public record Login(String username) {}
    }
}
