package io.ermdev.cshop.web.dto;

import io.ermdev.cshop.security.annotation.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    @NotEmpty
    public String name;
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

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
