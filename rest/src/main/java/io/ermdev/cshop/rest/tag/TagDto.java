package io.ermdev.cshop.rest.tag;

import io.ermdev.cshop.commons.Link;

import java.util.ArrayList;
import java.util.List;

public class TagDto {

    private Long id;
    private String name;
    private String description;
    private List<Link> links = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}