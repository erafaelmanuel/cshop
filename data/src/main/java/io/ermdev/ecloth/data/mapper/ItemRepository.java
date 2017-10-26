package io.ermdev.ecloth.data.mapper;

import io.ermdev.ecloth.model.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemRepository {

    @Select("select * from tblitem where id = #{itemId}")
    Item findById(@Param("itemId") Long itemId);

    @Select("select * from tblitem")
    List<Item> findAll();

    @Insert("insert into tblitem(name, value) values(#{name}, #{value})")
    void add(Item item);

    @Update("update tblitem set name=#{name}, description=#{description}, price=#{price}, discount=#{discount} " +
            "where id=#{id}")
    void updateById(Item item);

    @Delete("delete from tblitem where id=#{itemId}")
    void deleteById(Long itemId);
}
