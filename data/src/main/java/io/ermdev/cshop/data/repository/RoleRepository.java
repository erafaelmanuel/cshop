package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_role(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbl_role WHERE id=#{roleId}")
    Role findById(@Param("roleId") Long roleId);

    @Select("SELECT * FROM tbl_role")
    List<Role> findAll();

    @Insert("INSERT INTO tbl_role(id, name) VALUES(#{id}, #{name})")
    void add(Role role);

    @Update("UPDATE tbl_role SET name=#{name} WHERE id=#{id}")
    void update(Role role);

    @Delete("DELETE FROM tbl_role WHERE id=#{id}")
    void delete(Role role);
}
