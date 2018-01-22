package io.ermdev.cshop.data.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_role(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45), PRIMARY KEY(id))")
    void createTable();
}
