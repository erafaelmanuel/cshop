package io.ermdev.ecloth.data.mapper;

import io.ermdev.ecloth.model.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemRepository {

    @Select("select * from tblitem where id = #{itemId}")
    Item findById(@Param("itemId") Long itemId);

    @Select("select * from tblitem where categoryId=#{categoryId}")
    List<Item> findByCategory(@Param("categoryId") Long categoryId);

    @Select("select * from tblitem")
    List<Item> findAll();

    @Insert("insert into tblitem(name, description, price, discount, categoryId) values(#{name}, #{description}, " +
            "#{price}, #{discount}, #{categoryId})")
    void add(@Param("name") String name, @Param("description") String description, @Param("price") Double price,
             @Param("discount") Double discount, @Param("categoryId") Long categoryId);

    @Update("update tblitem set name=#{name}, description=#{description}, price=#{price}, discount=#{discount}, " +
            "categoryId=#{categoryId} where id=#{id}")
    void updateById(Item item);

    @Delete("delete from tblitem where id=#{itemId}")
    void deleteById(Long itemId);
}
