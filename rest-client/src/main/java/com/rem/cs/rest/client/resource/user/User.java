package com.rem.cs.rest.client.resource.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonAlias("uid")
    private String id;

    private String name;

    private String email;

    private String password;

    private boolean activated;
}
