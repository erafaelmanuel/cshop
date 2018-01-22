package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbluser(id BIGINIT NOT NULL AUTO_INCREMENT, name VARCHAR(45)," +
            "email VARCHAR(45), username VARCHAR(45), password VARCHAR(45), enabled TINYINT(1), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbluser WHERE id=#{userId}")
    User findById(@Param("userId") Long userId);

    @Select("SELECT * FROM tbluser WHERE email=#{email}")
    User findByEmail(@Param("email") String email);

    @Select("SELECT * FROM tbluser WHERE username=#{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM tbluser")
    List<User> findAll();

    @Insert("INSERT INTO tbluser(id, name, email, username, password, enabled) VALUES(#{id}, #{name}, #{email}," +
            "#{username}, #{password}, #{enabled})")
    void add(User user);

    @Update("UPDATE tbluser SET name=#{name}, email=#{email}, username=#{username}, password=#{password}," +
            "enabled=#{enabled} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM tbluser WHERE id=#{userId}")
    void delete(@Param("userId") Long userId);

    @Delete("DELETE FROM tbluser WHERE id=#{id}")
    void delete(User user);
}