package com.rem.cs.web.dto;

import com.rem.cs.web.validation.annotation.Email;
import com.rem.cs.web.validation.annotation.Name;
import com.rem.cs.web.validation.annotation.Password;

public class UserDto {

    private String id;

    @Name
    private String name;

    @Email
    private String email;

    @Password
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
