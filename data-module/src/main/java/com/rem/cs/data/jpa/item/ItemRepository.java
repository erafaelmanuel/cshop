package com.rem.cs.data.jpa.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {

    @Query("select i from Item as i join i.categories as c where c.id = :categoryId")
    List<Item> findByCategoryId(@Param("categoryId") String categoryId);

    @Query("select i from Item as i join i.categories as c where c.id = :categoryId")
    Page<Item> findByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);

    @Query("select i from Item as i join i.categories as c where c.id in :categoryIds")
    List<Item> findByCategoryIds(@Param("categoryIds") List<String> categoryIds);

    @Query("select i from Item as i join i.categories as c where c.id in :categoryIds")
    Page<Item> findByCategoryIds(@Param("categoryIds") List<String> categoryIds, Pageable pageable);

}
