package com.rem.cs.rest.dto;

import com.rem.mappyfy.Bind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(value = "user", collectionRelation = "users")
public class UserDto extends ResourceSupport {

    @Bind(fields = {"id"})
    private String uid;

    private String name;

    private String email;

    private String password;
}
