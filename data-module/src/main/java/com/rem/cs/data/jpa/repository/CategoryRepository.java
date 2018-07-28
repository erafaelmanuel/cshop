package com.rem.cs.data.jpa.repository;

import com.rem.cs.data.jpa.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryJpaRepository")
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("select c from Category as c join c.parent as p where p.id = :parentId")
    List<Category> findByParentId(@Param("parentId") String parentId);

    @Query("select c from Category as c where c.parent is null")
    List<Category> findByParentIsNull();
}
