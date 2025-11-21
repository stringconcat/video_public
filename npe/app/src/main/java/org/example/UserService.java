package org.example;

import java.util.Objects;

/**
 * Не делайте такие сервисы, пожалуйста
 */
public class UserService {

    public String createFullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName);
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        
        String fullName = firstName.trim() + " " + lastName.trim();
        return fullName.trim();
    }
}

