package com.rem.cs.data.jpa.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("select c from Category as c join c.parent as p where p.id = :parentId")
    List<Category> findByParentId(@Param("parentId") String parentId);

    @Query("select c from Category as c where c.parent is null")
    List<Category> findByParentIsNull();
}
