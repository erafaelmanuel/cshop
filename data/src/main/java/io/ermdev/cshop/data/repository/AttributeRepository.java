package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Attribute;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Deprecated
@Mapper
public interface AttributeRepository {

    @Select("select * from tblattribute where id=#{attributeId}")
    Attribute findById(@Param("attributeId") Long attributeId);

    @Select("select * from tblattribute")
    List<Attribute> findAll();

    @Insert("insert into tblattribute(id, title, content, description, type) values(#{id}, #{title}, #{content}, " +
            "#{description}, #{type})")
    Attribute add(Attribute attribute);

    @Update("update tblattribute set title=#{title}, content=#{content}, description=#{description}, type=#{type} " +
            "where id=#{id}")
    Attribute updateById(Attribute attribute);

    @Delete("detele from tblattribute where id=#{attributeId}")
    Attribute deleteById(@Param("attributeId") Long attributeId);
}
