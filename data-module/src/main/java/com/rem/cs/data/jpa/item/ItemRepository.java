package com.rem.cs.data.jpa.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {

}
