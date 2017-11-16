package io.ermdev.cshop.data.mapper;

import io.ermdev.cshop.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("select * from tbluser where id = #{userId}")
    User findById(@Param("userId") Long userId);

    @Select("select * from tbluser where email = #{email} LIMIT 1")
    User findByEmail(@Param("email") String email);

    @Select("select * from tbluser")
    List<User> findAll();

    @Insert("insert into tbluser(id, name, email, username, password, enabled) values(#{id}, #{name}, #{email}, " +
            "#{username}, #{password}, #{enabled})")
    void add(User user);

    @Update("update tbluser set name=#{name}, email=#{email}, username=#{username}, password=#{password}, " +
            "enabled=#{enabled} where id=#{id}")
    void updateById(User user);

    @Delete("delete from tbluser where id=#{userId}")
    void deleteById(Long userId);
}