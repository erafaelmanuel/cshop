package com.rem.cs.rest.category;

import com.rem.mappyfy.Bind;
import org.springframework.hateoas.core.Relation;

@Relation(value = "category", collectionRelation = "categories")
public class CategoryDto {

    @Bind(fields = {"id"})
    private String uid;

    private String name;

    private String description;

    public CategoryDto() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
