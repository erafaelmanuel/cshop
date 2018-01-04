package io.ermdev.cshop.data.mapper;

import io.ermdev.cshop.data.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryRepository {

    @Select("select * from tblcategory where id = #{categoryId}")
    Category findById(@Param("categoryId") Long categoryId);

    @Select("select _C.id as id, _C.name as name, _C.description as description, _C.parentId as parentId from " +
            "tblitem as _I left join tblcategory as _C on _I.categoryId=_C.id where _I.id = #{itemId}")
    Category findByItemId(@Param("itemId") Long itemId);

    @Select("select * from tblcategory where parentId=#{parentId}")
    List<Category> findByParent(@Param("parentId") Long parentId);

    @Select("select * from tblcategory")
    List<Category> findAll();

    @Insert("insert into tblcategory(id, name, description, parentId) values(#{id}, #{name}, #{description}, " +
            "#{parentId})")
    void add(Category category);

    @Update("update tblcategory set name=#{name}, description=#{description}, parentId=#{parentId} where id=#{id}")
    void updateById(Category category);

    @Delete("delete from tblcategory where id=#{categoryId}")
    void deleteById(Long categoryId);
}
