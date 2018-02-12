package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Attribute;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttributeRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_attribute(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(100), type " +
            "VARCHAR(100), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbl_attribute WHERE id=#{attributeId}")
    Attribute findById(@Param("attributeId") Long attributeId);

    @Select("SELECT * FROM tbl_attribute")
    List<Attribute> findAll();

    @Insert("INSERT INTO tbl_attribute(id, name, type) VALUES(#{id}, #{name}, #{type})")
    Attribute add(Attribute attribute);

    @Update("UPDATE tbl_attribute SET name=#{name}, type=#{type} WHERE id=#{id}")
    Attribute update(Attribute attribute);

    @Delete("DELETE FROM tbl_attribute WHERE id=#{id}")
    Attribute delete(Attribute attribute);
}
