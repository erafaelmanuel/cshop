package com.rem.cs.data.jpa.item;

import com.rem.cs.data.jpa.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {

    @Query("select i from Item as i join i.categories as c where c.id in :categoryIds")
    Page<Item> findByCategoryId(@Param("categoryIds") List<String> categoryIds, Pageable pageable);

    @Query("select i.categories from Item as i where i.id = :itemId")
    Page<Category> findCategoriesById(@Param("itemId") String itemId, Pageable pageable);
}
