package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemRepository {

    @Select("select count(*) from tblitem where name LIKE #{name}")
    Long countByName(@Param("name") String name);

    @Select("select count(*) from tblitem")
    Long countAll();

    @Select("select * from tblitem where id = #{itemId}")
    Item findById(@Param("itemId") Long itemId);

    @Select("select * from tblitem where categoryId=#{categoryId}")
    List<Item> findByCategory(@Param("categoryId") Long categoryId);

    @Select("select * from tblitem where name LIKE #{name}")
    List<Item> findByName(@Param("name") String name);

    @Select("select * from tblitem where name LIKE #{name} LIMIT #{offset}, #{size}")
    List<Item> findByNameFilter(@Param("name") String name, @Param("offset") Long offset, @Param("size") Long size);

    @Select("select * from tblitem LIMIT #{offset}, #{size}")
    List<Item> findAllFilter(@Param("offset") Long offset, @Param("size") Long size);

    @Select("select * from tblitem")
    List<Item> findAll();

    @Insert("insert into tblitem(id, name, description, price, discount, categoryId) values(#{itemId}, #{name}, " +
            "#{description}, #{price}, #{discount}, #{categoryId})")
    void add(@Param("itemId") Long itemId, @Param("name") String name, @Param("description") String description,
             @Param("price") Double price, @Param("discount") Double discount, @Param("categoryId") Long categoryId);

    @Update("update tblitem set name=#{name}, description=#{description}, price=#{price}, discount=#{discount}, " +
            "categoryId=#{categoryId} where id=#{id}")
    void updateById(Item item);

    @Delete("delete from tblitem where id=#{itemId}")
    void deleteById(Long itemId);
}
