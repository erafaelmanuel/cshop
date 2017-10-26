package io.ermdev.ecloth.data.mapper;

import io.ermdev.ecloth.model.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryRepository {

    @Select("select * from tblcategory where id = #{categoryId}")
    Category findById(@Param("categoryId") Long categoryId);

    @Select("select _C.id as id, _C.name as name, _C.value as value from tblitem_category as _IC join tblcategory " +
            "as _C on _IC.categoryId=_C.id where _IC.itemId = #{itemId} group by _IC.categoryId")
    List<Category> findByItemId(@Param("itemId") Long itemId);

    @Select("select * from tblcategory")
    List<Category> findAll();

    @Insert("insert into tblcategory(name, value) values(#{name}, #{value})")
    void add(Category category);

    @Update("update tblcategory set name=#{name}, value=#{value} where id=#{id}")
    void updateById(Category category);

    @Delete("delete from tblcategory where id=#{categoryId}")
    void deleteById(Long categoryId);
}
