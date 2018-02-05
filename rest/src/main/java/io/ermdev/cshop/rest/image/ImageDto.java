package io.ermdev.cshop.rest.image;

import io.ermdev.cshop.commons.Link;

import java.util.ArrayList;
import java.util.List;

public class ImageDto {

    private Long id;
    private String src;
    private List<Link> links = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
