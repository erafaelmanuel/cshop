package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_item(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(100), description " +
            "VARCHAR(200), price decimal(11, 2), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbl_item WHERE id = #{itemId}")
    Item findById(@Param("itemId") Long itemId);

    @Select("SELECT * FROM tbl_item")
    List<Item> findAll();

    @Insert("INSERT INTO tbl_item(id, name, description, price) values(#{id}, #{name}, #{description}, #{price})")
    void add(Item item);

    @Update("UPDATE tbl_item SET name=#{name}, description=#{description}, price=#{price} WHERE id=#{id}")
    void update(Item item);

    @Delete("DELETE FROM tbl_item WHERE id=#{id}")
    void delete(Item item);
}
