package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_tag(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(100), description " +
            "VARCHAR(200), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbl_tag WHERE id=#{tagId}")
    Tag findById(@Param("tagId") Long tagId);

    @Select("SELECT * FROM tbl_tag")
    List<Tag> findAll();

    @Insert("INSERT INTO tbl_tag(id, name, description) VALUES(#{id}, #{name}, #{description})")
    void add(Tag tag);

    @Update("UPDATE tbl_tag SET name=#{name}, description=#{description} WHERE id=#{id}")
    void update(Tag tag);

    @Delete("DELETE FROM tbl_tag WHERE id=#{id}")
    void delete(Tag tag);
}
