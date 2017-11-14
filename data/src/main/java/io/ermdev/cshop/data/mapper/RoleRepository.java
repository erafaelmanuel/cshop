package io.ermdev.cshop.data.mapper;

import io.ermdev.cshop.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleRepository {

    @Select("select * from tblrole as R left join tbluser_role as UR on R.id=UR.roleId where UR.userId=#{userId}")
    List<Role> getRolesByUserId(@Param("userId") Long userId);
}
