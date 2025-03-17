package com.agiletv.users.domain.model;

public class UserBuilder {
    private String username;
    private String name;
    private String email;
    private Gender gender;
    private String picture;
    private String country;
    private String state;
    private String city;

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public UserBuilder gender(String gender) {
        this.gender = Gender.fromString(gender);
        return this;
    }

    public UserBuilder picture(String picture) {
        this.picture = picture;
        return this;
    }

    public UserBuilder country(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder state(String state) {
        this.state = state;
        return this;
    }

    public UserBuilder city(String city) {
        this.city = city;
        return this;
    }

    public User build() {
        return new User(username, name, email, gender, picture, country, state, city);
    }
}
