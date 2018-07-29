package com.rem.cs.rest.client.resource.item;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    @JsonAlias("uid")
    private String id;

    private String name;

    private String description;

    private Double price;

    private String imageUrl;
}
