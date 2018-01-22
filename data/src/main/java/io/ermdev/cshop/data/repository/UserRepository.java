package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_user(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45)," +
            "email VARCHAR(45), username VARCHAR(45), password VARCHAR(45), enabled TINYINT(1), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbl_user WHERE id=#{userId}")
    User findById(@Param("userId") Long userId);

    @Select("SELECT * FROM tbl_user WHERE email=#{email}")
    User findByEmail(@Param("email") String email);

    @Select("SELECT * FROM tbl_user WHERE username=#{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM tbl_user")
    List<User> findAll();

    @Insert("INSERT INTO tbl_user(id, name, email, username, password, enabled) VALUES(#{id}, #{name}, #{email}," +
            "#{username}, #{password}, #{enabled})")
    void add(User user);

    @Update("UPDATE tbl_user SET name=#{name}, email=#{email}, username=#{username}, password=#{password}," +
            "enabled=#{enabled} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM tbl_user WHERE id=#{id}")
    void delete(User user);
}