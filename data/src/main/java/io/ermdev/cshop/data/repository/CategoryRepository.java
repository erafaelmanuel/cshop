package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_category(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(100), " +
            "description VARCHAR(200), parentId BIGINT DEFAULT NULL, PRIMARY KEY(id), FOREIGN KEY(parentId) " +
            "REFERENCES tbl_category(id) ON DELETE CASCADE ON UPDATE CASCADE)")
    void createTable();

    @Select("SELECT * FROM tbl_category WHERE id=#{categoryId}")
    Category findById(@Param("categoryId") Long categoryId);

    @Select("SELECT * FROM tbl_category WHERE parentId=#{parentId}")
    List<Category> findByParentId(@Param("parentId") Long parentId);

    @Select("SELECT * FROM tbl_category")
    List<Category> findAll();

    @Insert("INSERT INTO tbl_category(id, name, description, parentId) VALUES(#{id}, #{name}, #{description}, " +
            "#{parentId})")
    void add(Category category);

    @Update("UPDATE tbl_category SET name=#{name}, description=#{description}, parentId=#{parentId} WHERE id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM tbl_category WHERE id=#{categoryId}")
    void delete(Category category);
}
