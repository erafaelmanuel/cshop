package io.ermdev.cshop.commons;

import java.util.ArrayList;
import java.util.List;

public class ResourceSupport {

    private List<Link> _links = new ArrayList<>();

    public List<Link> getLinks() {
        return _links;
    }

    public void setLinks(List<Link> _links) {
        this._links = _links;
    }
}
