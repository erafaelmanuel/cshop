package com.rem.cs.web.dto;

import com.rem.cs.web.validation.annotation.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserDto {

    private String id;

    @NotNull
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull
    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    @NotNull
    @NotEmpty(message = "Password is required")
    private String password;

    public UserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
