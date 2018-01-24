package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_user_role(id BIGINT NOT NULL AUTO_INCREMENT, userId BIGINT NOT NULL," +
            "roleId BIGINT NOT NULL, PRIMARY KEY(id), FOREIGN KEY(userId) REFERENCES tbl_user(id) ON DELETE CASCADE " +
            "ON UPDATE CASCADE, FOREIGN KEY(roleId) REFERENCES tbl_role(id) ON DELETE CASCADE ON UPDATE CASCADE)")
    void createTable();

    @Select("SELECT * FROM tbl_role AS A LEFT JOIN tbl_user_role AS B ON A.id = B.roleId " +
            "WHERE B.userId=#{userId} AND B.roleId=#{roleId}")
    Role findUserRoleById(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("SELECT * FROM tbl_role AS A LEFT JOIN tbl_user_role AS B ON A.id = B.roleId " +
            "WHERE B.userId=#{userId}")
    List<Role> findRolesByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO tbl_user_role(userId, roleId) VALUES(#{userId, #{roleId})")
    void addRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Insert("DELETE FROM tbl_user_role WHERE userId=#{userId} AND roleId=#{roleId}")
    void deleteRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
