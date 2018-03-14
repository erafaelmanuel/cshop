package io.ermdev.cshop.rest.image;

import io.ermdev.cshop.commons.ResourceSupport;

public class ImageDto extends ResourceSupport {

    private Long id;
    private String src;

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
}
