package io.ermdev.cshop.rest.role;

import io.ermdev.cshop.data.model.Link;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {

    private Long id;
    private String name;
    private List<Link> links = new ArrayList<>();

    public RoleDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
