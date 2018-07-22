package com.rem.cs.data.jpa.item;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecificationBuilder {

    private List<ItemCriteria> params;

    public ItemSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public ItemSpecificationBuilder with(String key, String operation, String value) {
        params.add(new ItemCriteria(key, operation, value));
        return this;
    }

    public Specification<Item> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification<Item>> specs = new ArrayList<>();
        for (ItemCriteria param : params) {
            specs.add(new ItemSpecification(param));
        }

        Specification<Item> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = result.and(specs.get(i));
        }
        return result;
    }
}
