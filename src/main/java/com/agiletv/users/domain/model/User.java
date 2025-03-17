package com.agiletv.users.domain.model;

public class User {
    private final String username;
    private String name;
    private String email;
    private Gender gender;
    private String picture;
    private String country;
    private String state;
    private String city;

    public User(String username,
                String name,
                String email,
                Gender gender,
                String picture,
                String country,
                String state,
                String city) {
        if (username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Username cannot be blank");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be blank");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email cannot be blank");
        if (!email.matches("^(.+)@(.+)$")) throw new IllegalArgumentException("Invalid email");
        if (gender == null) throw new IllegalArgumentException("Gender cannot be null");
        if (picture == null || picture.trim().isEmpty() || !picture.startsWith("http")) throw new IllegalArgumentException("Invalid picture");

        this.username = username;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.picture = picture;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String username() { return username; }
    public String name() { return name; }
    public String email() { return email; }
    public Gender gender() { return gender; }
    public String picture() { return picture; }
    public String country() { return country; }
    public String state() { return state; }
    public String city() { return city; }

    public void update(String picture, String country, String state, String city) {
        this.picture = picture;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public static UserBuilder builder() { return new UserBuilder(); }
}
