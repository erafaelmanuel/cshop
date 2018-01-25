package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Deprecated
@Mapper
public interface ItemRepository {

    @Select("SELECT * FROM tblitem WHERE id = #{itemId}")
    Item findById(@Param("itemId") Long itemId);

    @Select("SELECT * FROM tblitem")
    List<Item> findAll();

    @Insert("INSERT INSERT tblitem(id, name, description, price, discount, categoryId) values(#{itemId}, #{name}, " +
            "#{description}, #{price}, #{discount}, #{categoryId})")
    void add(@Param("itemId") Long itemId, @Param("name") String name, @Param("description") String description,
             @Param("price") Double price, @Param("discount") Double discount, @Param("categoryId") Long categoryId);

    @Update("UPDATE tblitem SET name=#{name}, description=#{description}, price=#{price}, discount=#{discount}, " +
            "categoryId=#{categoryId} WHERE id=#{id}")
    void updateById(Item item);

    @Delete("DELETE FROM tblitem WHERE id=#{itemId}")
    void deleteById(Long itemId);
}
