package io.ermdev.cshop.rest.attribute;

import io.ermdev.cshop.commons.Link;

import java.util.ArrayList;
import java.util.List;

public class AttributeDto {

    private Long id;
    private String name;
    private String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
