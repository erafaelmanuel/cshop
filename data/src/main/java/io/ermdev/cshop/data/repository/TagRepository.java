package io.ermdev.cshop.data.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_tag(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(100), description " +
            "VARCHAR(200), PRIMARY KEY(id))")
    void createTable();
}
