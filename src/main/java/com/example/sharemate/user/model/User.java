package com.example.sharemate.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash( email);
    }
}
