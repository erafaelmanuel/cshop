package io.ermdev.cshop.data.mapper;

import io.ermdev.cshop.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("select * from tbluser where id = #{userId}")
    User findById(@Param("userId") Long userId);

    @Select("select * from tbluser")
    List<User> findAll();

    @Insert("insert into tbluser(username, password) values(#{username}, #{password})")
    void add(User user);

    @Update("update tbluser set username=#{username}, password=#{password} where id=#{id}")
    void updateById(User user);

    @Delete("delete from tbluser where id=#{userId}")
    void deleteById(Long userId);
}